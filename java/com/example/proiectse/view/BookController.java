package com.example.proiectse.view;

import com.example.proiectse.business.BookService;
import com.example.proiectse.model.Book;
import com.example.proiectse.view.dto.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController implements BookEndpoint{

    @Autowired
    private BookService bookService;

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public ResponseEntity<Book> getBookByTitle(@PathVariable("title") String title){
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }

    public ResponseEntity<String> addBook(BookDTO book) {
         return new ResponseEntity<>(bookService.addBook(book),HttpStatus.OK);
    }

    public List<Book> getBooksByCategory(@PathVariable("category") String category){
        return bookService.getBooksByCategory(category);
    }



}
