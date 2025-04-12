package com.altmusic.altermusic.controller;

import com.altmusic.altermusic.model.*;
import com.altmusic.altermusic.service.SpotifyServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@RequestMapping("/")
public class SpotifyController {
    @Autowired
    private SpotifyServiceImplementation spotifyServiceImpl;

    @GetMapping("/search")
    public LinkedList<SearchItem> search(@RequestParam final String searchInput) {

        LinkedList<SearchItem> dataResponse = spotifyServiceImpl.searchSpotify(searchInput);
        return dataResponse;
    }

    @GetMapping("/track")
    public TrackItem trackInfo(@RequestParam final String trackId) {

        TrackItem trackResponse = spotifyServiceImpl.getTrackInfo(trackId);
        return  trackResponse;
    }

    @GetMapping("/album")
    public AlbumItem albumInfo(@RequestParam final String albumId) {

        AlbumItem albumResponse = spotifyServiceImpl.getAlbumInfo(albumId);
        return albumResponse;
    }

    @GetMapping("/artist")
    public ArtistItem artistInfo(@RequestParam final String artistId) {

        ArtistItem artistResponse = spotifyServiceImpl.getArtistInfo(artistId);
        return artistResponse;
    }
}
