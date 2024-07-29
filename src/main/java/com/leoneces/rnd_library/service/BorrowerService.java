package com.leoneces.rnd_library.service;

import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.BookRepository;
import com.leoneces.rnd_library.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class BorrowerService {
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Borrower addBorrower(Borrower borrower){
        borrower.setBorrowerID(UUID.randomUUID().toString());
        return borrowerRepository.save(borrower);
    }

    public Optional<Borrower> findById(String id) {
        return borrowerRepository.findById(id);
    }

    public List<Book> getBorrowedBooksByBorrowerId(String id) {
        return bookRepository.findByBorrowedByBorrowerID(id);
    }
}
