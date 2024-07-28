-- Create the rnd_library database
CREATE SCHEMA rnd_library;

-- Use the rnd_library database
SET SCHEMA rnd_library;

-- Schema for Authors
CREATE TABLE author (
    AuthorID VARCHAR(36) PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Country VARCHAR(255) NOT NULL
);

-- Schema for Borrowers
CREATE TABLE borrower (
    BorrowerID VARCHAR(36) PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Phone VARCHAR(50) NOT NULL
);

-- Schema for Books
CREATE TABLE book (
    BookID VARCHAR(36) PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    PublicationYear INT NOT NULL,
    AuthorID VARCHAR(36),
    BorrowedBy VARCHAR(36),
    FOREIGN KEY (AuthorID) REFERENCES author(AuthorID),
    FOREIGN KEY (BorrowedBy) REFERENCES borrower(BorrowerID)
);

