package com.leoneces.rnd_library.service;

import org.springframework.stereotype.Service;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service

public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book person) {
        return bookRepository.save(person);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
}
