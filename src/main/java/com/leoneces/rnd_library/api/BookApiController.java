package com.leoneces.rnd_library.api;


import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-27T10:29:52.747471+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Controller
@RequestMapping("${openapi.libraryManagementSystem.base-path:}")
public class BookApiController implements BookApi {

    private final NativeWebRequest request;

    @Autowired
    private BookService bookService;

    @Autowired
    public BookApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<Book> addBook(Book book) {
        Optional<Book> addedBook = Optional.ofNullable(bookService.addBook(book));
        return addedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
