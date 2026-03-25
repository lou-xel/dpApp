package com.arrowcatering.model;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String contactNumber;

    public Supplier() {}

    public Supplier(int supplierId, String supplierName, String contactNumber) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
    }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return supplierName;
    }
}