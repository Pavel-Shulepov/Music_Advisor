package advisor.models;

public class CategoriesShell implements ISpotify {

    private final Categories categories;

    public Categories getCategories() {
        return categories;
    }

    public CategoriesShell(Categories categories) {
        this.categories = categories;
    }
}
