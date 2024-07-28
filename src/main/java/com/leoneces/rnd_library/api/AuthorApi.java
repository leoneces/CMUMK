/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.7.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.leoneces.rnd_library.api;

import com.leoneces.rnd_library.model.Author;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-28T20:35:55.083763+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Validated
@Tag(name = "Author Endpoints", description = "Authors that write books")
public interface AuthorApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /author
     * Add a new author.
     *
     * @param author  (optional)
     * @return Record Successfully Added (status code 201)
     *         or Bad request (status code 400)
     */
    @Operation(
        operationId = "addAuthor",
        description = "Add a new author.",
        tags = { "Author Endpoints" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Record Successfully Added", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/author",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Author> addAuthor(
        @Parameter(name = "Author", description = "") @Valid @RequestBody(required = false) Author author
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"AuthorID\" : \"257f4259-9e90-4f29-871d-eea3a4386da2\", \"Title\" : \"James Augustine Aloysius Joyce\", \"Country\" : \"Ireland\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /author/{id}
     * Retrieve details of a specific author.
     *
     * @param id  (required)
     * @return Success Response (status code 200)
     *         or Author not found (status code 404)
     */
    @Operation(
        operationId = "getAuthorById",
        description = "Retrieve details of a specific author.",
        tags = { "Author Endpoints" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Success Response", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class))
            }),
            @ApiResponse(responseCode = "404", description = "Author not found")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/author/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<Author> getAuthorById(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"AuthorID\" : \"257f4259-9e90-4f29-871d-eea3a4386da2\", \"Title\" : \"James Augustine Aloysius Joyce\", \"Country\" : \"Ireland\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
