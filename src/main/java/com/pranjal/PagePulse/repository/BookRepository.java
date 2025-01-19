package com.pranjal.PagePulse.repository;

import com.pranjal.PagePulse.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long > {
    @Query("SELECT b FROM Book b WHERE lower(b.title) = lower(:title)")
    Optional<Book> findByTitle(@Param("title") String title);

//    Optional<Book> findByTitle(String title);
    void deleteByTitle(String title);

    @Query(value = "SELECT * FROM book WHERE lower(title) LIKE  concat('%' , lower(:title), '%')" , nativeQuery = true)
    List <Book> searchBooks(@Param("title") String title);

    @Query(value = "SELECT * FROM book WHERE lower(author) LIKE concat('%' , lower(:author), '%')" , nativeQuery = true)
    List<Book> searchBooksByAuthor (@Param("author") String author);




}