package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.leoneces.rnd_library.model.Book;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Tag("integration")
public class BorrowerApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createBorrower() throws Exception {
        String borrowerJson = "{ \"Name\": \"Leone Francisco Cesca\", \"Phone\": \"+353 86 211 554433\" }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/borrower")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowerJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Borrower borrower = objectMapper.readValue(responseContent, Borrower.class);

        Assertions.assertDoesNotThrow( () -> { UUID.fromString(borrower.getBorrowerID()); });
        Assertions.assertEquals("Leone Francisco Cesca", borrower.getName());
        Assertions.assertEquals("+353 86 211 554433", borrower.getPhone());
    }

    @Test
    public void createBorrower_bad_request() throws Exception {
        String borrowerJson = "{ this is bad data }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowerJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getBorrowerById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/borrower/{id}", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Borrower borrower = objectMapper.readValue(responseContent, Borrower.class);

        Assertions.assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", borrower.getBorrowerID());
        Assertions.assertEquals("Michael D. Higgins", borrower.getName());
        Assertions.assertEquals("+353 1 677 0095", borrower.getPhone());
    }

    @Test
    public void getBorrower_not_found() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/borrower/{id}", "7ecfa7d7-8f1a-4497-b15a-4fb119519255")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    public void getBorrowedBooks() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/borrower/{id}/borrowed_books", "7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        List<Book> bookList = objectMapper.readValue(responseContent, new TypeReference<List<Book>>(){});

        Assertions.assertEquals(1, bookList.size());
        Book book = bookList.get(0);
        // Book
        Assertions.assertEquals("025cb29e-bb0f-4011-a465-baa8d7b092cb", book.getBookID());
        Assertions.assertEquals("The Primrose Path", book.getTitle());
        Assertions.assertEquals(1875, book.getPublicationYear());
        // Author
        Assertions.assertEquals("cac0166f-4433-4c65-bb17-7dca0bbb7e60", book.getAuthor().getAuthorID());
        Assertions.assertEquals("Bram Stoker", book.getAuthor().getName());
        Assertions.assertEquals("Ireland", book.getAuthor().getCountry());
        // Borrower
        Assertions.assertEquals("7d978e18-9b82-4908-b7a9-5dd2dd7b349e", book.getBorrowedBy().getBorrowerID());
        Assertions.assertEquals("Michael D. Higgins", book.getBorrowedBy().getName());
        Assertions.assertEquals("+353 1 677 0095", book.getBorrowedBy().getPhone());
    }

    @Test
    public void getBorrowedBooks_not_found() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/borrower/{id}/borrowed_books", "7ecfa7d7-8f1a-4497-b15a-4fb119519255")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
