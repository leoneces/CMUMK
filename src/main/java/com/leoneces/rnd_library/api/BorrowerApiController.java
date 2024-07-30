package com.leoneces.rnd_library.api;

import com.leoneces.rnd_library.model.Book;
import com.leoneces.rnd_library.model.Borrower;


import com.leoneces.rnd_library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-07-28T20:35:55.083763+01:00[Europe/Dublin]", comments = "Generator version: 7.7.0")
@Controller
@RequestMapping("${openapi.libraryManagementSystem.base-path:}")
public class BorrowerApiController implements BorrowerApi {

    private final NativeWebRequest request;

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    public BorrowerApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }


    @Override
    public ResponseEntity<Borrower> addBorrower(@RequestBody Borrower borrower){
        Optional<Borrower> addedBook = Optional.ofNullable(borrowerService.addBorrower(borrower));
        return addedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Override
    public ResponseEntity<Borrower> getBorrowerById(@PathVariable String id){
        Optional<Borrower> borrower = borrowerService.findById(id);
        return borrower.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Book>> getBorrowedBooksByBorrowerId(@PathVariable("id") String id){
        try{
            List<Book> books = borrowerService.getBorrowedBooksByBorrowerId(id);
            return ResponseEntity.ok(books);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
