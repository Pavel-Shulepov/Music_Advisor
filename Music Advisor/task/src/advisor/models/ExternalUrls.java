package advisor.models;

import java.util.Map;

public class ExternalUrls {

    private final Map<String, String> external_urls;

    public Map<String, String> getExternalUrls() {
        return external_urls;
    }

    public ExternalUrls(Map<String, String> external_urls) {
        this.external_urls = external_urls;
    }
}
