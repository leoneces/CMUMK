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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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

    public Book addBook(Book book) throws Exception {
        // Sets book ID
        book.setBookID(UUID.randomUUID().toString());

        // Checks if author exists
        Optional<Author> author = authorRepository.findById(book.getAuthor().getAuthorID());
        if (author.isEmpty()){ throw new Exception("Author not found"); }

        // Guarantee the book starts without a borrower
        book.setBorrowedBy(null);

        // Adds the book
        return bookRepository.save(book);
    }

    public Optional<Book> findById(String bookId) {
        return bookRepository.findById(bookId);
    }

    public Book borrowBook(String bookId, String borrowerId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Borrower> borrower = borrowerRepository.findById(borrowerId);

        // Checks if Book and Borrower exist, and if Book is already borrowed
        if (book.isEmpty()) { throw new Exception("Book not found"); }
        if (borrower.isEmpty()){ throw new Exception("Borrower not found"); }
        if (book.get().getBorrowedBy()!=null){ throw new Exception("Book is already borrowed"); }

        book.get().setBorrowedBy(borrower.get());

        return bookRepository.save(book.get());
    }
}
