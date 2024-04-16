package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class PlayerInformation implements Serializable {
    protected final Integer score;
    protected final Arme arme_selectionné;
    protected final List<Arme> player_armes;
    public PlayerInformation(Integer score, Arme armeSelectionné, List<Arme> playerArmes){
        this.score = score;
        this.arme_selectionné = armeSelectionné;
        this.player_armes = playerArmes;
    }
    public Integer getScore() {
        return score;
    }

    public Arme getArme_selectionné() {
        return arme_selectionné;
    }

    public List<Arme> getPlayer_armes() {
        return player_armes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PlayerInformation {\n");
        sb.append("  Score: ").append(score).append("\n");
        sb.append("  Weapon Selected: ").append(arme_selectionné.getNom()).append("\n");
        sb.append("  Prix: ").append(arme_selectionné.getPrix()).append("\n");
        sb.append("  Performance: ").append(arme_selectionné.getFlower_per_click()).append("\n");
        sb.append("  ArmePlayer: \n");

        for (int i = 0; i < player_armes.size(); i++) {
            Arme arme = player_armes.get(i);
            sb.append("    Index: ").append(i).append(", Arme: ").append(arme.getNom()).append(", ").append(arme.getFileName()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

}
