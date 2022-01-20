package com.example.proiectse.view.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private Long overduePoints;
    private List<BorrowedBookDTO> borrowedBooks;
}
