package com.example.tapcase;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Item extends Fragment {
    private static final String ARG_NOM_ARME = "nom_arme";
    private static final String ARG_PRIX = "prix";
    private static final String ARG_RARETE = "rarete";
    private static final String ARG_IMAGE = "image";

    private String nom;
    private Integer prix;
    private Rarete rarete;
    private Drawable image;

    public Item() {
        // Required empty public constructor
    }

    public static Item newInstance(String nom, Integer prix, Rarete rarete, Drawable image) {
        Item fragment = new Item();
        Bundle args = new Bundle();
        args.putString(ARG_NOM_ARME, nom);
        args.putInt(ARG_PRIX, prix);
        args.putSerializable(ARG_RARETE, rarete);
        args.putParcelable(ARG_IMAGE, (Parcelable) image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nom = getArguments().getString(ARG_NOM_ARME);
            prix = getArguments().getInt(ARG_PRIX);
            rarete = (Rarete) getArguments().getSerializable(ARG_RARETE);
            image = getArguments().getParcelable(ARG_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }
}
