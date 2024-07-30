package com.leoneces.rnd_library.service;

import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.BookRepository;
import com.leoneces.rnd_library.repository.BorrowerRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class BorrowerService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Borrower addBorrower(Borrower borrower) {
        borrower.setBorrowerID(UUID.randomUUID().toString());
        return borrowerRepository.save(borrower);
    }

    public Optional<Borrower> findById(String id) {
        return borrowerRepository.findById(id);
    }

    public List<Book> getBorrowedBooksByBorrowerId(String id) throws NoSuchElementException, IllegalArgumentException {
        if (id.isBlank()) { throw new IllegalArgumentException("Borrower ID can't be blank"); }

        Optional<Borrower> borrower = borrowerRepository.findById(id);

        if (borrower.isEmpty()) { throw new NoSuchElementException("Borrower not found"); }
        return bookRepository.findByBorrowedByBorrowerID(id);
    }
}
