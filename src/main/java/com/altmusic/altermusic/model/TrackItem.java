package com.altmusic.altermusic.model;

public class TrackItem {

    public String name;
    public String id;
    public String type;
    public String artistName;
    public String albumName;
    public long minDuration;
    public long secDuration;
    public int popularity;
    public int discNumber;
    public String trackImage;
    public String externalLink;


    public TrackItem(String name, String id, String type, String artistName, String albumName, long minDuration, long secDuration, int popularity, int diskNumber, String trackImageUrl, String externalLink) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.artistName = artistName;
        this.albumName = albumName;
        this.minDuration = minDuration;
        this.secDuration = secDuration;
        this.popularity = popularity;
        this.discNumber = diskNumber;
        this.trackImage = trackImageUrl;
        this.externalLink = externalLink;

    }
}
