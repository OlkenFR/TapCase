package com.example.tapcase;

public enum Rarete {
    BASE(0),
    SUPERIEUR(1),
    EXOTIQUE(2),
    CLASSIFIE(3),
    SECRET(4);

    private final int value;

    Rarete(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
