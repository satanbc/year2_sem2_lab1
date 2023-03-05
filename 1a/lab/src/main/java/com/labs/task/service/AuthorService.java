package com.labs.task.service;

import com.labs.task.Entities.Author;

import java.util.List;

/// Сервіс авторів
public interface AuthorService {

	/// Метод для отримання всіх авторів
	public List<Author> findAll();

	/// Метод для пошуку за Id
	public Author findById(int theId);

	/// Метод для збереження
	public void save(Author theAuthor);

	/// Метод для видалення за Id
	public void deleteById(int theId);
}
