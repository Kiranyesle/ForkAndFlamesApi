package com.forksandflames.api.controller;

import com.forksandflames.api.model.Snack;
import com.forksandflames.api.repository.SnackRepository;
import com.forksandflames.api.repository.CompanyRepository;
import com.forksandflames.api.model.Company;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/snacks")
@CrossOrigin(origins = "*")
public class SnackController {
    private final SnackRepository snackRepository;
    private final CompanyRepository companyRepository;
    public SnackController(SnackRepository snackRepository, CompanyRepository companyRepository) {
        this.snackRepository = snackRepository;
        this.companyRepository = companyRepository;
    }
    @GetMapping
    public List<Snack> getAll(@RequestParam(value = "companyId", required = false) Long companyId) {
        if (companyId != null) {
            return snackRepository.findByCompany_Id(companyId);
        }
        return snackRepository.findAll();
    }
    @GetMapping("/{id}")
    public Snack getById(@PathVariable Long id) { return snackRepository.findById(id).orElse(null); }
    @PostMapping
    public List<Snack> create(@RequestBody Snack snack, @RequestParam Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            snack.setCompany(company);
            snackRepository.save(snack);
            return snackRepository.findByCompany_Id(companyId);
        }
        throw new IllegalArgumentException("Invalid companyId");
    }
    @PutMapping("/{id}")
    public Snack update(@PathVariable Long id, @RequestBody Snack snack) {
        snack.setId(id);
        return snackRepository.save(snack);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { snackRepository.deleteById(id); }
}
