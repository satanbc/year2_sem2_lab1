package com.labs.task.testing;

import com.labs.task.Entities.Book;
import com.labs.task.controller.BookController;
import com.labs.task.dao.BookRepository;
import com.labs.task.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class TestingWebApplicationTests {

    /**
     * Autowire in the service we want to test
     */
    @Autowired
    private BookService service;

    /**
     * Create a mock implementation of the WidgetRepository
     */
    @MockBean
    private BookRepository repository;

    @MockBean
    private BookController controller;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        Book book = new Book(99999, "Book Name", "2000", "300", "Description", 100);
        doReturn(Optional.of(book)).when(repository).findById(99999);

        Optional<Book> returnedWidget = Optional.ofNullable(service.findById(99999));

        Assertions.assertTrue(returnedWidget.isPresent(), "Book was not found");
        Assertions.assertSame(returnedWidget.get(), book, "The book returned was not the same as the mock");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() throws RuntimeException{
        try {
            doReturn(Optional.empty()).when(repository).findById(99999);

            Optional<Book> returnedWidget = Optional.ofNullable(service.findById(99999));

            Assertions.assertFalse(returnedWidget.isPresent(), "Book should not be found");
        } catch (RuntimeException e){
            System.out.println("Book not found - success");
        }
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        Book widget1 = new Book(99999, "Book Name", "2000", "300", "Description", 100);
        Book widget2 = new Book(99988, "Book 2 Name", "2000", "300", "Description", 100);
        doReturn(Arrays.asList(widget1, widget2)).when(repository).findAll();

        List<Book> widgets = service.findAll();

        Assertions.assertEquals(2, widgets.size(), "findAll should return 2 books");
    }
}
