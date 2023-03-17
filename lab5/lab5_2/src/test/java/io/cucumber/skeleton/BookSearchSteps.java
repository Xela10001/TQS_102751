package io.cucumber.skeleton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();
	
	@Given("a/another book with the title {string}, written by {string}, published in {localDate}")
	public void addNewBook(final String title, final String author, final LocalDate published) {
		Book book = new Book(title, author, published);
		library.addBook(book);
	}
	
	@When("the customer searches for books published between {int} and {int}")
	public void setSearchParameters(final int from, final int to) {
		result = library.findBooks(LocalDate.of(from, 1, 1), LocalDate.of(to, 12, 31));
	}
	
	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertEquals(result.size(), booksFound);
	}
	
	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertEquals(result.get(position - 1).getTitle(), title);
	}
}
