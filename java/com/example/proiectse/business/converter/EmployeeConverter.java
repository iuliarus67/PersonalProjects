package com.example.proiectse.business.converter;

import com.example.proiectse.model.Employee;
import com.example.proiectse.model.EmployeeLoginRepository;
import com.example.proiectse.view.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConverter {

    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;

    public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setName(employeeDTO.getName());
        employee.setPassword(employeeDTO.getPassword());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());

        return employee;
    }
}
