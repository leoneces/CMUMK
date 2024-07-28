Daily History
- Day 1:
  - Created openapi design using Swagger Editor
  - Created repo
- Day 2:
  - Generated the Spring Boot code using openapi-generator
  - Adjustments to the openapi.yaml
  - Fixed dependencies and integration with Spring Boot
- Day 3: Started H2 integration with JPA
  - Made some changes on Model adding annotations for IDs and Foreign fields
  - Worked on Books Controller and Service
  - H2 Configurations

Implementation Details
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
- Adjusted the models adding `@ID`, `@ManyToOne` and `@JoinColumn` annotations, fixing issues that I was having with 
JPA creating the tables correctly 