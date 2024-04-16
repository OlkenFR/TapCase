package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class GameInformation implements Serializable {
    private List<Arme> weaponAvailable;
    private PlayerInformation playerInformation;

    public GameInformation(List<Arme> weaponAvailable, PlayerInformation playerInformation) {
        this.weaponAvailable = weaponAvailable;
        this.playerInformation = playerInformation;
    }

    public List<Arme> getWeaponAvailable() {
        return weaponAvailable;
    }

    public void setWeaponAvailable(List<Arme> weaponAvailable) {
        this.weaponAvailable = weaponAvailable;
    }

    public PlayerInformation getPlayerInformation() {
        return playerInformation;
    }

    public void setPlayerInformation(PlayerInformation playerInformation) {
        this.playerInformation = playerInformation;
    }
}
