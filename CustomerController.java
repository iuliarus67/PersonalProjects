package com.example.proiectse.view;

import com.example.proiectse.business.CustomerService;
import com.example.proiectse.view.dto.CustomerDTO;
import com.example.proiectse.view.dto.MultipleCartItemsDTO;
import com.example.proiectse.view.dto.UserPointsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController implements CustomerEndpoint {

    @Autowired
    private CustomerService customerService;

    public void createCustomer(CustomerDTO customerDTO) {
        customerService.saveEmployee(customerDTO);
    }


    public Long getOverduePoints(String email) {
        return customerService.getOverduePoints(email);
    }

    public String createOrder(MultipleCartItemsDTO multipleCartItemsDTO) {
        customerService.createOrder(multipleCartItemsDTO);
        return "Ok";
    }

    public void editAccount(CustomerDTO customerDTO) {
        customerService.editAccount(customerDTO);
    }

    public CustomerDTO getDetails(@PathVariable("email") String email) {
        CustomerDTO customerDTO = customerService.getClientDetails(email);
        return customerService.getClientDetails(email);

    }


    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll();
    }

    public void modifyPoints(UserPointsDTO userPointsDTO) {
        customerService.modifyPoints(userPointsDTO);
    }
}
