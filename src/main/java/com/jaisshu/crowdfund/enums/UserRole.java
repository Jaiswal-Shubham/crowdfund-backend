package com.jaisshu.crowdfund.enums;

public enum UserRole {
    INNOVATOR,
    DONOR;

    public static UserRole getRole(String name) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No user role with name " + name);
    }
}
