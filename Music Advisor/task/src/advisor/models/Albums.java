package advisor.models;

import java.util.List;

public class Albums {

    private final String href;
    private final List<Album> items;

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

    public List<Album> getItems() {
        return items;
    }

    public Albums(String href, List<Album> items, int limit, int offset, int total) {
        this.href = href;
        this.items = items;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }
}
