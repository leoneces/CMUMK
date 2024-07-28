package com.leoneces.rnd_library.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Author
 */

@JsonTypeName("author")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-28T20:35:55.083763+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Entity
public class Author {

  @Id
  private String authorID;

  private String title;

  private String country;

  public Author authorID(String authorID) {
    this.authorID = authorID;
    return this;
  }

  /**
   * ID of the Author
   * @return authorID
   */
  
  @Schema(name = "AuthorID", example = "257f4259-9e90-4f29-871d-eea3a4386da2", description = "ID of the Author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("AuthorID")
  public String getAuthorID() {
    return authorID;
  }

  public void setAuthorID(String authorID) {
    this.authorID = authorID;
  }

  public Author title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Full name of the Author
   * @return title
   */
  
  @Schema(name = "Title", example = "James Augustine Aloysius Joyce", description = "Full name of the Author", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Author country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Country where the Author was born
   * @return country
   */
  
  @Schema(name = "Country", example = "Ireland", description = "Country where the Author was born", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Country")
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Author author = (Author) o;
    return Objects.equals(this.authorID, author.authorID) &&
        Objects.equals(this.title, author.title) &&
        Objects.equals(this.country, author.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorID, title, country);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Author {\n");
    sb.append("    authorID: ").append(toIndentedString(authorID)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
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

