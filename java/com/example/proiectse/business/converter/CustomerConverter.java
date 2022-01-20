package com.example.proiectse.business.converter;

import com.example.proiectse.model.Customer;
import com.example.proiectse.view.dto.BorrowedBookDTO;
import com.example.proiectse.view.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerConverter {

    public CustomerDTO entityToDTO(Customer customer, List<BorrowedBookDTO> bookList) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setBorrowedBooks(bookList);
        customerDTO.setName(customer.getName());

        customerDTO.setOverduePoints(customer.getOverduePoints());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setPassword(customer.getPassword());

        return customerDTO;
    }

}
