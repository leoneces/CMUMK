package com.leoneces.rnd_library.service;

import org.springframework.stereotype.Service;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        book.setBookID(UUID.randomUUID().toString());
        return bookRepository.save(book);
    }
}
