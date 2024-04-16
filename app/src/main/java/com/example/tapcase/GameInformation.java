package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class GameInformation implements Serializable {
    private List<Arme> weaponAvailable;

    public GameInformation(List<Arme> weaponAvailable) {
        this.weaponAvailable = weaponAvailable;
    }

    public List<Arme> getWeaponAvailable() {
        return weaponAvailable;
    }

    public void setWeaponAvailable(List<Arme> weaponAvailable) {
        this.weaponAvailable = weaponAvailable;
    }
}
