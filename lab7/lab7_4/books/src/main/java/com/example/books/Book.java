package com.example.books;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	String name;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Book(String name) {
		this.name = name;
	}
	
	public Book() {
	
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Book book = (Book) o;
		
		if (!id.equals(book.id)) return false;
		return Objects.equals(name, book.name);
	}
	
	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
