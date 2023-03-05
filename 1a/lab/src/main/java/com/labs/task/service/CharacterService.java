package com.labs.task.service;

import com.labs.task.Entities.Character;

import java.util.List;

/// Сервіс персонажів
public interface CharacterService {

	public List<Character> findAll();
	
	public Character findById(int theId);
	
	public void save(Character theCharacter);
	
	public void deleteById(int theId);
	
}
