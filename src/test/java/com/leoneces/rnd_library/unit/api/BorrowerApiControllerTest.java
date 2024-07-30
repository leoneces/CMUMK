package com.leoneces.rnd_library.unit.api;

import com.leoneces.rnd_library.api.BorrowerApiController;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.service.BorrowerService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
@SpringBootTest
public class BorrowerApiControllerTest {

    @InjectMocks
    private BorrowerApiController borrowerApiController;

    @Mock
    private BorrowerService borrowerService;

    @Test
    public void test_addBorrower(){
        // Arrange
        Borrower borrowerIn = new Borrower()
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Borrower borrowerOut = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        when(borrowerService.addBorrower(borrowerIn)).thenReturn(borrowerOut);

        // Act
        ResponseEntity<Borrower> response = borrowerApiController.addBorrower(borrowerIn);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", response.getBody().getBorrowerID());
        assertEquals("Michael D. Higgins", response.getBody().getName());
        assertEquals("+353 1 677 0095", response.getBody().getPhone());
        verify(borrowerService, times(1)).addBorrower(borrowerIn);
    }

    @Test
    public void test_addBorrower_empty_data(){
        // Arrange
        when(borrowerService.addBorrower(any())).thenReturn(null);

        // Act
        ResponseEntity<Borrower> response = borrowerApiController.addBorrower(new Borrower());

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(borrowerService, times(1)).addBorrower(any(Borrower.class));
    }

    @Test
    public void test_getBorrowerById() {
        // Arrange
        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        when(borrowerService.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.ofNullable(borrower));

        // Act
        ResponseEntity<Borrower> response = borrowerApiController.getBorrowerById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", response.getBody().getBorrowerID());
        assertEquals("Michael D. Higgins", response.getBody().getName());
        assertEquals("+353 1 677 0095", response.getBody().getPhone());
        verify(borrowerService, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }

    @Test
    public void test_getBorrowerById_not_found() {
        // Arrange
        when(borrowerService.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Borrower> response = borrowerApiController.getBorrowerById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(borrowerService, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
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

        when(borrowerService.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(bookList);

        // Act
        ResponseEntity<List<Book>> response = borrowerApiController.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookList, response.getBody());
        verify(borrowerService, times(1))
                .getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }

    @Test
    public void test_getBorrowedBooksByBorrowerId_not_found() throws Exception {

        when(borrowerService.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenThrow(new NoSuchElementException("Borrower not found"));

        // Act
        ResponseEntity<List<Book>> response = borrowerApiController.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(borrowerService, times(1))
                .getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }

    @Test
    public void test_getBorrowedBooksByBorrowerId_bad_request() throws Exception {

        when(borrowerService.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenThrow(new IllegalArgumentException("Borrower ID can't be blank"));

        // Act
        ResponseEntity<List<Book>> response = borrowerApiController.getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(borrowerService, times(1))
                .getBorrowedBooksByBorrowerId("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }
}
