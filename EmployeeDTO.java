package com.example.proiectse.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO  implements Serializable {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;
}
