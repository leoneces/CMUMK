package com.leoneces.rnd_library.unit.api;

import com.leoneces.rnd_library.api.BookApiController;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.BookRepository;
import com.leoneces.rnd_library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@SpringBootTest
public class BookApiControllerTest {

    @InjectMocks
    private BookApiController bookApiController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_getBooks(){
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book1 = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        Book book2 = new Book()
                .bookID("d9b2ce8f-12ad-49a1-b87b-7ac588fc0b22")
                .title("Exiles")
                .publicationYear(1918)
                .author(author);

        List<Book> bookList = new ArrayList<Book>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getAllBooks()).thenReturn(bookList);

        //Act
        ResponseEntity<List<Book>> response = bookApiController.getBooks();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookList, response.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void test_addBook(){
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book bookIn = new Book()
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        Book bookOut = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        try {
            when(bookService.addBook(bookIn)).thenReturn(bookOut);
        } catch (Exception e) { /* Do Nothing */ }

        // Act
        ResponseEntity<Book> response = bookApiController.addBook(bookIn);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookOut, response.getBody());
        try {
            verify(bookService, times(1)).addBook(bookIn);
        } catch (Exception e) { /* Do Nothing */ }
    }

    @Test
    public void test_addBook_no_author(){
        // Arrange
        Book book = new Book()
                .title("Ulysses")
                .publicationYear(1922);

        try {
            when(bookService.addBook(book)).thenReturn(null);
        } catch (Exception e) { /* Do Nothing */ }

        // Act
        ResponseEntity<Book> response = bookApiController.addBook(book);

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        try {
            verify(bookService, times(1)).addBook(any(Book.class));
        } catch (Exception e) { /* Do Nothing */ }
    }

    @Test
    public void test_borrowBook(){
        // Arrange
        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author)
                .borrowedBy(borrower);

        try {
            when(bookService.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                    .thenReturn(book);
        } catch (Exception e) { /* Do Nothing */ }

        // Act
        ResponseEntity<Book> response = bookApiController.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        try {
            verify(bookService, times(1))
                    .borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
        } catch (Exception e) { /* Do Nothing */ }
    }

    @Test
    public void test_borrowBook_with_exception(){
        // Arrange
        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author)
                .borrowedBy(borrower);

        try {
            when(bookService.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                    .thenThrow(new Exception("Book is already borrowed"));
        } catch (Exception e) { /* Do Nothing */ }

        // Act
        ResponseEntity<Book> response = bookApiController.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        try {
            verify(bookService, times(1))
                    .borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
        } catch (Exception e) { /* Do Nothing */ }
    }


}
