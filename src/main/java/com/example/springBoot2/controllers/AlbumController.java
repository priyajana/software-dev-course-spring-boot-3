package com.example.springBoot2.controllers;

import com.example.springBoot2.models.Album;
import com.example.springBoot2.models.Book;
import com.example.springBoot2.models.Movie;
import com.example.springBoot2.repositories.AlbumRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
   // Add private final fields for the JPA repository that corresponds to the controllers.

    private final AlbumRepository albumRepo;

    public AlbumController(AlbumRepository albumRepo) {
        this.albumRepo = albumRepo;
    }

    @GetMapping
    public List<Album> getAllItems () {
        return albumRepo.findAll();
    }

    @GetMapping("/{id}")
    public Album getItem(@PathVariable int id) {
        return albumRepo.findById(id).orElse(null);
    }

    @PostMapping
    public Album addItem(@RequestBody Album album) {
        return albumRepo.save(album);
    }

    @PutMapping("/{id}")
    public Album updateItem(@PathVariable int id, @RequestBody Album album) {
        album.setId(id);
        return albumRepo.save(album);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id) {
        albumRepo.deleteById(id);
    }
}
