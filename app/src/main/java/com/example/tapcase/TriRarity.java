package com.example.tapcase;

import java.util.Comparator;

public class TriRarity implements Comparator<Arme> {

    public int compare(Arme a, Arme b) {
        int compA = a.getRarete().ordinal();
        int compB = b.getRarete().ordinal();
        return compA - compB;
    }
}