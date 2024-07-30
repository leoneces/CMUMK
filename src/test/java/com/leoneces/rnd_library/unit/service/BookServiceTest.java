package com.leoneces.rnd_library.unit.service;

import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.AuthorRepository;
import com.leoneces.rnd_library.repository.BookRepository;
import com.leoneces.rnd_library.repository.BorrowerRepository;
import com.leoneces.rnd_library.service.BookService;
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
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void test_getAllBooks() {
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

        when(bookRepository.findAll()).thenReturn(bookList);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(bookList, result);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void test_addBook() {
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book = new Book()
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        when(authorRepository.findById("257f4259-9e90-4f29-871d-eea3a4386da2"))
                .thenReturn(Optional.ofNullable(author));
        when(bookRepository.save(book)).
                thenReturn(book);

        // Act & Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertDoesNotThrow(() -> resultHolder.set(bookService.addBook(book)));
        Book result = resultHolder.get();

        // Other Assertions
        assertNotNull(result);
        assertDoesNotThrow(() -> {
            UUID.fromString(result.getBookID());
        });
        assertEquals("Ulysses", result.getTitle());
        assertEquals(1922, result.getPublicationYear());
        assertNull(result.getBorrowedBy());
        verify(bookRepository, times(1)).save(book);
        verify(authorRepository, times(1)).findById("257f4259-9e90-4f29-871d-eea3a4386da2");
    }

    @Test
    public void test_addBook_with_invalid_author() {
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book = new Book()
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        when(authorRepository.findById("257f4259-9e90-4f29-871d-eea3a4386da2"))
                .thenReturn(Optional.empty());
//        when(bookRepository.save(book)).thenReturn(book);

        // Act & Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertThrows(Exception.class, () -> resultHolder.set(bookService.addBook(book)));
        Book result = resultHolder.get();

        // Other Assertions
        assertNull(result);
        verify(bookRepository, times(0)).save(book);
        verify(authorRepository, times(1)).findById("257f4259-9e90-4f29-871d-eea3a4386da2");
    }

    @Test
    public void test_addBook_without_author() {
        // Arrange
        Book book = new Book()
                .title("Ulysses")
                .publicationYear(1922);

        // Act & Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertThrows(Exception.class, () -> resultHolder.set(bookService.addBook(book)));
        Book result = resultHolder.get();

        // Other Assertions
        assertNull(result);
        verify(bookRepository, times(0)).save(book);
        verify(authorRepository, times(0)).findById(any());
    }

    @Test
    public void test_borrowBook() {
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        when(bookRepository.findById("018b2f19-e79e-7d6a-a56d-29feb6211b04"))
                .thenReturn(Optional.ofNullable(book));
        when(borrowerRepository.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.ofNullable(borrower));
        assertNotNull(book);
        when(bookRepository.save(book))
                .thenReturn(book);

        // Act and Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertNotNull(borrower);
        assertDoesNotThrow(() -> resultHolder.set(bookService.borrowBook(book.getBookID(), borrower.getBorrowerID())));
        Book result = resultHolder.get();

        // Other Assertions
        assertEquals("018b2f19-e79e-7d6a-a56d-29feb6211b04", result.getBookID());
        assertEquals("Ulysses", result.getTitle());
        assertEquals(1922, result.getPublicationYear());
        assertEquals(author, result.getAuthor());
        assertEquals(borrower, result.getBorrowedBy());
        verify(bookRepository, times(1)).findById("018b2f19-e79e-7d6a-a56d-29feb6211b04");
        verify(borrowerRepository, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void test_borrowBook_invalid_book_id() {
        // Arrange
        Borrower borrower = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        when(bookRepository.findById("018b2f19-e79e-7d6a-a56d-29feb6211b04"))
                .thenReturn(Optional.empty());
        when(borrowerRepository.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.ofNullable(borrower));

        // Act and Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertNotNull(borrower);
        assertThrows(Exception.class, () -> resultHolder.set(bookService.borrowBook("018b2f19-e79e-7d6a-a56d-29feb6211b04", borrower.getBorrowerID())));
        Book result = resultHolder.get();

        // Other Assertions
        assertNull(result);
        verify(bookRepository, times(1)).findById("018b2f19-e79e-7d6a-a56d-29feb6211b04");
        verify(borrowerRepository, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    public void test_borrowBook_invalid_borrower_id() {
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Book book = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author);

        when(bookRepository.findById("018b2f19-e79e-7d6a-a56d-29feb6211b04"))
                .thenReturn(Optional.ofNullable(book));
        when(borrowerRepository.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.empty());

        // Act and Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertNotNull(book);
        assertThrows(Exception.class, () -> resultHolder.set(bookService.borrowBook(book.getBookID(), "7d978e18-9b82-4908-b7a9-5dd2dd7b349e")));
        Book result = resultHolder.get();

        // Other Assertions
        assertNull(result);
        verify(bookRepository, times(1)).findById("018b2f19-e79e-7d6a-a56d-29feb6211b04");
        verify(borrowerRepository, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    public void test_borrowBook_already_borrowed() {
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

        when(bookRepository.findById("018b2f19-e79e-7d6a-a56d-29feb6211b04"))
                .thenReturn(Optional.ofNullable(book));
        when(borrowerRepository.findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e"))
                .thenReturn(Optional.ofNullable(borrower));

        // Act and Assert
        AtomicReference<Book> resultHolder = new AtomicReference<>();
        assertNotNull(borrower);
        assertNotNull(book);
        assertThrows(Exception.class, () -> resultHolder.set(bookService.borrowBook(book.getBookID(), borrower.getBorrowerID())));
        Book result = resultHolder.get();

        // Other Assertions
        assertNull(result);
        verify(bookRepository, times(1)).findById("018b2f19-e79e-7d6a-a56d-29feb6211b04");
        verify(borrowerRepository, times(1)).findById("7d978e18-9b82-4908-b7a9-5dd2dd7b349e");
        verify(bookRepository, times(0)).save(any());
    }
}
