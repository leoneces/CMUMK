package com.leoneces.rnd_library.repository;

import com.leoneces.rnd_library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}
