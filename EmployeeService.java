package com.example.proiectse.business;

import com.example.proiectse.model.Employee;
import com.example.proiectse.model.EmployeeLogin;
import com.example.proiectse.model.EmployeeLoginRepository;
import com.example.proiectse.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public String saveEmployee(Employee employee) {

        if (employeeRepository.findByEmail(employee.getEmail()) == null) {
            EmployeeLogin employeeLogin = new EmployeeLogin();
            employeeLogin.setEmail(employee.getEmail());
            employeeLogin.setPassword(employee.getPassword());
            employeeLoginRepository.save(employeeLogin);

            EmployeeLogin savedEmployee = employeeLoginRepository.findByEmail(employee.getEmail());
            employee.setEmployeeLogin(savedEmployee);

            employeeRepository.save(employee);
            return "Success";
        } else {
            return "Email already in database";
        }
    }
}
