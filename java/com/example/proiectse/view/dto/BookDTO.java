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
public class BookDTO implements Serializable {
    private String title;
    private String author;
    private String inventoryNumber;
    private long categoryId;
    private long stock;
    private String imageUrl;
}
