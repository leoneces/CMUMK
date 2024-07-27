/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.7.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.leoneces.rnd_library.api;

import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.BorrowedBook;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-27T10:29:52.747471+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Validated
@Tag(name = "Book Endpoints", description = "Books that can be borrowed")
public interface BookApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /book
     * Add a new book to the library.
     *
     * @param book  (optional)
     * @return Record Successfully Added (status code 201)
     *         or Bad Request (status code 400)
     */
    @Operation(
        operationId = "addBook",
        description = "Add a new book to the library.",
        tags = { "Book Endpoints" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Record Successfully Added", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/book",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Book> addBook(
        @Parameter(name = "Book", description = "") @Valid @RequestBody(required = false) Book book
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"BookID\" : \"018b2f19-e79e-7d6a-a56d-29feb6211b04\", \"Year\" : 1922, \"Title\" : \"Ulysses\", \"Author\" : { \"AuthorID\" : \"257f4259-9e90-4f29-871d-eea3a4386da2\", \"Title\" : \"James Augustine Aloysius Joyce\", \"Country\" : \"Ireland\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /book/{book_id}/borrow/{borrower_id}
     * Borrow a book by specifying the book ID and borrower ID.
     *
     * @param bookId  (required)
     * @param borrowerId  (required)
     * @return Success Response (status code 201)
     *         or Bad Request (status code 400)
     */
    @Operation(
        operationId = "borrowBook",
        description = "Borrow a book by specifying the book ID and borrower ID.",
        tags = { "Book Endpoints" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Success Response", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = BorrowedBook.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/book/{book_id}/borrow/{borrower_id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<BorrowedBook> borrowBook(
        @Parameter(name = "book_id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("book_id") String bookId,
        @Parameter(name = "borrower_id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("borrower_id") String borrowerId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"Book\" : { \"BookID\" : \"018b2f19-e79e-7d6a-a56d-29feb6211b04\", \"Year\" : 1922, \"Title\" : \"Ulysses\", \"Author\" : { \"AuthorID\" : \"257f4259-9e90-4f29-871d-eea3a4386da2\", \"Title\" : \"James Augustine Aloysius Joyce\", \"Country\" : \"Ireland\" } }, \"Borrower\" : { \"BorrowerID\" : \"7d978e18-9b82-4908-b7a9-5dd2dd7b349e\", \"Phone\" : \"+353 1 677 0095\", \"Name\" : \"Michael Daniel Higgins\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /book
     * Retrieve a list of all available books.
     *
     * @return Success Response (status code 200)
     */
    @Operation(
        operationId = "getBooks",
        description = "Retrieve a list of all available books.",
        tags = { "Book Endpoints" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success Response", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Book.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/book",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<Book>> getBooks(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"BookID\" : \"018b2f19-e79e-7d6a-a56d-29feb6211b04\", \"Year\" : 1922, \"Title\" : \"Ulysses\", \"Author\" : { \"AuthorID\" : \"257f4259-9e90-4f29-871d-eea3a4386da2\", \"Title\" : \"James Augustine Aloysius Joyce\", \"Country\" : \"Ireland\" } }, { \"BookID\" : \"018b2f19-e79e-7d6a-a56d-29feb6211b04\", \"Year\" : 1922, \"Title\" : \"Ulysses\", \"Author\" : { \"AuthorID\" : \"257f4259-9e90-4f29-871d-eea3a4386da2\", \"Title\" : \"James Augustine Aloysius Joyce\", \"Country\" : \"Ireland\" } } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
