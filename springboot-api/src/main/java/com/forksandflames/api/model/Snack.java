package com.forksandflames.api.model;

import jakarta.persistence.*;

@Entity
public class Snack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int stock;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
