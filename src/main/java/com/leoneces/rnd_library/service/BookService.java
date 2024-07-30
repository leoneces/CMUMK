package com.leoneces.rnd_library.service;

import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.AuthorRepository;
import com.leoneces.rnd_library.repository.BorrowerRepository;
import org.springframework.stereotype.Service;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service

public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) throws NoSuchElementException {
        // Sets book ID
        book.setBookID(UUID.randomUUID().toString());

        // Checks if author exists
        Optional<Author> author = authorRepository.findById(book.getAuthor().getAuthorID());
        if (author.isEmpty()){ throw new NoSuchElementException("Author not found"); }

        // Guarantee the book starts without a borrower
        book.setBorrowedBy(null);

        // Adds the book
        return bookRepository.save(book);
    }

    public Book borrowBook(String bookId, String borrowerId) throws IllegalArgumentException, NoSuchElementException {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);

        // Checks if Book and Borrower exist, and if Book is already borrowed
        if (book.isEmpty()) { throw new IllegalArgumentException("Book not found"); }
        if (borrower.isEmpty()){ throw new IllegalArgumentException("Borrower not found"); }
        if (book.get().getBorrowedBy()!=null){ throw new NoSuchElementException("Book is already borrowed"); }

        book.get().setBorrowedBy(borrower.get());

        return bookRepository.save(book.get());
    }
}
