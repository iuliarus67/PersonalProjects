package com.example.proiectse.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLoginRepository extends CrudRepository<EmployeeLogin, Integer> {

    public EmployeeLogin findByEmail(String email);
}
