package com.example.proiectse.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "library_book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "stock")
    private long stock;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "image_url")
    private String imageUrl;

    public String toString() {

        return "Book with id : " + id + " has as author : " + author + ", and title : " + title;
    }
}
