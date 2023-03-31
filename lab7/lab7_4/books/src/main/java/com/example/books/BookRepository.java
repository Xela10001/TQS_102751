package com.example.books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	
	@Query("SELECT b FROM Book b WHERE b.id = :id")
	Optional<Book> findById(@Param("id") @NonNull Long id);
}
