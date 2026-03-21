package com.arrowcatering.model;

import java.time.LocalDate;

public class Customer {
    private int customerId;
    private String lastName;
    private String firstName;
    private String mi;  // Middle initial
    private String email;
    private int contactNumber;
    private LocalDate dateRegistered;  // Changed from LocalDateTime to LocalDate

    // Default constructor
    public Customer() {}

    // Constructor with all fields
    public Customer(int customerId, String lastName, String firstName, String mi,
                    String email, int contactNumber, LocalDate dateRegistered) {
        this.customerId = customerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.mi = mi;
        this.email = email;
        this.contactNumber = contactNumber;
        this.dateRegistered = dateRegistered;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }
}