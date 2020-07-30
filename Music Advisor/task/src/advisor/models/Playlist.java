package advisor.models;

import java.util.Map;

public class Playlist {

    private final String name;
    private final Map<String, String> external_urls;

    public String getName() {
        return name;
    }

    public Map<String, String> getExternalUrls() {
        return external_urls;
    }

    public Playlist(String name, Map<String, String> external_urls) {
        this.name = name;
        this.external_urls = external_urls;
    }
}
