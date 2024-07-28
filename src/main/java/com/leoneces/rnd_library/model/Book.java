package com.leoneces.rnd_library.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Borrower;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Book
 */

@JsonTypeName("book")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-29T00:25:52.581503+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Entity
public class Book {

  @Id
  private String bookID;

  private String title;

  private Integer publicationYear;

  @ManyToOne
  @JoinColumn(name = "AuthorID")
  private Author author;

  @ManyToOne
  @JoinColumn(name = "BorrowerID")
  private Borrower borrowedBy;

  public Book bookID(String bookID) {
    this.bookID = bookID;
    return this;
  }

  /**
   * ID of the Book
   * @return bookID
   */
  
  @Schema(name = "BookID", example = "018b2f19-e79e-7d6a-a56d-29feb6211b04", description = "ID of the Book", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("BookID")
  public String getBookID() {
    return bookID;
  }

  public void setBookID(String bookID) {
    this.bookID = bookID;
  }

  public Book title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Title of the Book
   * @return title
   */
  
  @Schema(name = "Title", example = "Ulysses", description = "Title of the Book", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Book publicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
    return this;
  }

  /**
   * Year of publication of the Book
   * @return publicationYear
   */
  
  @Schema(name = "PublicationYear", example = "1922", description = "Year of publication of the Book", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("PublicationYear")
  public Integer getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
  }

  public Book author(Author author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   */
  @Valid 
  @Schema(name = "Author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Author")
  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public Book borrowedBy(Borrower borrowedBy) {
    this.borrowedBy = borrowedBy;
    return this;
  }

  /**
   * Get borrowedBy
   * @return borrowedBy
   */
  @Valid 
  @Schema(name = "BorrowedBy", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("BorrowedBy")
  public Borrower getBorrowedBy() {
    return borrowedBy;
  }

  public void setBorrowedBy(Borrower borrowedBy) {
    this.borrowedBy = borrowedBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Book book = (Book) o;
    return Objects.equals(this.bookID, book.bookID) &&
        Objects.equals(this.title, book.title) &&
        Objects.equals(this.publicationYear, book.publicationYear) &&
        Objects.equals(this.author, book.author) &&
        Objects.equals(this.borrowedBy, book.borrowedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookID, title, publicationYear, author, borrowedBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Book {\n");
    sb.append("    bookID: ").append(toIndentedString(bookID)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    publicationYear: ").append(toIndentedString(publicationYear)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    borrowedBy: ").append(toIndentedString(borrowedBy)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

