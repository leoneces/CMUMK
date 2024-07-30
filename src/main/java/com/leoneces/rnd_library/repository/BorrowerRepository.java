package com.leoneces.rnd_library.repository;

import com.leoneces.rnd_library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, String> {
}
