package org.mov.entity;

public enum Role {
    ADMIN, MEMBER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
