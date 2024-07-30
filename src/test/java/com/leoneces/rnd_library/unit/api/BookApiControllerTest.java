package com.leoneces.rnd_library.unit.api;

import com.leoneces.rnd_library.api.BookApiController;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.service.BookService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
@SpringBootTest
public class BookApiControllerTest {

    @InjectMocks
    private BookApiController bookApiController;

    @Mock
    private BookService bookService;

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

        List<Book> bookList = new ArrayList<>();
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
    public void test_addBook() throws Exception {
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

        when(bookService.addBook(bookIn)).thenReturn(bookOut);

        // Act
        ResponseEntity<Book> response = bookApiController.addBook(bookIn);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookOut, response.getBody());
        verify(bookService, times(1)).addBook(bookIn);
    }

    @Test
    public void test_addBook_no_author() throws Exception {
        // Arrange
        Book book = new Book()
                .title("Ulysses")
                .publicationYear(1922);

        when(bookService.addBook(book)).thenReturn(null);

        // Act
        ResponseEntity<Book> response = bookApiController.addBook(book);

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    public void test_borrowBook() throws Exception {
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

        when(bookService.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(book);

        // Act
        ResponseEntity<Book> response = bookApiController.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
        verify(bookService, times(1))
                .borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }

    @Test
    public void test_borrowBook_not_found() {
        // Arrange
        when(bookService.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenThrow(new NoSuchElementException("Book is already borrowed"));

        // Act
        ResponseEntity<Book> response = bookApiController.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1))
                .borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }

    @Test
    public void test_borrowBook_bad_request() {
        // Arrange
        when(bookService.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenThrow(new IllegalArgumentException("Book is already borrowed"));

        // Act
        ResponseEntity<Book> response = bookApiController.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(bookService, times(1))
                .borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
    }


}
