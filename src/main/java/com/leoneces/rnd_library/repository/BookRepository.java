package com.leoneces.rnd_library.repository;

import com.leoneces.rnd_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}