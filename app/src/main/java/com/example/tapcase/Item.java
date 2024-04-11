package com.example.tapcase;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tapcase.databinding.FragmentItemBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Item extends Fragment {
    private FragmentItemBinding binding;
    private static final String ARG_NOM_ARME = "nom_arme";
    private static final String ARG_PRIX = "prix";
    private static final String ARG_RARETE = "rarete";
    private static final String ARG_IMAGE = "image";
    private static final String ARG_FLEURS_CLIQUE = "fleurs_clique";

    private String nom;
    private Integer prix;
    private Integer fleurs_par_click;
    private Rarete rarete = Rarete.BASE;
    private Drawable image;


    public Item() {

    }

    public static Item newInstance(String nom, Integer prix, Rarete rarete, int imageResId, int fleurs_par_click) {
        Item fragment = new Item();
        Bundle args = new Bundle();
        args.putString(ARG_NOM_ARME, nom);
        args.putInt(ARG_PRIX, prix);
        args.putSerializable(ARG_RARETE, rarete);
        args.putInt(ARG_IMAGE, imageResId);
        args.putInt(ARG_FLEURS_CLIQUE, fleurs_par_click);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nom = getArguments().getString(ARG_NOM_ARME);
            prix = getArguments().getInt(ARG_PRIX);
            fleurs_par_click = getArguments().getInt(ARG_FLEURS_CLIQUE);
            rarete = (Rarete) getArguments().getSerializable(ARG_RARETE);
            image = getResources().getDrawable(getArguments().getInt(ARG_IMAGE));
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        binding.ivImageArme.setImageDrawable(image);
        switch (rarete){
            case BASE:
                binding.ivImageArme.setBackground(getResources().getDrawable(R.drawable.arrondir_base));
                break;
            case EXOTIQUE:
                binding.ivImageArme.setBackground(getResources().getDrawable(R.drawable.arrondir_exotique));
                break;
            case SUPERIEUR:
                binding.ivImageArme.setBackground(getResources().getDrawable(R.drawable.arrondir_superieur));
                break;
            case CLASSIFIE:
                binding.ivImageArme.setBackground(getResources().getDrawable(R.drawable.arrondir_classifie));
                break;
            case SECRET:
                binding.ivImageArme.setBackground(getResources().getDrawable(R.drawable.arrondir_secret));
                break;
        }
        binding.ivImageArme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inventory.select_weapons(nom, image, rarete, prix, String.valueOf(fleurs_par_click), getResources());
            }
        });

        return binding.getRoot();
    }

}
