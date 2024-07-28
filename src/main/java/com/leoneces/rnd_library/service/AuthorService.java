package com.leoneces.rnd_library.service;

import com.leoneces.rnd_library.model.Author;
import com.leoneces.rnd_library.model.Borrower;
import com.leoneces.rnd_library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service

public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Optional<Author> findById(String id){
        return authorRepository.findById(id);
    }

    public Author addAuthor(Author author) {
        author.setAuthorID(UUID.randomUUID().toString());
        return authorRepository.save(author);
    }
}
