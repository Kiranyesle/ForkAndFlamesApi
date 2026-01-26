package com.forksandflames.api.model;

public class CompanyDTO {
    private Long id;
    private String name;
    private String email;
    private String logo;

    public CompanyDTO(Long id, String name, String email, String logo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.logo = logo;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getLogo() { return logo; }
}