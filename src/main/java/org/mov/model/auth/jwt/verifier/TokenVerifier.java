package org.mov.model.auth.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
