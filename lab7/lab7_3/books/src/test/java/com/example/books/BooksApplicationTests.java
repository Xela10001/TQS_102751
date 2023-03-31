package com.example.books;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@RunWith(SpringRunner.class)
class BooksApplicationTests {
	
	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
			.withUsername("duke")
			.withPassword("password")
			.withDatabaseName("test");
	
	@Autowired
	private BookRepository repository;
	
	// requires Spring Boot >= 2.2.6
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}
	
	@Test
	@Order(1)
	void contextLoads() {
		Book book = new Book("A");
		repository.save(book);
		repository.flush();
		System.out.println("Context loads!");
	}
	
	@Test
	@Order(2)
	void insertThenReadBook()
	{
		Book book = new Book("A");
		book.setName("B");
		repository.save(book);
		Optional<Book> retrieved = repository.findById(book.getId());
		assertTrue(retrieved.isPresent());
		Book retrieved2 = retrieved.get();
		assertEquals(book, retrieved2);
	}
}