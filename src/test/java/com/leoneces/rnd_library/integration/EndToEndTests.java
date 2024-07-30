package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Tag("integration")
public class EndToEndTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_end_to_end() throws Exception {
        // Create and get Borrower
        Borrower borrower = createBorrower();
        getBorrower(borrower);

        // Create and get Author
        Author author = createAuthor();
        getAuthor(author);

        // Create Book (using the previously created Author)
        Book book = addBook(author);

        // List all books and check if the created book is there
        List<Book> bookList = listBooks();
        Optional<Book> optBook = bookList.stream()
                .filter(b -> book.getBookID().equals((b.getBookID())))
                .findFirst();
        assertTrue(optBook.isPresent());
        assertEquals(book, optBook.get());

        // Borrow a book (using previously created Borrower)
        borrowBook(book, borrower);

        // List all books borrowed by the Borrower (should be 1)
        List<Book> borrowedBooks = getBorrowedBooksByBorrower(borrower.getBorrowerID());

        // Asserts
        assertEquals(1, borrowedBooks.size());
        Book borrowedBook = borrowedBooks.get(0);
        assertEquals(book.getBookID(), borrowedBook.getBookID());
        assertEquals(book.getTitle(), borrowedBook.getTitle());
        assertEquals(book.getPublicationYear(), borrowedBook.getPublicationYear());
        assertEquals(book.getAuthor(), borrowedBook.getAuthor());
        assertEquals(borrower, borrowedBook.getBorrowedBy());
    }

    private List<Book> getBorrowedBooksByBorrower(String borrowerID) throws Exception {
        MvcResult result = mockMvc.perform(get("/borrower/{id}/borrowed_books", borrowerID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        return objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});
    }

    private void borrowBook(Book book, Borrower borrower) throws Exception {
        MvcResult result = mockMvc.perform(post("/book/{id}/borrow/{borrower_id}",
                        book.getBookID(), borrower.getBorrowerID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Book borrowedBook = objectMapper.readValue(responseContent, Book.class);
        assertEquals(book.getBookID(), borrowedBook.getBookID());
        assertEquals(book.getTitle(), borrowedBook.getTitle());
        assertEquals(book.getPublicationYear(), borrowedBook.getPublicationYear());
        assertEquals(book.getAuthor(), borrowedBook.getAuthor());
        assertEquals(borrower, borrowedBook.getBorrowedBy());
    }

    private List<Book> listBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        return objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});
    }

    private Book addBook(Author author) throws Exception {
        String bookJson = "{\"Title\": \"Confessions of an Irish Rebel\", \"PublicationYear\": 1965, " +
                " \"Author\": {\"AuthorID\": \"" + author.getAuthorID() + "\"}," +
                " \"BorrowedBy\": null}";

        MvcResult result = mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Book book = objectMapper.readValue(responseContent, Book.class);

        // Asserts
        // Book
        assertDoesNotThrow( () -> { UUID.fromString(book.getBookID()); });
        assertEquals("Confessions of an Irish Rebel", book.getTitle());
        assertEquals(1965, book.getPublicationYear());
        // Author
        assertEquals(author, book.getAuthor());
        // Borrower
        assertNull(book.getBorrowedBy());

        return book;
    }

    private Borrower createBorrower() throws Exception {
        // Create Borrower
        String borrowerJson = "{ \"Name\": \"Leone Francisco Cesca\", \"Phone\": \"+353 86 211 554433\" }";

        MvcResult createBorrowerResult = mockMvc.perform(post("/borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowerJson))
                .andExpect(status().isOk())
                .andReturn();

        String createBorrowerResponseContent = createBorrowerResult.getResponse().getContentAsString();
        Borrower borrower = objectMapper.readValue(createBorrowerResponseContent, Borrower.class);

        assertDoesNotThrow( () -> { UUID.fromString(borrower.getBorrowerID()); });
        assertEquals("Leone Francisco Cesca", borrower.getName());
        assertEquals("+353 86 211 554433", borrower.getPhone());

        return borrower;
    }

    private void getBorrower(Borrower borrower) throws Exception {
        // Get Borrower
        MvcResult getBorrowerResult = mockMvc.perform(get("/borrower/{id}", borrower.getBorrowerID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String getBorrowerResponseContent = getBorrowerResult.getResponse().getContentAsString();
        Borrower getBorrower = objectMapper.readValue(getBorrowerResponseContent, Borrower.class);
        assertEquals(borrower, getBorrower);
    }

    private Author createAuthor() throws Exception {
        // Create Author
        String authorJson = "{\"Name\": \"Brendan Behan\", \"Country\": \"Ireland\"}";
        MvcResult result = mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author author = objectMapper.readValue(responseContent, Author.class);

        assertDoesNotThrow( () -> { UUID.fromString(author.getAuthorID()); });
        assertEquals("Brendan Behan", author.getName());
        assertEquals("Ireland", author.getCountry());

        return author;
    }

    private void getAuthor(Author author) throws Exception {
        MvcResult result = mockMvc.perform(get("/author/{id}", author.getAuthorID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author getAuthor = objectMapper.readValue(responseContent, Author.class);
        assertEquals(author, getAuthor);
    }
    /*
    - **Create Borrower**: Register a new borrower.
    - **Get Borrower**: Retrieve details of a specific borrower.

    - **Create Author**: Add a new author.
    - **Get Author**: Retrieve details of a specific author.

    - **Add Book**: Add a new book to the library.

    - **List Books**: Retrieve a list of all available books.
    - **Borrow Book**: Borrow a book by specifying the book ID and borrower ID.

    - **Borrowed Books**: Retrieve the list of books borrowed by a specific borrower.

    }


     */
}
