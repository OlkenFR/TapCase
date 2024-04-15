package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class PlayerInformation implements Serializable {
    protected final Integer argent;
    protected final Arme arme_selectionné;
    protected final List<Arme> player_armes;
    public PlayerInformation(Integer argent, Arme armeSelectionné, Integer flowerPerClick, List<Arme> playerArmes){
        this.argent = argent;
        this.arme_selectionné = armeSelectionné;
        this.player_armes = playerArmes;
    }

    public Integer getArgent() {
        return argent;
    }

    public Arme getArme_selectionné() {
        return arme_selectionné;
    }

    public List<Arme> getPlayer_armes() {
        return player_armes;
    }
}
