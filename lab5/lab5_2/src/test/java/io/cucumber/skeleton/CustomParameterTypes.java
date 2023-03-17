package io.cucumber.skeleton;

import io.cucumber.java.ParameterType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomParameterTypes {
	@ParameterType(".*")
	public LocalDate localDate(String input) {
		return LocalDate.parse(input, DateTimeFormatter.ofPattern("dd MMMM yyyy"));
	}
}