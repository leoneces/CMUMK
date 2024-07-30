package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Tag("integration")
public class EndToEndIT {

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
        Assertions.assertTrue(optBook.isPresent());
        Assertions.assertEquals(book, optBook.get());

        // Borrow a book (using previously created Borrower)
        borrowBook(book, borrower);

        // List all books borrowed by the Borrower (should be 1)
        List<Book> borrowedBooks = getBorrowedBooksByBorrower(borrower.getBorrowerID());

        // Asserts
        Assertions.assertEquals(1, borrowedBooks.size());
        Book borrowedBook = borrowedBooks.get(0);
        Assertions.assertEquals(book.getBookID(), borrowedBook.getBookID());
        Assertions.assertEquals(book.getTitle(), borrowedBook.getTitle());
        Assertions.assertEquals(book.getPublicationYear(), borrowedBook.getPublicationYear());
        Assertions.assertEquals(book.getAuthor(), borrowedBook.getAuthor());
        Assertions.assertEquals(borrower, borrowedBook.getBorrowedBy());
    }

    private List<Book> getBorrowedBooksByBorrower(String borrowerID) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/borrower/{id}/borrowed_books", borrowerID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        return objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});
    }

    private void borrowBook(Book book, Borrower borrower) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/book/{id}/borrow/{borrower_id}",
                        book.getBookID(), borrower.getBorrowerID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Book borrowedBook = objectMapper.readValue(responseContent, Book.class);
        Assertions.assertEquals(book.getBookID(), borrowedBook.getBookID());
        Assertions.assertEquals(book.getTitle(), borrowedBook.getTitle());
        Assertions.assertEquals(book.getPublicationYear(), borrowedBook.getPublicationYear());
        Assertions.assertEquals(book.getAuthor(), borrowedBook.getAuthor());
        Assertions.assertEquals(borrower, borrowedBook.getBorrowedBy());
    }

    private List<Book> listBooks() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();

        return objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});
    }

    private Book addBook(Author author) throws Exception {
        String bookJson = "{\"Title\": \"Confessions of an Irish Rebel\", \"PublicationYear\": 1965, " +
                " \"Author\": {\"AuthorID\": \"" + author.getAuthorID() + "\"}," +
                " \"BorrowedBy\": null}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Book book = objectMapper.readValue(responseContent, Book.class);

        // Asserts
        // Book
        Assertions.assertDoesNotThrow( () -> { UUID.fromString(book.getBookID()); });
        Assertions.assertEquals("Confessions of an Irish Rebel", book.getTitle());
        Assertions.assertEquals(1965, book.getPublicationYear());
        // Author
        Assertions.assertEquals(author, book.getAuthor());
        // Borrower
        Assertions.assertNull(book.getBorrowedBy());

        return book;
    }

    private Borrower createBorrower() throws Exception {
        // Create Borrower
        String borrowerJson = "{ \"Name\": \"Leone Francisco Cesca\", \"Phone\": \"+353 86 211 554433\" }";

        MvcResult createBorrowerResult = mockMvc.perform(MockMvcRequestBuilders.post("/borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowerJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String createBorrowerResponseContent = createBorrowerResult.getResponse().getContentAsString();
        Borrower borrower = objectMapper.readValue(createBorrowerResponseContent, Borrower.class);

        Assertions.assertDoesNotThrow( () -> { UUID.fromString(borrower.getBorrowerID()); });
        Assertions.assertEquals("Leone Francisco Cesca", borrower.getName());
        Assertions.assertEquals("+353 86 211 554433", borrower.getPhone());

        return borrower;
    }

    private void getBorrower(Borrower borrower) throws Exception {
        // Get Borrower
        MvcResult getBorrowerResult = mockMvc.perform(MockMvcRequestBuilders.get("/borrower/{id}", borrower.getBorrowerID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String getBorrowerResponseContent = getBorrowerResult.getResponse().getContentAsString();
        Borrower getBorrower = objectMapper.readValue(getBorrowerResponseContent, Borrower.class);
        Assertions.assertEquals(borrower, getBorrower);
    }

    private Author createAuthor() throws Exception {
        // Create Author
        String authorJson = "{\"Name\": \"Brendan Behan\", \"Country\": \"Ireland\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author author = objectMapper.readValue(responseContent, Author.class);

        Assertions.assertDoesNotThrow( () -> { UUID.fromString(author.getAuthorID()); });
        Assertions.assertEquals("Brendan Behan", author.getName());
        Assertions.assertEquals("Ireland", author.getCountry());

        return author;
    }

    private void getAuthor(Author author) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", author.getAuthorID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author getAuthor = objectMapper.readValue(responseContent, Author.class);
        Assertions.assertEquals(author, getAuthor);
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
