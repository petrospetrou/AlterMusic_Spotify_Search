package com.altmusic.altermusic.model;

import java.util.LinkedList;

public class AlbumItem {

    public String albumName;
    public String id;
    public String artistName;
    public String realeaseDate;
    public String type;
    public String label;
    public int popularity;
    public LinkedList<String> tracks;
    public String albumImage;
    public String externalLink;

    public AlbumItem(String albumName, String id, String artistName, String realeaseDate, String type, String label, int popularity, LinkedList<String> tracks, String albumImage, String externalLink) {
        this.albumName = albumName;
        this.id = id;
        this.artistName = artistName;
        this.realeaseDate = realeaseDate;
        this.type = type;
        this.label = label;
        this.popularity = popularity;
        this.tracks = tracks;
        this.albumImage = albumImage;
        this.externalLink = externalLink;
    }
}
