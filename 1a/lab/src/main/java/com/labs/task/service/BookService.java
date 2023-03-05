package com.labs.task.service;

import java.util.List;

import com.labs.task.Entities.Author;
import com.labs.task.Entities.Book;

/// Сервіс книжок
public interface BookService {

	/// Метод для отримання всіх книжок
	public List<Book> findAll();

	/// Метод для пошуку за Id
	public Book findById(int theId);

	/// Метод для збереження
	public void save(Book theBook);

	/// Метод для видалення за Id
	public void deleteById(int theId);

	/// Метод для отримання книжок за кодовим словом
	List<Book> getByKeyword(String keyword);
}
