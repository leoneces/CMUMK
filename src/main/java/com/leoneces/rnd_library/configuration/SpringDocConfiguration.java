package com.leoneces.rnd_library.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Library Management System API")
                                .description("This is a library API. It allow clients to: - **List Books**: Retrieve a list of all available books. - **Add Book**: Add a new book to the library. - **Borrow Book**: Borrow a book by specifying the book ID and borrower ID. - **Create Author**: Add a new author. - **Get Author**: Retrieve details of a specific author. - **Borrower Endpoints**: - **Create Borrower**: Register a new borrower. - **Get Borrower**: Retrieve details of a specific borrower. - **Borrowed Books**: Retrieve the list of books borrowed by a specific borrower. ")
                                .contact(
                                        new Contact()
                                                .name("Leone Cesca")
                                                .email("leoneces@gmail.com")
                                )
                                .version("1.0.0")
                )
        ;
    }
}