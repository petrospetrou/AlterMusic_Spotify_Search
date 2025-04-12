package com.altmusic.altermusic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Spotify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String searchInput;

    public Spotify() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
