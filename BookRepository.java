package com.example.proiectse.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    void deleteByTitle(String title);

    List<Book> findByCategoryId(Long categoryID);

    Book findByInventoryNumber(String nr);

}
