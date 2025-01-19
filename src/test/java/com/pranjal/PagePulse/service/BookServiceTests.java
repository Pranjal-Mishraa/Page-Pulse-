package com.pranjal.PagePulse.service;

import com.pranjal.PagePulse.entity.Book;
import com.pranjal.PagePulse.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTests {

//    MOCKING DEPENDENCY
    @Mock //mock the BookRepository because BookService relies on this repository to perform
    // its operations.
    private BookRepository bookRepository;

//  MOCKED DEPENDENCY INJECTED HERE ONLY
    @InjectMocks //injects the mocked dependencies(used by the BookService class itself) into this class.
    // Takes the BookService instance and injects the mocked BookRepository into it, replacing any real instance that would otherwise be used.
    private BookService bookService;

//    HERE WHY WE ARE NOT MOCKING THE BOOKSERVICE ITSELF because of what we want to test is bookservice .

//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); //initializes the mocks.
//    }

    @Test
    void testSaveBook_TitleOrAuthorMissing() {
        // Arrange
        Book book = new Book();
        book.setTitle("");
        book.setAuthor("Author");

        // Act
        ResponseEntity<String> response = bookService.saveBook(book);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Title or Author is missing", response.getBody());
    }

    @Test
    void testSaveBook_ExistingBookCountUpdated() {
        // Arrange
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setCount(5);

        Book existingBook = new Book();
        existingBook.setTitle("Harry Potter");
        existingBook.setAuthor("J.K. Rowling");
        existingBook.setCount(10);

        when(bookRepository.findByTitle("Harry Potter")).thenReturn(Optional.of(existingBook));

        // Act
        ResponseEntity<String> response = bookService.saveBook(book);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Book count updated successfully", response.getBody());
        assertEquals(15, existingBook.getCount());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testSaveBook_NewBookAdded() {
        // Arrange
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setCount(5);

        when(bookRepository.findByTitle("Harry Potter")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = bookService.saveBook(book);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Book added successfully", response.getBody());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testSaveBook_CountIsZeroOrNegative() {
        // Arrange
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setCount(0);

        // Act
        ResponseEntity<String> response = bookService.saveBook(book);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Count must be greater than 0 ", response.getBody());
    }

    @Test
    void testSaveBook_ExceptionHandling() {
        // Arrange
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setCount(5);

        when(bookRepository.findByTitle("Harry Potter")).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<String> response = bookService.saveBook(book);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("an error occured"));
        verify(bookRepository, never()).save(any(Book.class));
    }
}




//package com.pranjal.PagePulse.service;
//
//import com.pranjal.PagePulse.entity.Book;
//import com.pranjal.PagePulse.repository.BookRepository;
//import org.apache.catalina.Store;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//public class BookServiceTests {
//
////    @Autowired
////    private BookService bookService;
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Test
//    public void testSearchBookByName(){
//
////        bookRepository.save();
//        assertNotNull(bookRepository.searchBooks("And Potter"));
//    }
//
//