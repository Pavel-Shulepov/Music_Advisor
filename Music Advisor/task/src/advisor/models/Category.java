package advisor.models;

import java.util.List;

public class Category {

    private final String id;
    private final String name;
    private final String href;
    private final List<Image> icons;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Category(String id, String name, String href, List<Image> icons) {
        this.id = id;
        this.name = name;
        this.href = href;
        this.icons = icons;
    }
}
