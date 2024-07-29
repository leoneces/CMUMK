## Daily History
- Day 1:
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
  - Added Unit Tests for Models 

## Usage Instructions:

To run the application:
```shell
mvn spring-boot:run
```
To run Unit Tests
```shell
mvn test -Punit-tests
```
To run Integration Tests
```shell
mvn test -Pintegration-tests
```



## Implementation Details
- Based on openapi.yaml, I used openapi-generator to generate most of classes for spring boot:

```shell
openapi-generator generate \
-i ~/Downloads/openapi.yaml \
-g spring \
-o ~/IdeaProjects/output/ \
--invoker-package com.leoneces.rnd_library \
--api-package com.leoneces.rnd_library.api \
--model-package com.leoneces.rnd_library.model \
-p useBeanValidation=true,java8=false,javaVersion=17 
```
- Implemented Controller endpoints on `BorrowerApiController`, `AuthorApiController` and `BookApiController`.
- Fully implemented Services classes for Author, Book and Borrower. 
- Implemented repository interfaces for Author, Book and Borrower.
- Adjusted the models adding `@Entity`, `@ID`, `@ManyToOne` and `@JoinColumn` annotations, fixing issues that I was having with 
JPA creating the tables correctly 

## To Do:
- Figure out why some annotations are not being generated by openapi-generator-cli
- Field validations (like year)
- Hide fields that are not in use: book -> borrower
- Check how to get a proper message when returning Errors like 400
- Check all warnings on code
- Clean unused classes