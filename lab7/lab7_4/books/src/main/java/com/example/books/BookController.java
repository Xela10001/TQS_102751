package com.example.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
	
	private final BookRepository bookRepository;
	
	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookRepository.save(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		Optional<Book> optionalBook = bookRepository.findById(id);
		return optionalBook.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	// Implement other CRUD operations as needed
}
