package com.forksandflames.api.controller;

import com.forksandflames.api.model.Purchase;
import com.forksandflames.api.repository.PurchaseRepository;
import com.forksandflames.api.model.Company;
import com.forksandflames.api.model.User;
import com.forksandflames.api.model.Snack;
import com.forksandflames.api.repository.CompanyRepository;
import com.forksandflames.api.repository.UserRepository;
import com.forksandflames.api.repository.SnackRepository;
import com.forksandflames.api.dto.PurchaseReportDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SnackRepository snackRepository;

    @PostMapping
    public List<Purchase> createPurchases(@RequestBody List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            purchase.setPurchaseTime(LocalDateTime.now());
            // If userId is not set, set it to the first user for the companyId
            if (purchase.getUserId() == null && purchase.getCompanyId() != null) {
                List<User> users = userRepository.findAll().stream()
                    .filter(u -> u.getCompany() != null && purchase.getCompanyId().equals(u.getCompany().getId()))
                    .toList();
                if (!users.isEmpty()) {
                    purchase.setUserId(users.get(0).getId());
                }
            }
        }
        return purchaseRepository.saveAll(purchases);
    }

    @GetMapping
    public List<PurchaseReportDTO> getPurchases(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String date
    ) {
        List<Purchase> purchases = purchaseRepository.findAll();
        final LocalDate filterDate;
        if (date != null && !date.isEmpty()) {
            LocalDate parsed = null;
            try {
                parsed = LocalDate.parse(date);
            } catch (Exception ignored) {}
            filterDate = parsed;
        } else {
            filterDate = null;
        }
        return purchases.stream()
                .filter(p -> companyId == null || companyId.equals(p.getCompanyId()))
                .filter(p -> {
                    if (filterDate == null) return true;
                    if (p.getPurchaseTime() == null) return false;
                    return p.getPurchaseTime().toLocalDate().equals(filterDate);
                })
                .map(p -> {
                    String companyName = "";
                    String userEmail = "";
                    String snackName = "";
                    if (p.getCompanyId() != null) {
                        Company c = companyRepository.findById(p.getCompanyId()).orElse(null);
                        if (c != null) companyName = c.getName();
                    }
                    // Always show the user email for the user whose companyId matches the purchase's companyId
                    if (p.getCompanyId() != null) {
                        List<User> users = userRepository.findAll().stream()
                            .filter(u -> u.getCompany() != null && p.getCompanyId().equals(u.getCompany().getId()))
                            .toList();
                        if (!users.isEmpty()) {
                            userEmail = users.get(0).getEmail();
                        }
                    }
                    if (p.getSnackId() != null) {
                        Snack s = snackRepository.findById(p.getSnackId()).orElse(null);
                        if (s != null) snackName = s.getName();
                    }
                    return new PurchaseReportDTO(
                        p.getId(),
                        companyName,
                        userEmail,
                        snackName,
                        p.getQuantity(),
                        p.getTotalPrice(),
                        p.getPurchaseTime() != null ? p.getPurchaseTime().toString() : ""
                    );
                })
                .toList();
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPurchasesExcel(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String date
    ) {
        List<PurchaseReportDTO> data = getPurchases(companyId, date);
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Purchases");
            Row header = sheet.createRow(0);
            String[] columns = {"ID", "Company", "User", "Snack", "Quantity", "Total Price", "Purchase Time"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }
            int rowIdx = 1;
            for (PurchaseReportDTO dto : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getId());
                row.createCell(1).setCellValue(dto.getCompanyName());
                row.createCell(2).setCellValue(dto.getUserEmail());
                row.createCell(3).setCellValue(dto.getSnackName());
                row.createCell(4).setCellValue(dto.getQuantity());
                row.createCell(5).setCellValue(dto.getTotalPrice());
                row.createCell(6).setCellValue(dto.getPurchaseTime());
            }
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            workbook.write(out);
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=purchases.xlsx");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok().headers(headers).body(bytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
