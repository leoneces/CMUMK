package com.leoneces.rnd_library.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoneces.rnd_library.model.Author;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Tag("integration")
public class AuthorApiIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_addAuthor() throws Exception {
        String authorJson = "{\"Name\": \"James Augustine Aloysius Joyce\", \"Country\": \"Ireland\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author author = objectMapper.readValue(responseContent, Author.class);

        Assertions.assertDoesNotThrow( () -> { UUID.fromString(author.getAuthorID()); });
        Assertions.assertEquals("James Augustine Aloysius Joyce", author.getName());
        Assertions.assertEquals("Ireland", author.getCountry());
    }

    @Test
    public void test_addAuthor_bad_request() throws Exception {
        String authorJson = "{ this is a bad request }";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void test_getAuthorById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", "257f4259-9e90-4f29-871d-eea3a4386da2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Author author = objectMapper.readValue(responseContent, Author.class);

        Assertions.assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", author.getAuthorID());
        Assertions.assertEquals("James Joyce", author.getName());
        Assertions.assertEquals("Ireland", author.getCountry());
    }

    @Test
    public void test_getAuthorById_not_found() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", "cacbd521-4f95-4835-ac62-b23aaec87201")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}
