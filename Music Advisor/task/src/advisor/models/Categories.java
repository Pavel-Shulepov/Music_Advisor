package advisor.models;

import java.util.List;

public class Categories {

    private final String href;
    private final List<Category> items;

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

    public List<Category> getItems() {
        return items;
    }

    public Categories(String href, List<Category> items, int limit, int offset, int total) {
        this.href = href;
        this.items = items;
        this.limit = limit;
        this.offset = offset;
        this.total = total;
    }
}
