package com.forksandflames.api.dto;

public class PurchaseReportDTO {
    private Long id;
    private String companyName;
    private String userEmail;
    private String snackName;
    private int quantity;
    private double totalPrice;
    private String purchaseTime;

    public PurchaseReportDTO(Long id, String companyName, String userEmail, String snackName, int quantity, double totalPrice, String purchaseTime) {
        this.id = id;
        this.companyName = companyName;
        this.userEmail = userEmail;
        this.snackName = snackName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.purchaseTime = purchaseTime;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getSnackName() { return snackName; }
    public void setSnackName(String snackName) { this.snackName = snackName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public String getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(String purchaseTime) { this.purchaseTime = purchaseTime; }
}
