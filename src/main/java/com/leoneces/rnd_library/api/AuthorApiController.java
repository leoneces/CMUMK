package com.leoneces.rnd_library.api;

import com.leoneces.rnd_library.model.Author;


import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-28T20:35:55.083763+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Controller
@RequestMapping("${openapi.libraryManagementSystem.base-path:}")
public class AuthorApiController implements AuthorApi {

    private final NativeWebRequest request;

    @Autowired
    private AuthorService authorService;

    @Autowired
    public AuthorApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        Optional<Author> addedAuthor = Optional.ofNullable(authorService.addAuthor(author));
        return addedAuthor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Author> getAuthorById(@PathVariable String id){
        Optional<Author> author = authorService.findById(id);
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}