package advisor.models;

public class SpotifyError {

    private final Error error;

    public String getMessage() {
        return error.message;
    }

    public SpotifyError(Error error) {
        this.error = error;
    }

    private class Error {
        final int status;
        final String message;

        private Error(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }

}
