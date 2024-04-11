package com.example.tapcase;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.tapcase.databinding.ActivityInventoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends AppCompatActivity {

    private static ActivityInventoryBinding binding;

    BottomNavigationView bottomNavigationView;

    private List<Item> fragments_items = new ArrayList<>();

    public static void select_weapons(String selected_weapons, Drawable drawableID, Rarete rarete, Integer prix, String fleur_click, Resources resources) {
        binding.tvArmeSelectionne.setText(selected_weapons);
        binding.tvWeaponsPerformance.setText(fleur_click + " fleurs/click");
        binding.tvWeaponPrice.setText("Valeur :" + prix);
        binding.ivWeaponSelected.setImageDrawable(drawableID);
        switch (rarete){
            case BASE:
                binding.ivWeaponSelected.setBackground(resources.getDrawable(R.drawable.arrondir_base));
                break;
            case EXOTIQUE:
                binding.ivWeaponSelected.setBackground(resources.getDrawable(R.drawable.arrondir_exotique));
                break;
            case SUPERIEUR:
                binding.ivWeaponSelected.setBackground(resources.getDrawable(R.drawable.arrondir_superieur));
                break;
            case CLASSIFIE:
                binding.ivWeaponSelected.setBackground(resources.getDrawable(R.drawable.arrondir_classifie));
                break;
            case SECRET:
                binding.ivWeaponSelected.setBackground(resources.getDrawable(R.drawable.arrondir_secret));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        List<Item> itemsFromTextFile = readItemsFromTextFile();
        for (Item item : itemsFromTextFile) {
            ft.add(binding.gridLayout.getId(), item);
        }

        binding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for button click
            }
        });

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.inventory);

        bottomNavigationView
                .setOnItemSelectedListener(item -> {
                    int id = item.getItemId();
                    if(id == R.id.clicker){
                        startActivity(new Intent(getApplicationContext(), Clicker.class));
                        finish();
                        return true;
                    } else if (id == R.id.inventory){
                        return true;
                    } else if (id == R.id.store){
                        startActivity(new Intent(getApplicationContext(), Store.class));
                        finish();
                        return true;
                    }
                    return false;
                });
    }

    private List<Item> readItemsFromTextFile() {
        List<Item> itemsList = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.item_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] itemData = line.split(",");
                String nom = itemData[0];
                int prix = Integer.parseInt(itemData[1]);
                Rarete rarete = Rarete.valueOf(itemData[2]);
                int imageResId = getResources().getIdentifier(itemData[3], "drawable", getPackageName());
                int fleurs_par_click = Integer.parseInt(itemData[4]);
                itemsList.add(Item.newInstance(nom, prix, rarete,imageResId, fleurs_par_click));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return itemsList;
    }
}