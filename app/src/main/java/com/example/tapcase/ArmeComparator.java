package com.example.tapcase;

import com.example.tapcase.Arme;

import java.util.Comparator;

public class ArmeComparator {

    public static Comparator<Arme> prixComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            return Integer.compare(arme1.getPrix(), arme2.getPrix());
        }
    };

    public static Comparator<Arme> rareteComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            return arme1.getRarete().compareTo(arme2.getRarete());
        }
    };

    public static Comparator<Arme> fleursTirComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            return Integer.compare(arme1.getFlower_per_click(), arme2.getFlower_per_click());
        }
    };

    public static Comparator<Arme> nomComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            int typeValue1 = getTypeValueFromFileName(arme1.getFileName());
            int typeValue2 = getTypeValueFromFileName(arme2.getFileName());

            int typeCompare = Integer.compare(typeValue1, typeValue2);

            if (typeCompare == 0) {
                return arme1.getNom().compareTo(arme2.getNom());
            }

            return typeCompare;
        }

        private int getTypeValueFromFileName(String fileName) {
            if (fileName.startsWith("glock")) {
                return 0;
            } else if (fileName.startsWith("usp")) {
                return 1;
            } else if (fileName.startsWith("mp")) {
                return 2;
            } else if (fileName.startsWith("m_")) {
                return 3;
            } else if (fileName.startsWith("ak")) {
                return 4;
            } else if (fileName.startsWith("awp")) {
                return 5;
            } else if (fileName.startsWith("knife")) {
                return 6;
            }
            return 7;
        }
    };


}