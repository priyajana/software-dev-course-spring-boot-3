package com.example.springBoot2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Album {
    private String name;
    private int year;
    private String artist;
    private int tracks;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public Album() {
    }

    public Album(String name, String artist, int year, int tracks, int id) {
        this.name = name;
        this.year = year;
        this.artist = artist;
        this.tracks = tracks;
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public void setId(int id){
        this.id = id;
    }
}
