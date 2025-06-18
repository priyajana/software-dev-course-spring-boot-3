package com.example.springBoot2.controllers;

import com.example.springBoot2.models.Album;
import com.example.springBoot2.models.Book;
import com.example.springBoot2.models.Movie;
import com.example.springBoot2.repositories.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    //Add private final fields for the JPA repository that corresponds to the controllers.

    private final BookRepository bookRepo;


    public BookController(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping
    public String getAllItems () {
        List<Book> booksList = bookRepo.findAll();
        StringBuilder customString = new StringBuilder();
        System.out.println("Books fetched!!!"+booksList);
        for (Book book : booksList) {
            customString.append("<li><a href='/artworks/details/").append(book.getId()).append("'>").append(book.getName()).append("</a></li>");
        }
        return
                """        
                <html>
                <body>
                <h2>BOOKS</h2>
                <ul>
                """+
                customString +
                """
                </ul>
                <p><a href='/books/addItem'>Add</a> another book or <a href='/books/delete'>delete</a> one or more books.</p>
                </body>
                </html>
            """;
    }

    @GetMapping("/{id}")
    public Book getItem(@PathVariable int id) {
        return bookRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Book addItem(@RequestBody Book book) {
        return bookRepo.save(book);
    }

    @PutMapping("/{id}")
    public Book updateItem(@PathVariable int id, @RequestBody Book book) {
        book.setId(id);
        return bookRepo.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id) {
        bookRepo.deleteById(id);
    }
}
