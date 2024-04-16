package com.example.tapcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.tapcase.databinding.ActivityInventoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Inventory extends AppCompatActivity {
    private static ActivityInventoryBinding binding;
    private BottomNavigationView bottomNavigationView;
    private Gson gson;
    private GameInformation gameInformation;
    private static PlayerInformation playerInformation;
    private Integer score;
    private SharedPreferences prefs;
    private Rarete armeSelectionneRarete;
    private SharedPreferences.Editor editor;
    private List<Item> fragments_items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gson = new Gson();
        prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        editor = prefs.edit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.inventory);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.gameInformation = (GameInformation) bundle.getSerializable("GAME_INFO");
        this.playerInformation = this.gameInformation.getPlayerInformation();
        score = playerInformation.getScore();
        List<Item> playerItem = new ArrayList<>();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Arme arme : playerInformation.getPlayer_armes()){
            int imageResId = getResources().getIdentifier(arme.getFileName(), "drawable", getPackageName());
            playerItem.add(Item.newInstance(arme.getNom(), arme.getFileName(), arme.getPrix(), arme.getRarete(), imageResId,arme.getFlower_per_click()));
        }
        for (Item item : playerItem) {
            ft.add(binding.gridLayout.getId(), item);
        }
        ft.commit();
        binding.tvScore.setText("" + score);
        binding.btnSelect.setEnabled(false);
        binding.btnSelect.setText("Sélectionné");
        binding.btnVendre.setBackgroundColor(R.color.RED);
        binding.btnSelect.setBackgroundColor(R.color.GREEN);
        switch (playerInformation.getArme_selectionné().getRarete()){
            case BASE:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_base));
                break;
            case EXOTIQUE:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_exotique));
                break;
            case SUPERIEUR:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_superieur));
                break;
            case CLASSIFIE:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_classifie));
                break;
            case SECRET:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_secret));
                break;
        }
        binding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnSelect.setEnabled(false);
                binding.btnSelect.setText("Séléctionné");
                editor.putString("WEAPON_SELECTED", playerInformation.getArme_selectionné().getFileName());
                editor.putInt("RARETE", playerInformation.getArme_selectionné().getRarete().getValue());
                editor.putString("NOM", playerInformation.getArme_selectionné().getNom());
                editor.putInt("PRIX", playerInformation.getArme_selectionné().getPrix());
                editor.putInt("FLOWER_PER_CLICK", playerInformation.getArme_selectionné().getFlower_per_click());
                editor.apply();
            }
        });
        binding.btnVendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerInformation.getArme_selectionné().getFileName().equals("glock_classique")) {
                    Toast.makeText(Inventory.this, "Vous ne pouvez pas vendre cette arme.", Toast.LENGTH_SHORT).show();
                    return;
                }
                score = score + playerInformation.getArme_selectionné().getPrix();
                Iterator<Arme> iterator = playerInformation.getPlayer_armes().iterator();
                while (iterator.hasNext()) {
                    Arme arme = iterator.next();
                    for (Item playerItem : playerItem){
                        if ((arme.getNom().equals(playerInformation.getArme_selectionné().getNom())) && (arme.getNom().equals(playerItem.getNom()))) {
                            iterator.remove();
                            removeItemFragment(playerItem);
                            break;
                        }
                    }
                }
                Random random = new Random();
                int randomIndex = random.nextInt(playerInformation.getPlayer_armes().size());
                Arme nextArme = playerInformation.getPlayer_armes().get(randomIndex);
                binding.tvArmeSelectionne.setText(nextArme.getNom());
                binding.tvWeaponsPerformance.setText(nextArme.getFlower_per_click() + " fleurs/tirs");
                binding.tvWeaponPrice.setText("Prix :" + nextArme.getPrix());
                int newImageResId = getResources().getIdentifier(nextArme.getFileName(), "drawable", getPackageName());
                binding.ivWeaponSelected.setImageResource(newImageResId);
                binding.btnSelect.setText("Sélectionner");
                binding.btnSelect.setEnabled(true);
                switch (nextArme.getRarete()) {
                    case BASE:
                        binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_base));
                        break;
                    case EXOTIQUE:
                        binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_exotique));
                        break;
                    case SUPERIEUR:
                        binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_superieur));
                        break;
                    case CLASSIFIE:
                        binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_classifie));
                        break;
                    case SECRET:
                        binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_secret));
                        break;
                }
                editor.putString("WEAPON_SELECTED", nextArme.getFileName());
                editor.putInt("RARETE", nextArme.getRarete().getValue());
                editor.putString("NOM", nextArme.getNom());
                editor.putInt("PRIX", nextArme.getPrix());
                editor.putInt("FLOWER_PER_CLICK", nextArme.getFlower_per_click());
                String playerWeapons = gson.toJson(playerInformation.getPlayer_armes());
                editor.putInt("SCORE", score);
                editor.putString("NOM_ARMES_JOUEUR", playerWeapons);
                editor.apply();
                update();
            }
        });


        int resourceId = getResources().getIdentifier("glock_classique", "drawable", getPackageName());
        String fileName = playerInformation.getArme_selectionné().getFileName();
        int imageResId = getResources().getIdentifier(fileName, "drawable", getPackageName());
        binding.ivWeaponSelected.setImageResource(imageResId);
        switch (playerInformation.getArme_selectionné().getRarete()) {
            case BASE:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_base));
                break;
            case EXOTIQUE:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_exotique));
                break;
            case SUPERIEUR:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_superieur));
                break;
            case CLASSIFIE:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_classifie));
                break;
            case SECRET:
                binding.ivWeaponSelected.setBackground(getDrawable(R.drawable.arrondir_secret));
                break;
        }
        binding.tvWeaponsPerformance.setText(String.valueOf(playerInformation.getArme_selectionné().getFlower_per_click()) + " fleurs/tirs");
        binding.tvWeaponPrice.setText("Prix : " + String.valueOf(playerInformation.getArme_selectionné().getPrix()));
        binding.tvArmeSelectionne.setText(String.valueOf(playerInformation.getArme_selectionné().getNom()));
        Intent inventoryToStore = new Intent(Inventory.this, Store.class);
        Bundle bundleInventoryToStore = new Bundle();
        bundleInventoryToStore.putSerializable("GAME_INFO", gameInformation);
        inventoryToStore.putExtras(bundleInventoryToStore);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.clicker) {
                startActivity(new Intent(Inventory.this, Clicker.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.store) {
                startActivity(inventoryToStore);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.inventory) {
                return true;
            }
            return false;
        });
    }

    private void update() {
        score = prefs.getInt("SCORE", 0);
        binding.tvScore.setText(String.valueOf(score));
    }

    private void removeItemFragment(Item item) {
        getSupportFragmentManager().beginTransaction().remove(item).commit();
    }

    private List<Item> loadPlayerItem() {
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
                String fileName = itemData[3];
                int imageResId = getResources().getIdentifier(fileName, "drawable", getPackageName());
                int fleurs_par_click = Integer.parseInt(itemData[4]);
                itemsList.add(Item.newInstance(nom, fileName, prix, rarete,imageResId, fleurs_par_click));
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

    public static void select_weapons(String selected_weapons, String fileName, Drawable drawableID, Rarete rarete, Integer prix, String fleur_click, Resources resources) {
        playerInformation.getArme_selectionné().setFileName(fileName);
        playerInformation.getArme_selectionné().setNom(selected_weapons);
        playerInformation.getArme_selectionné().setPrix(prix);
        playerInformation.getArme_selectionné().setRarete(rarete);
        playerInformation.getArme_selectionné().setFlower_per_click(Integer.parseInt(fleur_click));
        binding.tvArmeSelectionne.setText(selected_weapons);
        binding.tvWeaponsPerformance.setText(fleur_click + " fleurs/tirs");
        binding.tvWeaponPrice.setText("Prix :" + prix);
        binding.ivWeaponSelected.setImageDrawable(drawableID);
        binding.btnSelect.setText("Sélectionner");
        binding.btnSelect.setEnabled(true);
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
}