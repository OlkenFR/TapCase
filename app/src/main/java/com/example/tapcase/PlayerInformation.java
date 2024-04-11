package com.example.tapcase;

import java.io.Serializable;

public class PlayerInformation implements Serializable {
    protected final Integer argent;
    protected final Arme arme_selectionné;
    public PlayerInformation(Integer argent, Arme armeSelectionné, Integer flowerPerClick){
        this.argent = argent;
        this.arme_selectionné = armeSelectionné;
    }

    public Integer getArgent() {
        return argent;
    }

    public Arme getArme_selectionné() {
        return arme_selectionné;
    }
}
