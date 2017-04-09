package org.mov.model.auth;

public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
