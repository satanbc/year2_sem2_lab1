package com.labs.task.controller;

import com.labs.task.Entities.Book;
import com.labs.task.service.BookService;
import com.labs.task.sorting.Sorting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

/// Контроллер сортування
@Controller
@RequestMapping("/sorting")
public class SortingController {

	private BookService bookService;

	public SortingController(BookService theBookService) {
		bookService = theBookService;
	}

	@GetMapping("/insertionSort")
	public String insertionSort(Model theModel){

		StopWatch watch = new StopWatch();
		watch.start();

		Book[] booksArray = bookService.findAll().toArray(new Book[0]);
		Sorting sort = new Sorting(booksArray);

		sort.insertionSort(booksArray);

		theModel.addAttribute("booksArray", booksArray);

		watch.stop();
		System.out.println("Total execution of insertionSort: " + watch.getTotalTimeMillis() + " milliseconds");

		return "books/list-books-sorted";
	}

	@GetMapping("/quickSort")
	public String quickSort(Model theModel){

		StopWatch watch = new StopWatch();
		watch.start();

		Book[] booksArray = bookService.findAll().toArray(new Book[0]);
		Sorting sort = new Sorting(booksArray);

		try {
			sort.quickSort(booksArray, 0, booksArray.length-1);
		}catch (ArrayIndexOutOfBoundsException e){

		}

		theModel.addAttribute("booksArray", booksArray);

		watch.stop();
		System.out.println("Total execution of quickSort: " + watch.getTotalTimeMillis() + " milliseconds");

		return "books/list-books-sorted";
	}

	@GetMapping("/mergeSort")
	public String mergeSort(Model theModel){

		StopWatch watch = new StopWatch();
		watch.start();

		Book[] booksArray = bookService.findAll().toArray(new Book[0]);
		Sorting sort = new Sorting(booksArray);

		sort.mergeSort(booksArray, booksArray.length);

		theModel.addAttribute("booksArray", booksArray);

		watch.stop();
		System.out.println("Total execution of mergeSort: " + watch.getTotalTimeMillis() + " milliseconds");

		return "books/list-books-sorted";
	}

	@GetMapping("/selectionSort")
	public String selectionSort(Model theModel){

		StopWatch watch = new StopWatch();
		watch.start();

		Book[] booksArray = bookService.findAll().toArray(new Book[0]);
		Sorting sort = new Sorting(booksArray);

		sort.selectionSort(booksArray);

		theModel.addAttribute("booksArray", booksArray);

		watch.stop();
		System.out.println("Total execution of selectionSort: " + watch.getTotalTimeMillis() + " milliseconds");

		return "books/list-books-sorted";
	}

	@GetMapping("/shuttleSort")
	public String shuttleSort(Model theModel){

		StopWatch watch = new StopWatch();
		watch.start();

		Book[] booksArray = bookService.findAll().toArray(new Book[0]);
		Sorting sort = new Sorting(booksArray);

		sort.shuttleSort(booksArray);

		theModel.addAttribute("booksArray", booksArray);

		watch.stop();
		System.out.println("Total execution of shuttleSort: " + watch.getTotalTimeMillis() + " milliseconds");

		return "books/list-books-sorted";
	}

	@GetMapping("/shellSort")
	public String shellSort(Model theModel){

		StopWatch watch = new StopWatch();
		watch.start();

		Book[] booksArray = bookService.findAll().toArray(new Book[0]);
		Sorting sort = new Sorting(booksArray);

		sort.shellSort(booksArray);

		theModel.addAttribute("booksArray", booksArray);

		watch.stop();
		System.out.println("Total execution of shellSort: " + watch.getTotalTimeMillis() + " milliseconds");

		return "books/list-books-sorted";
	}
}









