package app.sctp.core;

import com.auth0.android.jwt.JWT;
import com.google.gson.Gson;

import app.sctp.user.UserDetails;

public final class JwtUtils {
    private final Gson gson;

    public JwtUtils(Gson gson) {
        this.gson = gson;
    }

    /**
     * This method only parses. Does not verify the token
     *
     * @return .
     */
    public UserDetails parseJwtToken(String token) {
        try {
            JWT jwt = new JWT(token);
            String claims = jwt.getClaim("claims").asString();
            return this.gson.fromJson(claims, UserDetails.class);
        } catch (Exception e) {
            // TODO proper logging
            return null;
        }
    }
}
