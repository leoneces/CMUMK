package com.leoneces.rnd_library.unit.model;

import com.leoneces.rnd_library.model.Borrower;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class BorrowerTest {

    @Test
    public void test_borrowerID(){
        Borrower borrower = new Borrower().borrowerID("820f1bfc-c986-4cb8-a2e3-78c873cdd746");
        assertEquals("820f1bfc-c986-4cb8-a2e3-78c873cdd746", borrower.getBorrowerID());
        borrower.setBorrowerID("97acab6a-240f-4007-bebd-d47ac85b40e6");
        assertEquals("97acab6a-240f-4007-bebd-d47ac85b40e6", borrower.getBorrowerID());
    }

    @Test
    public void test_name(){
        Borrower borrower = new Borrower().name("Michael D. Higgins");
        assertEquals("Michael D. Higgins", borrower.getName());
        borrower.setName("John O'Hara");
        assertEquals("John O'Hara", borrower.getName());
    }

    @Test
    public void test_phone(){
        Borrower borrower = new Borrower().phone("+353 1 677 0095");
        assertEquals("+353 1 677 0095", borrower.getPhone());
        borrower.setPhone("+353 94 906 4000");
        assertEquals("+353 94 906 4000", borrower.getPhone());
    }

    @Test
    public void test_equals_hash(){
        Borrower borrower1 = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Borrower borrower2 = new Borrower()
                .borrowerID("7d978e18-9b82-4908-b7a9-5dd2dd7b349e")
                .name("Michael D. Higgins")
                .phone("+353 1 677 0095");

        Borrower borrower3 = new Borrower()
                .borrowerID("fec174ff-ed17-4b26-b1e3-11c8d01fb5f4")
                .name("John O''Hara")
                .phone("+353 94 906 4000");

        assertEquals(borrower1, borrower2);
        assertNotEquals(borrower1, borrower3);
        assertEquals(borrower1.hashCode(), borrower2.hashCode());
        assertNotEquals(borrower1.hashCode(), borrower3.hashCode());
    }

}
