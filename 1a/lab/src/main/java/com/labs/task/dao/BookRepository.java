package com.labs.task.dao;

import com.labs.task.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/// Репозиторій книжок
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /// Метод пошуку за кодовим словом
    @Query(value = "select * from Book book where book.name like %:keyword%", nativeQuery = true)
    List<Book> getByKeyword(@Param("keyword") String keyword);
}











