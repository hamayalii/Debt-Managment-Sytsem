package com.example.market_sytsem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt , Long> {

    List<Debt> findByCustomerNameContaining(String name);
}
