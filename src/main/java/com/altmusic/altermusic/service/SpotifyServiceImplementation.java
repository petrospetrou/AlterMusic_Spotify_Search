package com.altmusic.altermusic.service;

import com.altmusic.altermusic.model.*;
import org.springframework.stereotype.Service;

import com.neovisionaries.i18n.CountryCode;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.*;

@Service
public class SpotifyServiceImplementation implements SpotifyService{

    //Credentials used for Authentication with the SpotifyAPI
    private static final String clientId = "f728b651b2b14519965900cbbbcb2d71";
    private static final String clientSecret = "822cdf52292940dc86cacafb0b8fd89f";

    private final SpotifyApi spotifyApi;

    public SpotifyServiceImplementation() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        clientCredentials_Async();
    }

    //Method for authenticating with the SpotifyAPI once and not needing token everytime
    private void clientCredentials_Async() {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                    .build();
            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            final ClientCredentials clientCredentials = clientCredentialsFuture.join();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    //Method for searching for something
    public LinkedList<SearchItem> searchSpotify(String searchInput) {
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(searchInput).market(CountryCode.SE).limit(3).build();
        SearchAlbumsRequest searchAlbumRequest = spotifyApi.searchAlbums(searchInput).market(CountryCode.SE).limit(3).build();
        SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(searchInput).market(CountryCode.SE).limit(3).build();

        try {
            Paging<Track> track = searchTracksRequest.execute();
            Paging<AlbumSimplified> album = searchAlbumRequest.execute();
            Paging<Artist> artist = searchArtistsRequest.execute();

            var response = new LinkedList<SearchItem>();
            Image[] trackImage, albumImage,artistImage;
            String trackArtist = null, albumArtist = null, imageUrl = null, type;

            //Getting the search data for a track
            for (Track t : track.getItems()) {
                type = t.getType().toString().substring(0,1).toUpperCase() + t.getType().toString().substring(1).toLowerCase();

                for(ArtistSimplified tArtist : t.getArtists()){
                    trackArtist = tArtist.getName();

                }

                trackImage = t.getAlbum().getImages();

                if (trackImage != null && trackImage.length > 0) {
                    imageUrl = trackImage[0].getUrl();
                }

                response.add(new SearchItem(t.getName(), t.getId(), trackArtist, type, imageUrl));
            }

            //Getting the search data for an album
            for (AlbumSimplified a : album.getItems()) {
                type =a.getType().toString().substring(0,1).toUpperCase() + a.getType().toString().substring(1).toLowerCase();

                for (ArtistSimplified aArtist : a.getArtists()){
                    albumArtist = aArtist.getName();
                }

                albumImage = a.getImages();

                if (albumImage != null && albumImage.length > 0) {
                    imageUrl = albumImage[0].getUrl();
                }

                response.add(new SearchItem(a.getName(), a.getId(), albumArtist, type, imageUrl));
            }

            //Getting the search data for an artist
            for (Artist singer : artist.getItems()) {
                type = singer.getType().toString().substring(0,1).toUpperCase() + singer.getType().toString().substring(1).toLowerCase();

                artistImage = singer.getImages();

                if (artistImage != null && artistImage.length > 0) {
                    imageUrl = artistImage[0].getUrl();
                }

                response.add(new SearchItem(singer.getName(), singer.getId(), singer.getName(), type, imageUrl));
            }

            return response;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        } catch (org.apache.hc.core5.http.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //Method for getting the needed data for a track
    public TrackItem getTrackInfo(String trackId) {
        try {

            GetTrackRequest getTrackRequest = spotifyApi.getTrack(trackId).build();
            CompletableFuture<Track> trackFuture = getTrackRequest.executeAsync();

            final Track track = trackFuture.join();

            String artistName = null;
            Image[] trackImageUrl = track.getAlbum().getImages();
            String trackType = track.getType().toString().substring(0,1).toUpperCase() + track.getType().toString().substring(1).toLowerCase();

            //Converting the duration from msec to minutes
            // formula for conversion for milliseconds to minutes.
            long minutes = (track.getDurationMs() / 1000) / 60;
            // formula for conversion for milliseconds to seconds
            long seconds = (track.getDurationMs() / 1000) % 60;

            for (ArtistSimplified t : track.getArtists()) {
                artistName = t.getName();
            }

            return new TrackItem(track.getName(), track.getId().toString(), trackType, artistName, track.getAlbum().getName().toString(), minutes, seconds, track.getPopularity(), track.getDiscNumber(), trackImageUrl[0].getUrl(), track.getExternalUrls().get("spotify"));

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return null;
    }

    //Method for getting the needed data for an album
    public AlbumItem getAlbumInfo(String albumId) {
        try {
            GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(albumId).build();
            CompletableFuture<Album> albumFuture = getAlbumRequest.executeAsync();

            LinkedList<String> trackList = new LinkedList<>();

            String artistName = null;

            final Album album = albumFuture.join();

            Image[] albumImageUrl = album.getImages();
            String albumType = album.getAlbumType().toString().substring(0,1).toUpperCase() + album.getAlbumType().toString().substring(1).toLowerCase();

            for(ArtistSimplified t: album.getArtists()){
                artistName = t.getName();
            }

            for (TrackSimplified a: album.getTracks().getItems()){
                trackList.add(a.getName());
            }

            return new AlbumItem(album.getName(),album.getId(),artistName,album.getReleaseDate(),albumType,album.getLabel(),album.getPopularity(),trackList,albumImageUrl[0].getUrl(),album.getExternalUrls().get("spotify").toString());

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return null;
    }

    //Method for getting the needed data for an artist
    public ArtistItem getArtistInfo(String artistId) {
        try {

            GetArtistRequest getArtistRequest = spotifyApi.getArtist(artistId).build();
            CompletableFuture<Artist> albumFuture = getArtistRequest.executeAsync();
            final Artist artist = albumFuture.join();

            GetArtistsAlbumsRequest getArtistsAlbumsRequest = spotifyApi.getArtistsAlbums(artistId).album_type("album").market(CountryCode.SE).build();
            CompletableFuture<Paging<AlbumSimplified>> pagingFuture = getArtistsAlbumsRequest.executeAsync();
            final Paging<AlbumSimplified> albumSimplifiedPaging = pagingFuture.join();

            LinkedList<String> albumList = new LinkedList<>();
            Image[] imageUrl = artist.getImages();
            String artistType = artist.getType().toString().substring(0,1).toUpperCase() + artist.getType().toString().substring(1).toLowerCase();

            for (AlbumSimplified a: albumSimplifiedPaging.getItems()){
                albumList.add(a.getName());
            }

            return new ArtistItem(artist.getName(),artist.getId(),artist.getGenres(),artistType,artist.getFollowers().getTotal().toString(),albumList,artist.getPopularity(),imageUrl[0].getUrl().toString(),artist.getExternalUrls().get("spotify"));
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
        return null;
    }
}
