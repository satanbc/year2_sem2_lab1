package com.labs.task.controller;

import java.util.ArrayList;
import java.util.List;

import com.labs.task.Entities.Author;
import com.labs.task.Entities.Book;
import com.labs.task.Entities.Character;
import com.labs.task.Entities.Series;
import com.labs.task.service.AuthorService;
import com.labs.task.service.BookService;
import com.labs.task.service.CharacterService;
import com.labs.task.service.SeriesService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;

/// Головний Контроллер проекту
@Controller
@RequestMapping("/books")
public class BookController {

	private BookService bookService;
	private SeriesService seriesService;
	private AuthorService authorService;
	private CharacterService characterService;

	public BookController(BookService theBookService, SeriesService seriesService, AuthorService authorService, CharacterService characterService) {
		bookService = theBookService;
		this.seriesService = seriesService;
		this.authorService = authorService;
		this.characterService = characterService;
	}

	/// Метод для виводу списку
	@GetMapping("/list")
	public String listBooks(Model theModel){

		List<Book> theBooks = bookService.findAll();

		theModel.addAttribute("books", theBooks);

		return "books/list-books";
	}

	/// Метод для виводу форми додавання
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel){

		Book theBook = new Book();

		for(int i = 1; i <= 3; i++){
			theBook.addCharacter(new Character());
		}

		theModel.addAttribute("book", theBook);

		return "books/book-form";
	}

	/// Метод для виводу форми сортування
	@GetMapping("/showFormForSort")
	public String showFormForSort(Model theModel){

		List<Book> bookList = bookService.findAll();

		theModel.addAttribute("books", bookList);

		return "books/book-sort";
	}

	/// Метод для пошуку
	@RequestMapping(path = {"/books/list","/search"})
	public String search(Model theModel, String keyword) {
		if(keyword!=null) {
			List<Book> books = bookService.getByKeyword(keyword);
			theModel.addAttribute("books", books);
		}else {
			List<Book> books = bookService.findAll();
			theModel.addAttribute("books", books);}
		return "books/search-books";
	}

	/// Метод для зберігання
	@PostMapping("/save")
	public String saveBook(@ModelAttribute("book") Book theBook){

		int index = -1;
		boolean isMainCharacterNew = true;
		String authorName = theBook.getAuthor().getName();

		Author tempAuthor;
		Series series = new Series("Not in any series");
		Series tempSeriesObject = null;

		List<Integer> characterIdList1 = new ArrayList<>();
		List<Character> characterList = new ArrayList<>();


		SessionFactory factory = new Configuration()
				.configure()
				.addAnnotatedClass(Author.class)
				.addAnnotatedClass(Series.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(Character.class)
				.buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();


		try{
			Query<Integer> query1 = session.createQuery("select author.id from Author author where author.name=:name");
			query1 = query1.setParameter("name", authorName);
			index = query1.getSingleResult();
		}catch (NoResultException e){}
		try{
			Query<Integer> query2 = session.createQuery("select character.id from Character character where character.role=:role");
			query2 = query2.setParameter("role", "main");
			characterIdList1 = query2.getResultList();

			query2 = query2.setParameter("role", "secondary");
			List<Integer> characterIdList2 = query2.getResultList();

			characterIdList1.addAll(characterIdList2);
		}catch (NoResultException e){}

		for (int m : characterIdList1){
			characterList.add(characterService.findById(m));
		}


		if (index == -1){
			tempAuthor = new Author(authorName);
		}
		else {
			tempAuthor = authorService.findById(index);
		}


		for (Character cha : characterList){
			for (int t = 0; t < theBook.getCharacters().size(); t++){
				if(cha.getName().equals(theBook.getCharacters().get(t).getName())){
					tempSeriesObject = cha.getBooks().get(0).getSeries();
					isMainCharacterNew = false;
					break;
				}
			}
		}


		if (isMainCharacterNew) {
			for (int t = 0; t < theBook.getCharacters().size(); t++) {
				if (theBook.getCharacters().get(t).getRole().equals("main") || theBook.getCharacters().get(t).getRole().equals("secondary")) {
					String name = theBook.getCharacters().get(t).getName();
					series = new Series(name + " adventures");
					tempAuthor.addSeries(series);
					authorService.save(tempAuthor);
					series.setAuthor(tempAuthor);
					series.addBook(theBook);
					seriesService.save(series);
					break;
				}
			}
		}else 
			series = tempSeriesObject;


		tempAuthor.addBook(theBook);
		theBook.setAuthor(tempAuthor);
		theBook.setSeries(series);

		bookService.save(theBook);

		return "redirect:/books/list";
	}

	/// Метод для видалення
	@GetMapping("/delete")
	public  String delete(@RequestParam("bookId") int theId){

		Book theBook = bookService.findById(theId);
		Series theSeries = seriesService.findById(theBook.getSeries().getId());
		Author theAuthor = authorService.findById(theBook.getAuthor().getId());
		List<Character> characterList = theBook.getCharacters();

		int seriesId = theBook.getSeries().getId();
		int authorId = theBook.getAuthor().getId();

		bookService.deleteById(theId);

		if (theSeries.getBooks().size() < 1)
			seriesService.deleteById(seriesId);

		if (theAuthor.getBooks().size() < 1)
			authorService.deleteById(authorId);

		try{
			for (Character character : characterList){
				characterService.deleteById(character.getId());
			}
		}catch (EmptyResultDataAccessException e){

		}

		return "redirect:/books/list";
	}
}









