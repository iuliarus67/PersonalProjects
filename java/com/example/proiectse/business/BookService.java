package com.example.proiectse.business;

import com.example.proiectse.business.converter.BookConverter;
import com.example.proiectse.exception.BookNotFoundException;
import com.example.proiectse.model.Book;
import com.example.proiectse.model.BookRepository;
import com.example.proiectse.model.Category;
import com.example.proiectse.model.CategoryRepository;
import com.example.proiectse.view.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookConverter bookConverter;


    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book updateBookStock(int stock, String title) {
        Book book = bookRepository.findByTitle(title).orElseThrow(() -> new BookNotFoundException(title));
        book.setStock(stock);
        return bookRepository.save(book);
    }

    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(() -> new BookNotFoundException(title));
    }

    public String addBook(BookDTO book) {
        Book book1 = bookConverter.bookDtoToEntity(book);
        if (bookRepository.findByTitle(book.getTitle()).isEmpty()) {
            if (bookRepository.findByInventoryNumber(book.getInventoryNumber()) == null) {
                bookRepository.save(book1);
                return "Success";
            } else {
                return "Inventory numbers cannot be the same";
            }
        } else {
            return "Book already in database";
        }
    }

    public List<Book> getBooksByCategory(String category) {
        Optional<Category> category2 = categoryRepository.findByName(category);
        if (category2.isPresent()) {
            List<Book> books = bookRepository.findByCategoryId(category2.get().getId());
            return books;
        }
        return new ArrayList<>();
    }
}
