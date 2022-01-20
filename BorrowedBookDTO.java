package com.example.proiectse.view.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookDTO {
    private String title;
    private String borrowedDate;
    private String returnedDate;
}
