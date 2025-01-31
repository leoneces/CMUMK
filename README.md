# Daily History
- Day 1 (half day):
  - Created openapi design using Swagger Editor
  - Created repo
- Day 2:
  - Generated the Spring Boot code using openapi-generator
  - Adjustments to the openapi.yaml
  - Fixed dependencies and integration with Spring Boot
- Day 3: 
  - Started H2 integration with JPA
  - Made some changes on Model adding annotations for IDs and Foreign fields
  - Worked on Borrower and Author Controllers and Services classes
  - H2 Configurations
- Day 4:
  - Added Borrowing Books and List Borrowed Books workflows, integrated with H2
  - Added Unit Tests for Models, Services and Controllers
  - Added Integration Tests for all endpoints
  - Added Integration End-to-End tests
- Day 5:
  - Code Cleanup
  - Updated README.MD

# Usage Instructions:

From the project root:
- To run the application locally: `mvn spring-boot:run`
- To run Unit Tests `mvn test`
- To run Unit and Integration Tests `mvn install`
- If you want to run as a docker container:
  - From the project root directory build with `docker build -t rnd_library-app .` 
  - Start the container with `docker run -p 8080:8080 rnd_library-app`.


# Implementation Details
## REST API
- Designed the `openapi.yaml` on Swagger Editor.
- Based on `openapi.yaml`, I used openapi-generator to generate most of the classes for spring boot:
- Implemented Controller endpoints on `BorrowerApiController`, `AuthorApiController` and `BookApiController`.
- Fully implemented Services classes for Author, Book and Borrower. 
- Implemented repository interfaces for Author, Book and Borrower.
- Added annotation to models `@Entity`, `@ID`, `@ManyToOne` and `@JoinColumn`, fixing issues that I was having with 
JPA creating the tables correctly

## Test
- Test cases are tagged as unit or integration. E2E tests are part of integration tag.

## Database
- H2 database is accessible at `http://localhost:8080/h2-console`. Username: `rnd_library` Password: `library123`
- H2 database is reset on each run. It's already populated with some data. See `schema.sql` and `data.sql` on `resources` directory.

# To Do:
- Figure out why some annotations are not being generated by openapi-generator-cli
- Hide fields that are not in use: book -> borrower
- When returning Status Codes (404, 400), no message is being returned on the Body. Could modify the model to include a message when errors occur.
- Controller Unit Tests are starting spring-boot to run (probably because of@SpringBootTest). Unit tests should run quickly.
