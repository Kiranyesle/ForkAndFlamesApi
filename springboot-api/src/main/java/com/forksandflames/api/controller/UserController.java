package com.forksandflames.api.controller;

import com.forksandflames.api.model.User;
import com.forksandflames.api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> getAll() { return userRepository.findAll(); }
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) { return userRepository.findById(id).orElse(null); }
    @PostMapping
    public User create(@RequestBody User user) { return userRepository.save(user); }

    @PostMapping("/login")
    public ResponseEntity<com.forksandflames.api.model.UserDTO> login(@RequestBody User loginRequest) {
        logger.info("Login attempt: email={}, password={}", loginRequest.getEmail(), loginRequest.getPassword());
        Optional<User> userOpt = userRepository.findAll().stream()
            .filter(u -> u.getEmail().equals(loginRequest.getEmail()))
            .findFirst();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            logger.info("Found user: email={}, password={}, role={}", user.getEmail(), user.getPassword(), user.getRole());
            if (user.getPassword().equals(loginRequest.getPassword())) {
                com.forksandflames.api.model.UserDTO dto = new com.forksandflames.api.model.UserDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getRole(),
                    user.getCompany() != null ? user.getCompany().getId() : null,
                    user.getCompany() != null ? user.getCompany().getName() : null
                );
                return ResponseEntity.ok(dto);
            } else {
                logger.warn("Password mismatch for user: {}", user.getEmail());
                return ResponseEntity.status(401).body(null);
            }
        } else {
            logger.warn("User not found: {}", loginRequest.getEmail());
            return ResponseEntity.status(401).body(null);
        }
    }
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { userRepository.deleteById(id); }
}
