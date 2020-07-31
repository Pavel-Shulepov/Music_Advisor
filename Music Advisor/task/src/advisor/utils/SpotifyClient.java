package advisor.utils;

import advisor.models.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

public class SpotifyClient {

    private String spotifyAccountUrl = "https://accounts.spotify.com";
    private String spotifyApiUrl = "https://api.spotify.com";
    private boolean isAuth = false;
    private Token token;

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public void setSpotifyAccountUrl(String spotifyAccountUrl, String spotifyApiUrl) {
        this.spotifyAccountUrl = spotifyAccountUrl;
        this.spotifyApiUrl = spotifyApiUrl;
    }

    public String getSpotifyAccountUrl() {
        return spotifyAccountUrl;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public int categories(int limit, int offset) throws IOException, InterruptedException {
        CategoriesShell categoriesShell = getResponse("/v1/browse/categories", CategoriesShell.class);

        List<Category> items = categoriesShell.getCategories().getItems();
        Category category = items.get(offset);
        System.out.println(category.getName());

        System.out.printf("\n---PAGE %d OF %d---\n", (offset + limit) / limit, round(categoriesShell.getCategories().getTotal(), limit));
        return categoriesShell.getCategories().getTotal();
    }

    private int round(int a, int b) {
        int result = a / b;
        if ((a % b) > 0) result++;
        return result;
    }

    private Categories getCategories() throws IOException, InterruptedException {
        return getResponse("/v1/browse/categories?limit=50", CategoriesShell.class).getCategories();
    }

    public int playlists(String name, int limit, int offset) throws IOException, InterruptedException {
        Categories categories = getCategories();
        Category category = null;
        for (Category c : categories.getItems()) {
            if (name.equals(c.getName())) {
                category = c;
                break;
            }
        }
        if (category == null) {
            System.out.println("Unknown category name.");
        } else {
            PlayListShell playListShell
                    = getResponse(String.format("/v1/browse/categories/%s/playlists", category.getId()), PlayListShell.class);
            if (playListShell != null && playListShell.getPlaylists() != null) {
                List<Playlist> items = playListShell.getPlaylists().getItems();
                Playlist playlist = items.get(offset);
                System.out.println(playlist.getName());
                playlist.getExternalUrls().forEach((key, value) -> System.out.println(value));
                System.out.printf("\n---PAGE %d OF %d---\n", (offset + limit) / limit, round(playListShell.getPlaylists().getTotal(), limit));
                return playListShell.getPlaylists().getTotal();
            }
        }
        return 0;
    }

    public int newRealises(int limit, int offset) throws IOException, InterruptedException {
        AlbumShell albumShell = getResponse("/v1/browse/new-releases", AlbumShell.class);
        if (albumShell == null) return 0;
        List<Album> items = albumShell.getAlbums().getItems();
        Album album = items.get(offset);
        System.out.println(album.getName());
        System.out.println(album.getArtists());
        album.getExternalUrls().forEach((key, value) -> System.out.println(value));
        System.out.printf("\n---PAGE %d OF %d---\n", (offset + limit) / limit, round(albumShell.getAlbums().getTotal(), limit));
        return albumShell.getAlbums().getTotal();
    }

    public int featured(int limit, int offset) throws IOException, InterruptedException {
        PlayListShell playListShell = getResponse("/v1/browse/featured-playlists", PlayListShell.class);

        List<Playlist> items = playListShell.getPlaylists().getItems();
        Playlist playlist = items.get(offset);
        System.out.println(playlist.getName());
        playlist.getExternalUrls().forEach((key, value) -> System.out.println(value));
        System.out.printf("\n---PAGE %d OF %d---\n", (offset + limit) / limit, round(playListShell.getPlaylists().getTotal(), limit));
        return playListShell.getPlaylists().getTotal();
    }

    public void spotifyAuth(String code) throws IOException, InterruptedException {

        String uri = spotifyAccountUrl + "/api/token";

        System.out.println("code received\n" +
                "making http request for access_token...");

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("Authorization"," Basic " + Base64.getEncoder().encodeToString(("c0f908b3680043d7b7fd6b21f90b9a44:f90c31ce1569413082142ea9c0a3fde9").getBytes()))
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:8080"))
                .build();

        HttpResponse<String> response =  httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        token = gson.fromJson(response.body(), Token.class);

        System.out.println("response:");
        System.out.println(token.getAccessToken());
        isAuth = true;
    }

    private <T> T getResponse(String method, Class<T> clazz) throws IOException, InterruptedException {
        String uri = spotifyApiUrl + method;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + this.token.getAccessToken())
                .uri(URI.create(uri))
                .GET()
                .build();

        HttpResponse<String> response =  httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.body().contains("error")) {
            Gson gson = new Gson();
            SpotifyError spotifyError = gson.fromJson(response.body(), SpotifyError.class);
            System.out.println(spotifyError.getMessage());
        }

        Gson gson = new Gson();
        return gson.fromJson(response.body(), clazz);
    }

}
