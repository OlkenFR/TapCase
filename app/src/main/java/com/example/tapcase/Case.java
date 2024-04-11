package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class Case implements Serializable {
    private List<Arme> armeDispo;
    private Integer prix;
    private Rarete rarete;
    private Arme armeObtenu;

    public Case(List<Arme> armeDispo, Integer prix, Rarete rarete, Arme armeObtenu) {
        this.armeDispo = armeDispo;
        this.prix = prix;
        this.rarete = rarete;
        this.armeObtenu = armeObtenu;
    }

    public List<Arme> getArmeDispo() {
        return armeDispo;
    }

    public void setArmeDispo(List<Arme> armeDispo) {
        this.armeDispo = armeDispo;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Rarete getRarete() {
        return rarete;
    }

    public void setRarete(Rarete rarete) {
        this.rarete = rarete;
    }

    public Arme getArmeObtenu() {
        return armeObtenu;
    }

    public void setArmeObtenu(Arme armeObtenu) {
        this.armeObtenu = armeObtenu;
    }
}
