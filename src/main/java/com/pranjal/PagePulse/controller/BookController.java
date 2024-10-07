package com.pranjal.PagePulse.controller;

import com.pranjal.PagePulse.entity.Book;
import com.pranjal.PagePulse.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/books" )
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAllBooks(){
        return bookService.findAllBooks();
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getBookByName(@PathVariable String name){
        Optional<Book> book = bookService.findByName(name);
        if (book.isPresent()) {
            return ResponseEntity.ok("Book found \n"+ book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book not found!");
        }

    }

    @PostMapping("/add")
    public  Book createBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @PutMapping("/{name}")
    public  ResponseEntity<Book> updateBook(@PathVariable String name , @RequestBody Book book){
        if (bookService.findByName(name).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        book.setTitle(name);
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @DeleteMapping("/{title}")
    @Transactional
    public  ResponseEntity<Void> deleteBook(@PathVariable String title){
        if (bookService.findByName(title).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(title);
        return ResponseEntity.noContent().build();
    }


}

