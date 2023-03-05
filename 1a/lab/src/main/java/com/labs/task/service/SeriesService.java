package com.labs.task.service;

import com.labs.task.Entities.Series;

import java.util.List;

/// Сервіс серій
public interface SeriesService {

	/// Метод для отримання всіх серій
	public List<Series> findAll();

	/// Метод для пошуку за Id
	public Series findById(int theId);

	/// Метод для збереження
	public void save(Series theSeries);

	/// Метод для видалення за Id
	public void deleteById(int theId);
}
