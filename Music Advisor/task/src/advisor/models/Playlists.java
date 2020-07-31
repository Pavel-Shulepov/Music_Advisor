package advisor.models;

import java.util.List;

public class Playlists {

    private final String href;
    private final List<Playlist> items;

    private final int limit;
    private final int offset;
    private final int total;

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }

    public List<Playlist> getItems() {
        return items;
    }

    public Playlists(String href, List<Playlist> items, int limit, int offset, int total) {
        this.href = href;
        this.items = items;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }

}
