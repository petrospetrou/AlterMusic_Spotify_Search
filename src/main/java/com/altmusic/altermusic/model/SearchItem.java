package com.altmusic.altermusic.model;

public class SearchItem {
    public String searchName;
    public String searchId;
    public String artistName;
    public String searchType;
    public String imageUrl;

    public SearchItem(String searchName, String searchId, String artistName, String searchType, String imageUrl) {
        this.searchName = searchName;
        this.searchId = searchId;
        this.artistName = artistName;
        this.searchType = searchType;
        this.imageUrl = imageUrl;
    }
}
