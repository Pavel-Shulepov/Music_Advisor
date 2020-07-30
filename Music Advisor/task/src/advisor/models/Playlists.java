package advisor.models;

import java.util.List;

public class Playlists {

    private final String href;
    private final List<Playlist> items;

    public List<Playlist> getItems() {
        return items;
    }

    public Playlists(String href, List<Playlist> items) {
        this.href = href;
        this.items = items;
    }

}
