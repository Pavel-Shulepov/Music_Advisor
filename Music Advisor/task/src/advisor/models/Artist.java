package advisor.models;

import java.util.Map;

public class Artist {

    private final String id;
    private final String name;
    private final String type;
    private final String uri;
    private final String href;
    private final Map<String, String> external_urls;


    public Artist(String id, String name, String type, String uri, String href, Map<String, String> external_urls) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.uri = uri;
        this.href = href;
        this.external_urls = external_urls;
    }

    @Override
    public String toString() {
        return name;
    }
}
