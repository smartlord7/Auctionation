package Helpers.config;

/**
 * Class used to implement a token response.
 */
public class TokenResponse {
    public String token;
    public int expirationTime;

    public TokenResponse(String token, int expirationTime) {
        this.token = token;
        this.expirationTime = expirationTime;
    }
}
