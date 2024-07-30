package com.leoneces.rnd_library.unit.service;

import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.repository.AuthorRepository;
import com.leoneces.rnd_library.service.AuthorService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void test_findById(){
        // Arrange
        Author author = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");
        when(authorRepository.findById("257f4259-9e90-4f29-871d-eea3a4386da2"))
                .thenReturn(Optional.ofNullable(author));

        // Act
        Optional<Author> result = authorService.findById("257f4259-9e90-4f29-871d-eea3a4386da2");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(author, result.get());
        verify(authorRepository, times(1)).findById("257f4259-9e90-4f29-871d-eea3a4386da2");
    }

    @Test
    public void test_addAuthor(){
        // Arrange
        Author author = new Author()
                .name("James Joyce")
                .country("Ireland");
        when(authorRepository.save(any(Author.class)))
                .thenReturn(author);

        // Act
        Author result = authorService.addAuthor(author);

        // Assert
        assertNotNull(result);
        assertDoesNotThrow(() -> { UUID.fromString(result.getAuthorID()); });
        assertEquals("James Joyce", result.getName());
        assertEquals("Ireland", result.getCountry());
        verify(authorRepository, times(1)).save(author);
    }
}
