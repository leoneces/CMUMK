package com.leoneces.rnd_library.unit.model;

import com.leoneces.rnd_library.model.Author;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Tag("unit")
public class AuthorTest {

    @Test
    public void test_authorID(){
        Author author = new Author().authorID("257f4259-9e90-4f29-871d-eea3a4386da2");
        assertEquals("257f4259-9e90-4f29-871d-eea3a4386da2", author.getAuthorID());
        author.setAuthorID("ac0166f-4433-4c65-bb17-7dca0bbb7e60");
        assertEquals("ac0166f-4433-4c65-bb17-7dca0bbb7e60", author.getAuthorID());
    }

    @Test
    public void test_name(){
        Author author = new Author().name("James Joyce");
        assertEquals("James Joyce", author.getName());
        author.setName("John O'Hara");
        assertEquals("John O'Hara", author.getName());
    }

    @Test
    public void test_country(){
        Author author = new Author().country("Ireland");
        assertEquals("Ireland", author.getCountry());
        author.setCountry("Brazil");
        assertEquals("Brazil", author.getCountry());
    }

    @Test
    public void test_equal_hash(){
        Author author1 = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Author author2 = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Author author3 = new Author()
                .authorID("cac0166f-4433-4c65-bb17-7dca0bbb7e60")
                .name("Bram Stoker")
                .country("Ireland");

        assertEquals(author1, author2);
        assertNotEquals(author1, author3);
        assertEquals(author1.hashCode(), author2.hashCode());
        assertNotEquals(author1.hashCode(), author3.hashCode());
    }
}
