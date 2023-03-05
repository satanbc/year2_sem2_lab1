package com.labs.task.service;

import java.util.List;

import com.labs.task.Entities.Book;

public interface BookService {

	public List<Book> findAll();
	
	public Book findById(int theId);
	
	public void save(Book theBook);
	
	public void deleteById(int theId);

	public List<Book> getByKeyword(String keyword);
}
