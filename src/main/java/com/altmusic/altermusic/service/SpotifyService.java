package com.altmusic.altermusic.service;

import com.altmusic.altermusic.model.*;

import java.util.LinkedList;

public interface SpotifyService {
    LinkedList<SearchItem> searchSpotify(String searchInput);
    TrackItem getTrackInfo(String trackId);
    AlbumItem getAlbumInfo(String albumId);
    ArtistItem getArtistInfo(String artistId);
}
