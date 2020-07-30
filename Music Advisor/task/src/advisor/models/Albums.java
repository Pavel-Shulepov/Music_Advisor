package advisor.models;

import java.util.List;

public class Albums {

    private final String href;
    private final List<Album> items;

    public List<Album> getItems() {
        return items;
    }

    public Albums(String href, List<Album> items) {
        this.href = href;
        this.items = items;
    }
}
