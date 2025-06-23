package com.grupo1.enums;

public enum Priority {
    BAJA("Baja"),
    MEDIA("Media"),
    CRITICA("Crítica");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}