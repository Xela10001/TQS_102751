package com.example;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ClassTest {
	
	private final String jsonPlaceholder = "https://jsonplaceholder.typicode.com/todos";

	@Test
	public void TODOsAreAvailableTest() {
		get(jsonPlaceholder).then().statusCode(200);
	}
	
	@Test
	public void TODO4ReturnsCorrectString() {
		get(jsonPlaceholder).then().body("[3].title", equalTo("et porro tempora"));
	}
	
	@Test
	public void getAllTODOsIncluding198And199() {
		get(jsonPlaceholder).then().body("id", hasItems(198, 199)).body("", hasSize(200));
	}
	
	@Test
	public void timeTakenToGetAllTODOs() {
		get(jsonPlaceholder).then().body("", hasSize(200)).time(lessThan(2L*1000));
	}
	
}
