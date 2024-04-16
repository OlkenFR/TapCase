package com.example.tapcase;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Arme implements Serializable {
    private Rarete rarete;
    private Integer flower_per_click;
    private Integer prix;
    private String nom;
    private String fileName;

    public Arme(Rarete rarete, Integer flower_per_click, Integer prix, String nom, String fileName) {
        this.rarete = rarete;
        this.flower_per_click = flower_per_click;
        this.prix = prix;
        this.nom = nom;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Arme{" +
                "rarete=" + rarete +
                ", flower_per_click=" + flower_per_click +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
