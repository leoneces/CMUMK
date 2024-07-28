-- Initial data for Borrowers
INSERT INTO borrower (BorrowerID, Name, Phone)
            VALUES ('7d978e18-9b82-4908-b7a9-5dd2dd7b349e', 'Michael D. Higgins', '+353 1 677 0095'),
                   ('fec174ff-ed17-4b26-b1e3-11c8d01fb5f4', 'John O''Hara', '+353 94 906 4000'),
                   ('8445057d-3741-4df6-8f26-a065065d2ab2', 'Albert Dolan', '+353 91 509000');

-- Initial data for Authors
INSERT INTO author (AuthorID, Name, Country)
            VALUES ('257f4259-9e90-4f29-871d-eea3a4386da2', 'James Joyce', 'Ireland'),
                   ('cac0166f-4433-4c65-bb17-7dca0bbb7e60', 'Bram Stoker', 'Ireland'),
                   ('c3aacf06-f0a5-470c-9369-b53ce2616c98', 'Maeve Binchy', 'Ireland'),
                   ('41c211f3-f1d8-4206-b209-ac956810587e', 'Vitalino Cesca', 'Brazil');                   ;

-- Initial data for Books
--INSERT INTO book (BookID, Title, Year, AuthorID)
--            VALUES ('018b2f19-e79e-7d6a-a56d-29feb6211b04', 'Ulysses', 1922, '257f4259-9e90-4f29-871d-eea3a4386da2');


-- Initial data for Borrowed Books
--INSERT INTO borrowed_book (BookID, BorrowerID)
--            VALUES ('018b2f19-e79e-7d6a-a56d-29feb6211b04', '7d978e18-9b82-4908-b7a9-5dd2dd7b349e');
