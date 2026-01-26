package com.forksandflames.api.model;

public class UserDTO {
    private Long id;
    private String email;
    private String role;
    private Long companyId;
    private String companyName;

    public UserDTO(Long id, String email, String role, Long companyId, String companyName) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Long getCompanyId() { return companyId; }
    public String getCompanyName() { return companyName; }
}