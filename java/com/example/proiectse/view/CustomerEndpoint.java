package com.example.proiectse.view;


import com.example.proiectse.view.dto.CustomerDTO;
import com.example.proiectse.view.dto.MultipleCartItemsDTO;
import com.example.proiectse.view.dto.UserPointsDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customer")
public interface CustomerEndpoint {

    @PostMapping("/register")
    void createCustomer(@RequestBody  CustomerDTO customerDTO);

    @GetMapping("/overdue/{email}")
    Long getOverduePoints(@PathVariable("email") String email);

    @PostMapping("/createOrder")
    String createOrder(@RequestBody MultipleCartItemsDTO cartItems);

    @PutMapping("/editAccount")
    void editAccount(@RequestBody CustomerDTO customerDTO);

    @GetMapping("/details/{email}")
    CustomerDTO getDetails(@PathVariable("email") String email);

    @GetMapping("/all")
    List<CustomerDTO> getAllCustomers();

    @PutMapping("/modify-points")
    void modifyPoints(@RequestBody UserPointsDTO userPointsDTO);

}
