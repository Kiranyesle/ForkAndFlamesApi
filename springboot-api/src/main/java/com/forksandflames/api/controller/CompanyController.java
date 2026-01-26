package com.forksandflames.api.controller;

import com.forksandflames.api.model.Company;
import com.forksandflames.api.repository.CompanyRepository;
import com.forksandflames.api.model.User;
import com.forksandflames.api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyController(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<com.forksandflames.api.model.CompanyDTO> getAll() {
        return companyRepository.findAll().stream()
            .filter(c -> c.getId() != 1)
            .map(c -> new com.forksandflames.api.model.CompanyDTO(c.getId(), c.getName(), c.getEmail(), c.getLogo()))
            .toList();
    }
    @GetMapping("/{id}")
    public com.forksandflames.api.model.CompanyDTO getById(@PathVariable Long id) {
        if (id == 1) return null;
        return companyRepository.findById(id)
            .map(c -> new com.forksandflames.api.model.CompanyDTO(c.getId(), c.getName(), c.getEmail(), c.getLogo()))
            .orElse(null);
    }
    @PostMapping
    public Company create(@RequestBody Company company) {
        Company savedCompany = companyRepository.save(company);
        // Create a user for this company
        User user = new User();
        user.setEmail(company.getEmail());
        user.setPassword("1234");
        user.setRole("user");
        user.setCompany(savedCompany);
        userRepository.save(user);
        return savedCompany;
    }
    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company company) {
        company.setId(id);
        return companyRepository.save(company);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { companyRepository.deleteById(id); }
}
