package com.forksandflames.api.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String logo;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Snack> snacks;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Snack> getSnacks() { return snacks; }
    public void setSnacks(List<Snack> snacks) { this.snacks = snacks; }
    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
}
