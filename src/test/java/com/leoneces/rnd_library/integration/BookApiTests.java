package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoneces.rnd_library.model.Book;
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
public class BookApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_getBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Book> bookList = objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});

        String bookId = "025cb29e-bb0f-4011-a465-baa8d7b092cb";
        Optional<Book> optBook = bookList.stream()
                .filter(b -> bookId.equals((b.getBookID())))
                .findFirst();

        assertTrue(optBook.isPresent());
        Book book = optBook.get();

        // Asserts
        assertEquals(6, bookList.size());
        // Book
        assertEquals("025cb29e-bb0f-4011-a465-baa8d7b092cb", book.getBookID());
        assertEquals("The Primrose Path", book.getTitle());
        assertEquals(1875, book.getPublicationYear());
        // Author
        assertEquals("cac0166f-4433-4c65-bb17-7dca0bbb7e60", book.getAuthor().getAuthorID());
        assertEquals("Bram Stoker", book.getAuthor().getName());
        assertEquals("Ireland", book.getAuthor().getCountry());
        // Borrower
        assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", book.getBorrowedBy().getBorrowerID());
        assertEquals("Michael D. Higgins", book.getBorrowedBy().getName());
        assertEquals("+353 1 677 0095", book.getBorrowedBy().getPhone());
    }

    @Test
    public void test_addBook() throws Exception {
        String bookJson = "{\"Title\": \"Finnegans Wake\", \"PublicationYear\": 1939, " +
                " \"Author\": {\"AuthorID\": \"257f4259-9e90-4f29-871d-eea3a4386da2\"}," +
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
        assertEquals("Finnegans Wake", book.getTitle());
        assertEquals(1939, book.getPublicationYear());
        // Author
        assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", book.getAuthor().getAuthorID());
        assertEquals("James Joyce", book.getAuthor().getName());
        assertEquals("Ireland", book.getAuthor().getCountry());
    }

    @Test
    public void test_addBook_bad_request() throws Exception {
        String bookJson = "{this is a bad request}";

        MvcResult result = mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void test_borrowBook() throws Exception {
        MvcResult result = mockMvc.perform(post("/book/{id}/borrow/{borrower_id}", "018b2f19-e79e-7d6a-a56d-29feb6211b04", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Book book = objectMapper.readValue(responseContent, Book.class);

        // Asserts
        // Book
        assertEquals("018b2f19-e79e-7d6a-a56d-29feb6211b04", book.getBookID());
        assertEquals("Ulysses", book.getTitle());
        assertEquals(1922, book.getPublicationYear());
        // Author
        assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", book.getAuthor().getAuthorID());
        assertEquals("James Joyce", book.getAuthor().getName());
        assertEquals("Ireland", book.getAuthor().getCountry());
        // Borrower
        assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", book.getBorrowedBy().getBorrowerID());
        assertEquals("Michael D. Higgins", book.getBorrowedBy().getName());
        assertEquals("+353 1 677 0095", book.getBorrowedBy().getPhone());
    }

    @Test
    public void test_borrowBook_bad_request() throws Exception {
        MvcResult result = mockMvc.perform(post("/book/{id}/borrow/{borrower_id}",
                        "025cb29e-bb0f-4011-a465-baa8d7b092cb", "fec174ff-ed17-4b26-b1e3-11c8d01fb5f4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}
