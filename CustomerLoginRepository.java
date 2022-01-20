package com.example.proiectse.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLoginRepository extends CrudRepository<CustomerLogin, Integer> {
    public CustomerLogin findByEmail(String email);
}
