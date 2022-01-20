package com.example.proiectse.view;

import com.example.proiectse.view.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/employee")
public interface EmployeeEndPoint {

    @PostMapping("/register")
    public String registerEmployee(@RequestBody EmployeeDTO employeeDTO);


}
