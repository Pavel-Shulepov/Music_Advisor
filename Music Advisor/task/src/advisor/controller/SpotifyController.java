package advisor.controller;

import advisor.utils.SpotifyClient;

import java.io.IOException;

public class SpotifyController {

    private final SpotifyClient spotifyClient = new SpotifyClient();

    public int getCategories(int limit, int offset) throws IOException, InterruptedException {
        return spotifyClient.categories(limit, offset);
    }

    public int getNewRealises(int limit, int offset) throws IOException, InterruptedException {
        return spotifyClient.newRealises(limit, offset);
    }

    public int getFeatured(int limit, int offset) throws IOException, InterruptedException {
        return spotifyClient.featured(limit, offset);
    }

    public int getPlaylists(String name, int limit, int offset) throws IOException, InterruptedException {
        return spotifyClient.playlists(name, limit, offset);
    }

    public void doAuth(String code) throws IOException, InterruptedException {
        spotifyClient.spotifyAuth(code);
    }

    public boolean isAuth() {
        return spotifyClient.isAuth();
    }

    public String getSpotifyAccountUrl() {
        return spotifyClient.getSpotifyAccountUrl();
    }

    public void setSpotifyAccountUrl(String accountUrl, String spotifyApiUrl) {
        spotifyClient.setSpotifyAccountUrl(accountUrl, spotifyApiUrl);
    }

}
