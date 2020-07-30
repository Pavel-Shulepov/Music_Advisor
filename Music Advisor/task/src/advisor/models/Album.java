package advisor.models;

import java.util.List;
import java.util.Map;

public class Album {
    private final String album_type;
    private final List<Artist> artists;
    private final List<String> available_markets;
    private final Map<String, String> external_urls;
    private final String href;
    private final String id;
    private final List<Image> images;
    private final String name;
    private final String type;
    private final String uri;

    public List<Artist> getArtists() {
        return artists;
    }

    public Map<String, String> getExternalUrls() {
        return external_urls;
    }

    public String getName() {
        return name;
    }

    public Album(String album_type, List<Artist> artists, List<String> available_markets, Map<String, String> external_urls, String href, String id, List<Image> images, String name, String type, String uri) {
        this.album_type = album_type;
        this.artists = artists;
        this.available_markets = available_markets;
        this.external_urls = external_urls;
        this.href = href;
        this.id = id;
        this.images = images;
        this.name = name;
        this.type = type;
        this.uri = uri;
    }
}
