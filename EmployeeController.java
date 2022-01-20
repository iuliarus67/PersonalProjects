package com.example.proiectse.view;

import com.example.proiectse.business.EmployeeService;
import com.example.proiectse.business.converter.EmployeeConverter;
import com.example.proiectse.view.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController implements EmployeeEndPoint {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter employeeConverter;

    public String registerEmployee(EmployeeDTO employeeDTO) {
        return employeeService.saveEmployee(employeeConverter.employeeDTOToEmployee(employeeDTO));
    }

}
