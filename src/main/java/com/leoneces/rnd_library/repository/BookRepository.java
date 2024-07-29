package com.leoneces.rnd_library.repository;

import com.leoneces.rnd_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByBorrowedByBorrowerID(String borrowerId);
}