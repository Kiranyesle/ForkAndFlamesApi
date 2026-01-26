package com.forksandflames.api.controller;

import com.forksandflames.api.model.Snack;
import com.forksandflames.api.repository.SnackRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/snacks")
@CrossOrigin(origins = "*")
public class SnackController {
    private final SnackRepository snackRepository;
    public SnackController(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }
    @GetMapping
    public List<Snack> getAll() { return snackRepository.findAll(); }
    @GetMapping("/{id}")
    public Snack getById(@PathVariable Long id) { return snackRepository.findById(id).orElse(null); }
    @PostMapping
    public Snack create(@RequestBody Snack snack) { return snackRepository.save(snack); }
    @PutMapping("/{id}")
    public Snack update(@PathVariable Long id, @RequestBody Snack snack) {
        snack.setId(id);
        return snackRepository.save(snack);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { snackRepository.deleteById(id); }
}
