package com.example.proiectse.view;


import com.example.proiectse.view.dto.UserLogInDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/logIn")
public interface LogInEndpoint {

    @PutMapping("/user")
    String logIn(@RequestBody UserLogInDTO user);


}
