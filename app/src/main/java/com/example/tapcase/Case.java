package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class Case implements Serializable {
    private List<Arme> armeDispo;
    private Integer prix;
    private String caseFileName;

    private CaseType caseType;
    private Arme armeObtenu;
    private String caseName;

    public Case(List<Arme> armeDispo, CaseType caseType, Integer prix, String caseFileName, Arme armeObtenu, String caseName) {
        this.armeDispo = armeDispo;
        this.prix = prix;
        this.caseType = caseType;
        this.caseFileName = caseFileName;
        this.armeObtenu = armeObtenu;
        this.caseName = caseName;
    }

    public String getCaseFileName() {
        return caseFileName;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public void setCaseFileName(String caseFileName) {
        this.caseFileName = caseFileName;
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

    public Arme getArmeObtenu() {
        return armeObtenu;
    }

    public void setArmeObtenu(Arme armeObtenu) {
        this.armeObtenu = armeObtenu;
    }
    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
