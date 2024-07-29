package com.leoneces.rnd_library.unit.api;

import com.leoneces.rnd_library.api.AuthorApiController;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit")
@SpringBootTest
public class AuthorApiTest {

    @InjectMocks
    private AuthorApiController authorApiController;

    @Mock
    private AuthorService authorService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_addAuthor(){
        // Arrange
        Author authorIn = new Author()
                .name("James Joyce")
                .country("Ireland");

        Author authorOut = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        when(authorService.addAuthor(authorIn))
                .thenReturn(authorOut);

        // Act
        ResponseEntity<Author> response = authorApiController.addAuthor(authorIn);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", response.getBody().getAuthorID());
        assertEquals("James Joyce", response.getBody().getName());
        assertEquals("Ireland", response.getBody().getCountry());
        verify(authorService, times(1)).addAuthor(authorIn);
    }

    @Test
    public void test_addAuthor_empty_data(){
        when(authorService.addAuthor(any(Author.class)))
                .thenReturn(null);

        // Act
        ResponseEntity<Author> response = authorApiController.addAuthor(new Author());

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(authorService, times(1)).addAuthor(any(Author.class));
    }

    @Test
    public void test_getAuthorById(){
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        when(authorService.findById("257f4259-9e90-4f29-871d-eea3a4386da2"))
                .thenReturn(Optional.ofNullable(author));

        // Act
        ResponseEntity<Author> response = authorApiController.getAuthorById("257f4259-9e90-4f29-871d-eea3a4386da2");

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", response.getBody().getAuthorID());
        assertEquals("James Joyce", response.getBody().getName());
        assertEquals("Ireland", response.getBody().getCountry());
        verify(authorService, times(1)).findById("257f4259-9e90-4f29-871d-eea3a4386da2");
    }

    @Test
    public void test_getAuthorById_not_found(){
        // Arrange
        when(authorService.findById("257f4259-9e90-4f29-871d-eea3a4386da2"))
                .thenReturn(Optional.ofNullable(null));

        // Act
        ResponseEntity<Author> response = authorApiController.getAuthorById("257f4259-9e90-4f29-871d-eea3a4386da2");

        // Assert
        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(authorService, times(1)).findById("257f4259-9e90-4f29-871d-eea3a4386da2");
    }

}
