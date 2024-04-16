package com.example.tapcase;

import com.example.tapcase.Arme;

import java.util.Comparator;

public class ArmeComparator {

    public static Comparator<Arme> prixComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            return Integer.compare(arme2.getPrix(), arme1.getPrix()); // Note the order change
        }
    };

    public static Comparator<Arme> rareteComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            return arme2.getRarete().compareTo(arme1.getRarete()); // Note the order change
        }
    };

    public static Comparator<Arme> fleursTirComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            return Integer.compare(arme2.getFlower_per_click(), arme1.getFlower_per_click()); // Note the order change
        }
    };

    public static Comparator<Arme> nomComparator = new Comparator<Arme>() {
        @Override
        public int compare(Arme arme1, Arme arme2) {
            int typeValue1 = getTypeValueFromFileName(arme1.getFileName());
            int typeValue2 = getTypeValueFromFileName(arme2.getFileName());

            int typeCompare = Integer.compare(typeValue2, typeValue1); // Note the order change

            if (typeCompare == 0) {
                return arme2.getNom().compareTo(arme1.getNom()); // Note the order change
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
