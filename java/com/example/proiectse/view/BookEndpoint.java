package com.example.proiectse.view;

import com.example.proiectse.model.Book;
import com.example.proiectse.view.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/books")
public interface BookEndpoint {
    @GetMapping("/allBooks")
    public List<Book> getAllBooks();

    @GetMapping("/find/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable("title") String title);

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BookDTO book);

    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable("category") String category);


}
