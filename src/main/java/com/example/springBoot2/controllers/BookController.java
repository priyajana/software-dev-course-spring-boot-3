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
                <p><a href='/books/addItem'>Add</a> another book or <a href='/books/deleteItem'>delete</a> one or more books.</p>
                </body>
                </html>
            """;
    }

    @GetMapping("/{id}")
    public Book getItem(@PathVariable int id) {
        return bookRepo.findById(id).orElse(null);
    }

    @PostMapping("/addItem")
    // To use request body, we must construct a body and send request as json using JS fetch or ajax call.
    public String addItem(@RequestParam(value="name") String name,@RequestParam(value="author") String author,@RequestParam(value="year") int year, @RequestParam(value="pages") int pages) {
        Book newBook = new Book(name,author,year,pages);
        System.out.println("Saving book......"+newBook.getName());
         bookRepo.save(newBook);
       return """
               <html>
               <body>
               <h3>BOOK ADDED</h3>
               """ +
            "<p>You have successfully added " + name + " to the collection.</p>" +
            """
            <p><a href='/books/add'>Add</a> another book or view the <a href='/books'>updated list</a> of books.</p>
            </body>
            </html>
            """;
    }

    @GetMapping("/addItem")
    public String add(){
        return """
                <html>
                 <body>
                                <form method='POST'>
                                <p>Enter details of the new book:</p><br>
                                <input type='text' name='name' placeholder='Name' /><br>
                                <input type='text' name='author' placeholder='Author' /><br>
                                 <input type='text' name='year' placeholder='Year' /><br>
                                <input type='text' name='pages' placeholder='Pages' /><br>
                                <button type='submit'>Submit</button>
                                </form>
                                </body>
                </html>
                """;
    }
    @PutMapping("/{id}")
    public Book updateItem(@PathVariable int id, @RequestBody Book book) {
        book.setId(id);
        return bookRepo.save(book);
    }

    @GetMapping("/deleteItem")
    public String deleteItem() {
        List<Book> bookList = bookRepo.findAll();
        StringBuilder bookString = new StringBuilder();
        for (Book book : bookList) {
            int currId = book.getId();
            bookString.append("<li><input id='").append(currId).append("' name='bookIds' type='checkbox' value='").append(currId).append("' />").append(book.getName()).append("</li>");
        }
        return """
                <html>
                <body>
                <form method='POST'>
                <p>Select which book(s) you wish to delete:</p>
                <ul>
                """ +
               bookString +
               """
               </ul>
               <button type='submit'>Submit</button>
               </form>
               </body>
               </html>
               """;
    }

    @PostMapping("/deleteItem")
    public String deleteBooks(@RequestParam(value="bookIds") int[] bookIds){
        for(int id: bookIds){
            Book storedBook = bookRepo.findById(id).orElse(null);
            if(storedBook!=null){
                bookRepo.deleteById(id);
            }
        }
        String header = bookIds.length > 1 ? "BOOKS" : "BOOK";
        return """
                 <html>
                   <body>
                       <h3>
                           """ +
                       header +
                       """
                       DELETED</h3>
                       <p>Deletion successful.</p>
                       <p>View the <a href='/books'>updated list</a> of books.</p>
                       </body>
                       </html>
               """;

    }
}



