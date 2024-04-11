package com.example.tapcase;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Arme implements Serializable {
    private Drawable image_arme;
    private Rarete rarete;
    private Integer flower_per_click;
    private Integer prix;
    private String nom;

    public Arme(Drawable image_arme, Rarete rarete, Integer flower_per_click, Integer prix, String nom) {
        this.image_arme = image_arme;
        this.rarete = rarete;
        this.flower_per_click = flower_per_click;
        this.prix = prix;
        this.nom = nom;
    }

    public Drawable getImage_arme() {
        return image_arme;
    }

    public void setImage_arme(Drawable image_arme) {
        this.image_arme = image_arme;
    }

    public Rarete getRarete() {
        return rarete;
    }

    public void setRarete(Rarete rarete) {
        this.rarete = rarete;
    }

    public Integer getFlower_per_click() {
        return flower_per_click;
    }

    public void setFlower_per_click(Integer flower_per_click) {
        this.flower_per_click = flower_per_click;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
