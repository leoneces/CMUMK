package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoneces.rnd_library.model.Author;
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

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Tag("integration")
public class AuthorApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_addAuthor() throws Exception {
        String authorJson = "{\"Name\": \"James Augustine Aloysius Joyce\", \"Country\": \"Ireland\"}";

        MvcResult result = mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author author = objectMapper.readValue(responseContent, Author.class);

        assertDoesNotThrow( () -> { UUID.fromString(author.getAuthorID()); });
        assertEquals("James Augustine Aloysius Joyce", author.getName());
        assertEquals("Ireland", author.getCountry());
    }

    @Test
    public void test_addAuthor_bad_request() throws Exception {
        String authorJson = "{ this is a bad request }";

        MvcResult result = mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void test_getAuthorById() throws Exception {
        MvcResult result = mockMvc.perform(get("/author/{id}", "257f4259-9e90-4f29-871d-eea3a4386da2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author author = objectMapper.readValue(responseContent, Author.class);

        assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", author.getAuthorID());
        assertEquals("James Joyce", author.getName());
        assertEquals("Ireland", author.getCountry());
    }

    @Test
    public void test_getAuthorById_not_found() throws Exception {
        MvcResult result = mockMvc.perform(get("/author/{id}", "cacbd521-4f95-4835-ac62-b23aaec87201")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
