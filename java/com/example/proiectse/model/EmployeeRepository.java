package com.example.proiectse.model;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public Employee findByEmail(String email);
}
