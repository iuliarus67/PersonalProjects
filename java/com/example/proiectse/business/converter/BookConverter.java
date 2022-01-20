package com.example.proiectse.business.converter;

import com.example.proiectse.model.Book;
import com.example.proiectse.view.dto.BookDTO;
import org.springframework.stereotype.Service;

@Service
public class BookConverter {

    public Book bookDtoToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setStock(bookDTO.getStock());
        book.setAuthor(bookDTO.getAuthor());
        book.setCategoryId(bookDTO.getCategoryId());
        book.setImageUrl(bookDTO.getImageUrl());
        book.setTitle(bookDTO.getTitle());
        book.setInventoryNumber(bookDTO.getInventoryNumber());
        return book;
    }
}
