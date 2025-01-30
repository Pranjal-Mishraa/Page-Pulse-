package com.pranjal.PagePulse.controller;

import com.pranjal.PagePulse.entity.Book;
import com.pranjal.PagePulse.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;



    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library")

    @GetMapping
    public ResponseEntity<?> findAllBooks() {
        return bookService.findAllBooks();
    }


    @Operation(summary = "Add a new book", description = "Create a new book and add it to the library")

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }



    @Operation(summary = "Update a book", description = "Update an existing book in the library by its name")

    @PutMapping("/{name}")
    public ResponseEntity<String> updateBook(@PathVariable String name, @RequestBody Book book) {
        return bookService.updateBook(name,book);
    }



    @Operation(summary = "Delete a book", description = "Delete a book from the library by its title")

    @DeleteMapping("/{title}")
    public ResponseEntity<?> deleteBook(@PathVariable String title) {
        return bookService.deleteBook(title);

    }



    @Operation(summary = "Search book by author name", description = "Fetches all books that match the given author name")

    @GetMapping("/search/author/{author}")
    public ResponseEntity<?> searchBooksByAuthor(@PathVariable String author) {
        return bookService.searchBooksByAuthor(author);
    }



    @Operation(summary = "Search book by title", description = "Fetches all books that match the given title")

    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> searchBooksByTitle(@PathVariable String title) {
        return bookService.searchBooksByTitle(title);
    }
}

//    @Operation(summary = "Get a book by name", description = "Find a book in the library by its name")
//
//    @GetMapping("/{name}")
//    public ResponseEntity<?> getBookByName(@PathVariable String name) {
//        Optional<Book> book = bookService.findByName(name);
//        return book.map(value -> ResponseEntity.ok("Book found \n" + value))
//                .orElse(  ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Book not found!"));
//    }