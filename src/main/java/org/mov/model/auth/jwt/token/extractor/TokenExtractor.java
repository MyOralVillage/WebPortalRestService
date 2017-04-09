package org.mov.model.auth.jwt.token.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}
