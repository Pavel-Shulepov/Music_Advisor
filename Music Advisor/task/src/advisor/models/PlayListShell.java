package advisor.models;

public class PlayListShell {

    private final String message;
    private final Playlists playlists;

    public Playlists getPlaylists() {
        return playlists;
    }

    public PlayListShell(String message, Playlists playlists) {
        this.message = message;
        this.playlists = playlists;
    }
}
