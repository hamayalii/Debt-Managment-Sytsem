package com.example.market_sytsem;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amount;
    private String paymentDate;
    @ManyToOne
    @JoinColumn(name = "debt_id")
    private Debt debt;

    private String currency;


    public Payment() {
    }

    public Payment(long id, double amount, String paymentDate, Debt debt, String currency) {
        this.id = id;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.debt = debt;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Debt getDebt() {
        return debt;
    }

    public void setDebt(Debt debt) {
        this.debt = debt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
