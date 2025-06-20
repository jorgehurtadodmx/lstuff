package com.grupo1.enums;


public enum TaskStatus {
    OPEN("Abierta"),
    IN_PROGRESS("En Progreso"),
    RESOLVED("Resuelta"),
    CLOSED("Cerrada");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}

