package io.cucumber.skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;


public class Library {
	private final List<Book> store = new ArrayList<>();
	
	public void addBook(final Book book) {
		store.add(book);
	}
	
	public List<Book> findBooks(final LocalDate from, final LocalDate to) {
		return store.stream()
				.filter(book -> book.getPublished().isAfter(from.minusDays(1)) && book.getPublished().isBefore(to.plusDays(1)))
				.sorted((b1, b2) -> b2.getPublished().compareTo(b1.getPublished()))
				.collect(Collectors.toList());
	}
}