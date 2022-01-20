package com.example.proiectse.business;

import com.example.proiectse.model.*;
import com.example.proiectse.view.dto.UserLogInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogInService {
    @Autowired
    private CustomerLoginRepository customerLoginRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public String getUserType(UserLogInDTO userLogInDTO) {

        if (employeeLoginRepository.findByEmail(userLogInDTO.getEmail()) != null) {
            EmployeeLogin employeeLogin = employeeLoginRepository.findByEmail(userLogInDTO.getEmail());
            if (userLogInDTO.getPassword().equals(employeeLogin.getPassword())) {
                  return "Employee";
            }
        } else {
            if (customerLoginRepository.findByEmail(userLogInDTO.getEmail()) != null) {
                CustomerLogin customerLogin = customerLoginRepository.findByEmail(userLogInDTO.getEmail());
                if (customerLogin.getPassword().equals(userLogInDTO.getPassword())) {
                    return "Customer";
                }
            }
        }
        return "Wrong credentials";
    }
}
