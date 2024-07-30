package com.leoneces.rnd_library.unit.service;


import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.BookRepository;
import com.leoneces.rnd_library.repository.BorrowerRepository;
import com.leoneces.rnd_library.service.BorrowerService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class BorrowerServiceTest {

    @InjectMocks
    private BorrowerService borrowerService;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void test_addBorrower(){
        // Arrange
        Borrower borrower = new Borrower()
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

        // Act
        Borrower result = borrowerService.addBorrower(borrower);

        // Assert
        assertNotNull(result);
        assertDoesNotThrow(() -> { UUID.fromString(result.getBorrowerID()); });
        assertEquals("Michael D. Higgins", result.getName());
        assertEquals("+353 1 677 0095", result.getPhone());
        verify(borrowerRepository, times(1)).save(borrower);
    }

    @Test
    public void test_findById(){
        // Arrange
        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");
        when(borrowerRepository.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.ofNullable(borrower));

        // Act
        Optional<Borrower> result = borrowerService.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(borrower, result.get());
        verify(borrowerRepository, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }

    @Test
    public void test_getBorrowedBooksByBorrowerId() throws Exception {
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Book book1 = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author)
                .borrowedBy(borrower);

        Book book2 = new Book()
                .bookID("d9b2ce8f-12ad-49a1-b87b-7ac588fc0b22")
                .title("Exiles")
                .publicationYear(1918)
                .author(author)
                .borrowedBy(borrower);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookRepository.findByBorrowedByBorrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(bookList);
        when(borrowerRepository.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.ofNullable(borrower));

        // Act
        List<Book> result = borrowerService.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertEquals(bookList, result);
        verify(bookRepository, times(1)).findByBorrowedByBorrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }
}
