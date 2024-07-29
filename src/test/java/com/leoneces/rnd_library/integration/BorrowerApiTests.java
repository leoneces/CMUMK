package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoneces.rnd_library.model.Borrower;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Tag("integration")
public class BorrowerApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createBorrower() throws Exception {
        String borrowerJson = "{ \"Name\": \"Leone Francisco Cesca\", \"Phone\": \"+353 86 211 554433\" }";

        MvcResult result = mockMvc.perform(post("/borrower")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowerJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Borrower borrower = objectMapper.readValue(responseContent, Borrower.class);

        assertDoesNotThrow( () -> { UUID.fromString(borrower.getBorrowerID()); });
        assertEquals("Leone Francisco Cesca", borrower.getName());
        assertEquals("+353 86 211 554433", borrower.getPhone());
    }

    @Test
    public void createBorrower_bad_request() throws Exception {
        String borrowerJson = "{ this is bad data }";

        MvcResult result = mockMvc.perform(post("/borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowerJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getBorrower() throws Exception {
        MvcResult result = mockMvc.perform(get("/borrower/{id}", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Borrower borrower = objectMapper.readValue(responseContent, Borrower.class);

        assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", borrower.getBorrowerID());
        assertEquals("Michael D. Higgins", borrower.getName());
        assertEquals("+353 1 677 0095", borrower.getPhone());
    }

    @Test
    public void getBorrower_not_found() throws Exception {
        MvcResult result = mockMvc.perform(get("/borrower/{id}", "7ecfa7d7-8f1a-4497-b15a-4fb119519255")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getBorrowedBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/borrower/{id}/borrowed_books", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Book> bookList = objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});

        assertEquals(1, bookList.size());
        Book book = bookList.get(0);
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
    public void getBorrowedBooks_not_found() throws Exception {
        MvcResult result = mockMvc.perform(get("/borrower/{id}/borrowed_books", "7ecfa7d7-8f1a-4497-b15a-4fb119519255")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
