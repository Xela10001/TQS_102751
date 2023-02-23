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

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

	private Integer upperBound;
	private LinkedList<T> stack;
	
	public TqsStack() {
		this(null);
	}
	
	public TqsStack(Integer upperBound) {
		this.upperBound = upperBound;
		this.stack = new LinkedList<>();
	}
	
	public void push(T element) {
		if (upperBound != null && size() == upperBound)
			throw new IllegalStateException();
		
		this.stack.push(element);
	}
	
	public int size() {
		return stack.size();
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public T pop() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		return stack.pop();
	}
	
	public T peek() {
		if (isEmpty())
			throw new NoSuchElementException();
		
		return stack.peek();
	}
}
