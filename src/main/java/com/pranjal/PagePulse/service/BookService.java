package com.pranjal.PagePulse.service;

import com.pranjal.PagePulse.entity.Book;
import com.pranjal.PagePulse.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    //   Create a new book or add an existing book
    @Transactional
    public ResponseEntity<String> saveBook(Book book) {

        if(book.getTitle().isEmpty()  || book.getAuthor().isEmpty()){
            return ResponseEntity.badRequest().body("Title or Author is missing");
        }
        try {
            Optional<Book> existingBookOpt = bookRepository.findByTitle(book.getTitle());
            if (existingBookOpt.isPresent()) {
                Book existingBook = existingBookOpt.get();
                existingBook.setCount(existingBook.getCount() + book.getCount());
//                existingBook.setAvailable(book.isAvailable());
                return ResponseEntity.ok("Book count updated successfully");
            }
            else {

                Book newBook = new Book();
                newBook.setTitle(book.getTitle());
                newBook.setAuthor(book.getAuthor());
                if(book.getCount() <= 0){
                    return  ResponseEntity.badRequest().body("Count must be greater than 0 ");
                }
                newBook.setCount(book.getCount());
//                newBook.setAvailable(book.isAvailable());
                bookRepository.save(newBook);
                return ResponseEntity.ok("Book added successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("an error occured" + e.getMessage());
        }

    }

    //  Retrieve all books
    public ResponseEntity<?> findAllBooks() {
        try{
            List<Book> books = bookRepository.findAll();

            if (!books.isEmpty()) {
                return ResponseEntity.ok(books );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND) // No books found
                        .body("No books found in the library.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred :" + e.getMessage());
            }
        }

    //  Search books by title

    public ResponseEntity<?> searchBooksByTitle(String title) {
        try {
            List<Book> books = bookRepository.searchBooks(title);

            if ( !books.isEmpty()) {
                return ResponseEntity.ok(books);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with the title");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for books :" + e.getMessage());
        }
    }


    //  Search books by author

    public ResponseEntity<?> searchBooksByAuthor(String author) {
        try{

         List<Book> books =  bookRepository.searchBooksByAuthor(author);

            if ( !books.isEmpty()) {
                return ResponseEntity.ok(books );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books found with the author");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching for books :" + e.getMessage());
        }

    }

    //  Delete a book by title
    @Transactional
    public ResponseEntity<?> deleteBook(String title) {
        try {
            Optional<Book> book = bookRepository.findByTitle(title);
            if (book.isPresent()) {
                bookRepository.deleteByTitle(title);
                return ResponseEntity.ok("Book is deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured" + e.getMessage());
        }
    }


    //     Update an existing book by name
    @Transactional
    public ResponseEntity<String> updateBook(String name, Book book) {
        try {

            Optional<Book> existingBookO = bookRepository.findByTitle(name);
            if (existingBookO.isPresent()) {
                Book existingBook = existingBookO.get();
                existingBook.setTitle(book.getTitle()); // Ensure the ID stays the same
                bookRepository.save(existingBook);
                return ResponseEntity.ok("Book updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book doesn't exists");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the book:" + e.getMessage());
        }
    }


//    // Retrieve book by name
//    public Optional<Book> findByName(String title) {
//        return bookRepository.findByTitle(title);
//    }


}