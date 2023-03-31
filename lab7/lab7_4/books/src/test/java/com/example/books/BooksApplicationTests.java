package com.example.books;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class BooksApplicationTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
	
	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
			.withUsername("duke")
			.withPassword("password")
			.withDatabaseName("test");
	
	@Autowired
	private BookRepository repository;
	
	@LocalServerPort
	int randomServerPort;
	
	
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
		
		RestAssuredMockMvc.given()
				.mockMvc(mockMvc)
				.contentType("application/json")
				.body(book)
				.when()
				.post("/books")
				.then()
				.statusCode(201);
		
		Response response = given().port(randomServerPort)
				.contentType("application/json")
				.body(book)
				.when()
				.post("/books");
		
		response.then().statusCode(201)
				.body("id", notNullValue())
				.body("name", equalTo(book.getName()));
		
		long id = response.jsonPath().getLong("id");
		
		response = given().port(randomServerPort)
				.when()
				.get("/books/" + id);
		
		response.then().statusCode(200)
				.body("id", equalTo((int) id))
				.body("name", equalTo(book.getName()));
		
		Book retrievedBook = response.getBody().as(Book.class);
		assertEquals(book, retrievedBook);
	}
}