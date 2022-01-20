package com.example.proiectse.view;


import com.example.proiectse.business.UserLogInService;
import com.example.proiectse.view.dto.UserLogInDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogInController implements LogInEndpoint {

    @Autowired
    private UserLogInService userLogInService;

    public String logIn(UserLogInDTO userLogInDTO) {
        return  JSONObject.quote(userLogInService.getUserType(userLogInDTO));
    }

}
