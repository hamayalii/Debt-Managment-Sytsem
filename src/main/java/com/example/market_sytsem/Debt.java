package com.example.market_sytsem;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private double amount;
    private String phoneNumber;
    private String currency;

    @Column(name = "is_archived", columnDefinition = "boolean default false")
    private boolean isArchived = false;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL)
    private List<Payment> payments;


    public Debt() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Debt(String customerName, double amount , String phoneNumber , String currency) {
        this.customerName = customerName;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
        this.currency = currency;
    }

    public Debt(String customerName, double amount , String phoneNumber , String currency , List<Payment> payments) {
        this.customerName = customerName;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
        this.currency = currency;
        this.payments = payments;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
