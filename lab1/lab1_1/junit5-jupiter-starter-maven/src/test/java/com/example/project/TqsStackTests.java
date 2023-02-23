/*
 * Copyright 2015-2022 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.example.project.TqsStack;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTests {
	TqsStack<Integer> stack;
	TqsStack<Integer> stack2;
	
	@BeforeEach
	void createStack() {
		stack = new TqsStack<>();
		stack2 = new TqsStack<>(1);
	}
	
	@Test
	@DisplayName("Is empty on construction")
	void isEmptyOnConstruction() {
		assertTrue(stack.isEmpty());
	}
	
	@Test
	@DisplayName("Is size 0 on construction")
	void hasSize0OnConstruction() {
		assertEquals(0, stack.size());
	}

	@Test
	@DisplayName("Is size N after N pushes")
	void pushNValues_thenSizeIsN() {
		stack.push(1);
		stack.push(3);
		stack.push(6);
		assertFalse(stack.isEmpty());
		assertEquals(3, stack.size());
		
	}

	@DisplayName("Is pop equal to last push")
	@Test
	void pushThenPop() {
		stack.push(123);
		assertEquals(123, stack.pop());
	}
	
	@DisplayName("Is peek equal to last push")
	@Test
	void pushThenPeek() {
		stack.push(234);
		int size = stack.size();
		assertEquals(234, stack.peek());
		assertEquals(size, stack.size());
	}
	
	@DisplayName("Is empty after pushing and popping N")
	@Test
	void pushN_popN_isEmpty() {
		stack.push(9);
		stack.push(1);
		stack.pop();
		stack.pop();
		assertTrue(stack.isEmpty());
		assertEquals(0, stack.size());
	}

	@DisplayName("Throws exception if empty stack is popped")
	@Test
	void popFromEmptyStack() {
		assertThrows(NoSuchElementException.class, stack::pop);
	}
	
	@DisplayName("Throws exception if empty stack is peeked")
	@Test
	void peekFromEmptyStack() {
		assertThrows(NoSuchElementException.class, stack::peek);
	}
	
	@DisplayName("Throws exception if full stack is pushed")
	@Test
	void pushOntoFullStack() {
		stack2.push(6);
		assertThrows(IllegalStateException.class, () -> {
			stack2.push(7);
		});
	}
	
}
