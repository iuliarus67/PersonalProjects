package com.example.proiectse.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowedBookRepository extends CrudRepository<BorrowedBook, Integer> {

    List<BorrowedBook> findAllByCustomerId(Integer id);
}
