package com.example.tapcase;

import java.io.Serializable;
import java.util.List;

public class CaseInformation implements Serializable {
    private GameInformation gameInformation;
    private Case caseInfomation;

    public CaseInformation(GameInformation gameInformation, Case caseInfomation) {
        this.gameInformation = gameInformation;
        this.caseInfomation = caseInfomation ;
    }

    public GameInformation getGameInformation() {
        return gameInformation;
    }

    public void setGameInformation(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
    }

    public Case getCaseInfomation() {
        return caseInfomation;
    }

    public void setCaseInfomation(Case caseInfomation) {
        this.caseInfomation = caseInfomation;
    }
}
