package advisor.models;

public class Token {

    private final String access_token;
    private final String token_type;
    private final int expires_in;
    private final String refresh_token;
    private final String scope;

    public String getAccessToken() {
        return access_token;
    }

    public Token(String access_token, String token_type, int expires_in, String refresh_token, String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.scope = scope;
    }
}
