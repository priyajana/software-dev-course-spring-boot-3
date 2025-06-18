package com.example.springBoot2.controllers;

import com.example.springBoot2.models.Book;
import com.example.springBoot2.models.Movie;
import com.example.springBoot2.repositories.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepo;


    public MovieController(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @GetMapping
    public List<Movie> getAllItems () {
        return movieRepo.findAll();
    }

    @GetMapping("/{id}")
    public Movie getItem(@PathVariable int id) {
        return movieRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Movie addItem(@RequestBody Movie movie) {
        return movieRepo.save(movie);
    }

    @PutMapping("/{id}")
    public Movie updateItem(@PathVariable int id, @RequestBody Movie movie) {
        movie.setId(id);
        return movieRepo.save(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id) {
        movieRepo.deleteById(id);
    }
}
