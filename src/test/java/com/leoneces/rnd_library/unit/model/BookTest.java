package com.leoneces.rnd_library.unit.model;

import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Tag("unit")
public class BookTest {

    @Test
    public void test_bookID(){
        Book book = new Book().bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04");
        assertEquals("018b2f19-e79e-7d6a-a56d-29feb6211b04", book.getBookID());
        book.setBookID("d9b2ce8f-12ad-49a1-b87b-7ac588fc0b22");
        assertEquals("d9b2ce8f-12ad-49a1-b87b-7ac588fc0b22", book.getBookID());
    }

    @Test
    public void test_title(){
        Book book = new Book().title("Ulysses");
        assertEquals("Ulysses", book.getTitle());
        book.setBookID("Exiles");
        assertEquals("Exiles", book.getBookID());
    }

    @Test
    public void test_publicationYear(){
        Book book = new Book().publicationYear(1922);
        assertEquals(1922, book.getPublicationYear());
        book.setPublicationYear(1918);
        assertEquals(1918, book.getPublicationYear());
    }

    @Test
    public void test_author(){
        Author author1 = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Author author2 = new Author()
                .authorID("cac0166f-4433-4c65-bb17-7dca0bbb7e60")
                .name("Bram Stoker")
                .country("Ireland");

        Book book = new Book().author(author1);
        assertEquals(author1, book.getAuthor());
        book.setAuthor(author2);
        assertEquals(author2, book.getAuthor());
    }

    @Test
    public void test_borrower(){
        Borrower borrower1 = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Borrower borrower2 = new Borrower()
                .borrowerID("fec174ff-ed17-4b26-b1e3-11c8d01fb5f4")
                .name("John O''Hara")
                .phone("+353 94 906 4000");

        Book book = new Book().borrowedBy(borrower1);
        assertEquals(borrower1, book.getBorrowedBy());
        book.setBorrowedBy(borrower2);
        assertEquals(borrower2, book.getBorrowedBy());
    }

    @Test
    public void test_equals_hash(){
        Author author1 = new Author()
                .authorID("257f4259-9e90-4f29-871d-eea3a4386da2")
                .name("James Joyce")
                .country("Ireland");

        Author author2 = new Author()
                .authorID("cac0166f-4433-4c65-bb17-7dca0bbb7e60")
                .name("Bram Stoker")
                .country("Ireland");

        Borrower borrower1 = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Borrower borrower2 = new Borrower()
                .borrowerID("fec174ff-ed17-4b26-b1e3-11c8d01fb5f4")
                .name("John O''Hara")
                .phone("+353 94 906 4000");

        Book book1 = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author1)
                .borrowedBy(borrower1);

        Book book2 = new Book()
                .bookID("018b2f19-e79e-7d6a-a56d-29feb6211b04")
                .title("Ulysses")
                .publicationYear(1922)
                .author(author1)
                .borrowedBy(borrower1);

        Book book3 = new Book()
                .bookID("25cb29e-bb0f-4011-a465-baa8d7b092cb")
                .title("The Primrose Path")
                .publicationYear(1875)
                .author(author2)
                .borrowedBy(borrower2);

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }
}
