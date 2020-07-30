package advisor.models;

import java.util.List;

public class Categories {

    private final String href;
    private final List<Category> items;

    public List<Category> getItems() {
        return items;
    }

    public Categories(String href, List<Category> items) {
        this.href = href;
        this.items = items;
    }
}
