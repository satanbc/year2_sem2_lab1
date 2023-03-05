package com.labs.task.service;

import com.labs.task.Entities.Character;

import java.util.List;

/// Сервіс персонажів
public interface CharacterService {

	/// Метод для отримання всіх персонажів
	public List<Character> findAll();

	/// Метод для пошуку за Id
	public Character findById(int theId);

	/// Метод для збереження
	public void save(Character theCharacter);

	/// Метод для видалення за Id
	public void deleteById(int theId);
}
