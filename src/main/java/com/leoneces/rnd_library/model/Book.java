package com.leoneces.rnd_library.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.annotation.Generated;

/**
 * Book
 */

@JsonTypeName("book")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-27T10:29:52.747471+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
public class Book {

  private String bookID;

  private String title;

  private Integer year;

  private Author author;

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

  public Book year(Integer year) {
    this.year = year;
    return this;
  }

  /**
   * Year of publication of the Book
   * @return year
   */
  
  @Schema(name = "Year", example = "1922", description = "Year of publication of the Book", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Year")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
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
        Objects.equals(this.year, book.year) &&
        Objects.equals(this.author, book.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bookID, title, year, author);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Book {\n");
    sb.append("    bookID: ").append(toIndentedString(bookID)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
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

