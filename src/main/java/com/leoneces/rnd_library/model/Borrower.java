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
 * Borrower
 */

@JsonTypeName("borrower")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-29T00:25:52.581503+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Entity
public class Borrower {

  @Id
  private String borrowerID;

  private String name;

  private String phone;

  public Borrower borrowerID(String borrowerID) {
    this.borrowerID = borrowerID;
    return this;
  }

  /**
   * ID of the Borrower
   * @return borrowerID
   */
  
  @Schema(name = "BorrowerID", example = "7d978e18-9b82-4908-b7a9-5dd2dd7b349e", description = "ID of the Borrower", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("BorrowerID")
  public String getBorrowerID() {
    return borrowerID;
  }

  public void setBorrowerID(String borrowerID) {
    this.borrowerID = borrowerID;
  }

  public Borrower name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Full name of the Borrower
   * @return name
   */
  
  @Schema(name = "Name", example = "Michael Daniel Higgins", description = "Full name of the Borrower", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Borrower phone(String phone) {
    this.phone = phone;
    return this;
  }

  /**
   * Full phone number of the Borrower
   * @return phone
   */
  
  @Schema(name = "Phone", example = "+353 1 677 0095", description = "Full phone number of the Borrower", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Phone")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Borrower borrower = (Borrower) o;
    return Objects.equals(this.borrowerID, borrower.borrowerID) &&
        Objects.equals(this.name, borrower.name) &&
        Objects.equals(this.phone, borrower.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(borrowerID, name, phone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Borrower {\n");
    sb.append("    borrowerID: ").append(toIndentedString(borrowerID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
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

