-- Initial data for Authors
INSERT INTO author (AuthorID, Title, Country)
            VALUES ('257f4259-9e90-4f29-871d-eea3a4386da2', 'James Joyce', 'Ireland');

-- Initial data for Books
INSERT INTO book (BookID, Title, Year, AuthorID)
            VALUES ('018b2f19-e79e-7d6a-a56d-29feb6211b04', 'Ulysses', 1922, '257f4259-9e90-4f29-871d-eea3a4386da2');

-- Initial data for Borrowers
INSERT INTO borrower (BorrowerID, Name, Phone)
            VALUES ('7d978e18-9b82-4908-b7a9-5dd2dd7b349e', 'Michael D. Higgins', '+353 1 677 0095');

-- Initial data for Borrowed Books
INSERT INTO borrowed_book (BookID, BorrowerID)
            VALUES ('018b2f19-e79e-7d6a-a56d-29feb6211b04', '7d978e18-9b82-4908-b7a9-5dd2dd7b349e');
