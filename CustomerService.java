package com.example.proiectse.business;

import com.example.proiectse.business.converter.BookConverter;
import com.example.proiectse.business.converter.CustomerConverter;
import com.example.proiectse.model.*;
import com.example.proiectse.view.dto.BorrowedBookDTO;
import com.example.proiectse.view.dto.CustomerDTO;
import com.example.proiectse.view.dto.MultipleCartItemsDTO;
import com.example.proiectse.view.dto.UserPointsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerLoginRepository customerLoginRepository;

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerConverter converter;

    @Autowired
    private BookConverter bookConverter;

    public String saveEmployee(CustomerDTO customer) {

        if (customerRepository.findByEmail(customer.getEmail()) == null) {
            Customer customer1 = new Customer();
            customer1.setPhoneNumber(customer.getPhoneNumber());
            customer1.setEmail(customer.getEmail());
            customer1.setName(customer.getName());
            customer1.setAddress(customer.getAddress());
            customer1.setPassword(customer.getPassword());
            customer1.setOverduePoints(0L);

            CustomerLogin customerLogin = new CustomerLogin();
            customerLogin.setEmail(customer.getEmail());
            customerLogin.setPassword(customer.getPassword());
            customerLoginRepository.save(customerLogin);

            CustomerLogin savedCustomer = customerLoginRepository.findByEmail(customer.getEmail());
            customer1.setCustomerLogin(savedCustomer);

            customerRepository.save(customer1);
            return "Success";
        } else {
            return "Email already in database";
        }
    }

    public Long getOverduePoints(String email) {
        Customer customer;
        Long overDuePoints = 0L;
        if (customerRepository.findByEmail(email) != null) {
            customer = customerRepository.findByEmail(email);
            overDuePoints += customer.getOverduePoints();
//            List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAllByCustomerId(customer.getId());
//            overDuePoints += borrowedBookList.stream()
//                    .mapToLong(borrowedBook -> {
//                        if (LocalDateTime.now().isAfter(borrowedBook.getDateOfBorrow().plusDays(21))) {
//                            return LocalDateTime.now().compareTo(borrowedBook.getDateOfBorrow().plusDays(21));
//                        } else {
//                            return 0L;
//                        }
//                    })
//                    .sum();
            customer.setOverduePoints(overDuePoints);
            customerRepository.save(customer);
            return overDuePoints;
        } else {
            return 100L;
        }
    }

    public void createOrder(MultipleCartItemsDTO multipleCartItemsDTO) {
        Customer customer = customerRepository.findByEmail(multipleCartItemsDTO.getEmail());
        List<BorrowedBook> toSave = new ArrayList<>();
        multipleCartItemsDTO.getCartItemDTOList()
                .forEach(cartItemDTO -> {
                    if (cartItemDTO.getQuantity() > 0) {
                        LocalDateTime now = LocalDateTime.now();
                        BorrowedBook borrowedBook = new BorrowedBook();
                        Book book = new Book();
                        if (bookRepository.findByTitle(cartItemDTO.getBookDTO().getTitle()).isPresent()) {
                            book = bookRepository.findByTitle(cartItemDTO.getBookDTO().getTitle()).get();
                        }
                        long initStock = book.getStock();
                        book.setStock(initStock - cartItemDTO.getQuantity());
                        borrowedBook.setBook(book);
                        borrowedBook.setCustomer(customer);
                        borrowedBook.setDateOfBorrow(now);
                        toSave.add(borrowedBook);
                    }
                });

        borrowedBookRepository.saveAll(toSave);

    }

    public CustomerDTO getClientDetails(String email) {
        Customer customer = customerRepository.findByEmail(email);
        List<BorrowedBook> books = borrowedBookRepository.findAllByCustomerId(customer.getId());
        List<BorrowedBookDTO> borrowedBookDTOList = new ArrayList<>();
        books
                .forEach(book -> {
                    BorrowedBookDTO borrowedBookDTO = new BorrowedBookDTO(book.getBook().getTitle(), book.getDateOfBorrow().toString(), book.getDateOfBorrow().plusDays(21).toString());
                    borrowedBookDTOList.add(borrowedBookDTO);
                });
        return converter.entityToDTO(customer, borrowedBookDTOList);
    }

    public void editAccount(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setName(customerDTO.getName());
        customer.setPassword(customerDTO.getPassword());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());


        CustomerLogin customerLogin = customerLoginRepository.findByEmail(customerDTO.getEmail());
        customerLogin.setEmail(customerDTO.getEmail());
        customerLogin.setPassword(customerDTO.getPassword());

        customerRepository.save(customer);
        customerLoginRepository.save(customerLogin);
    }

    public List<CustomerDTO> getAll() {
        List<Customer> customerList = (List<Customer>) customerRepository.findAll();
        return customerList.stream()
                .map(customer -> getClientDetails(customer.getEmail()))
                .collect(Collectors.toList());
    }

    public void modifyPoints(UserPointsDTO userPointsDTO) {
        Customer customer = customerRepository.findByEmail(userPointsDTO.getEmail());
        customer.setOverduePoints(userPointsDTO.getPoints());
        customerRepository.save(customer);
    }
}


