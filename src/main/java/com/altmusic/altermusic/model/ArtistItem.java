package com.altmusic.altermusic.model;

import java.util.LinkedList;

public class ArtistItem {
    public String artistName;
    public String artistId;
    public String[] genres;
    public String type;
    public String followers;
    public LinkedList<String> albumList;
    public int popularity;
    public String imageLink;
    public String externalLink;

    public ArtistItem(String artistName, String artistId, String[] genres, String type, String followers, LinkedList<String> albumList, int popularity, String imageLink, String externalLink) {
        this.artistName = artistName;
        this.artistId = artistId;
        this.genres = genres;
        this.type = type;
        this.followers = followers;
        this.albumList = albumList;
        this.popularity = popularity;
        this.imageLink = imageLink;
        this.externalLink = externalLink;
    }
}
