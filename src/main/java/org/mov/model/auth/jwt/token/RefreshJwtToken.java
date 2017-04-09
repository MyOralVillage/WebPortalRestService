package org.mov.model.auth.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.mov.model.auth.Scopes;

import java.util.List;
import java.util.Optional;

public class RefreshJwtToken implements JwtToken {
    private Jws<Claims> claims;

    private RefreshJwtToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    public static Optional<RefreshJwtToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty()
                || scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
            return Optional.empty();
        }

        return Optional.of(new RefreshJwtToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }

    public String getJti() {
        return claims.getBody().getId();
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
