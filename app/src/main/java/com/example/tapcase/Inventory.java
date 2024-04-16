package com.example.tapcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
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
import java.util.Collections;
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
    private SharedPreferences.Editor editor;
    private Rarete armeSelectionneRarete;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSort.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.gameInformation = (GameInformation) bundle.getSerializable("GAME_INFO");
        this.playerInformation = this.gameInformation.getPlayerInformation();
        score = prefs.getInt("SCORE", 0);
        updateGridLayout();
        binding.tvScore.setText(String.valueOf(score));
        binding.tvNumberOfWeapon.setText("Nombres d'armes : " + String.valueOf(playerInformation.getPlayer_armes().size()));
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
        int savedPosition = prefs.getInt("SPINNER_POSITION", 0);
        binding.spinnerSort.setSelection(savedPosition);
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

        binding.spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = parentView.getItemAtPosition(position).toString();
                editor.putInt("SPINNER_POSITION", position);
                editor.apply();

                boolean isAscending = binding.ascendingCheckBox.isChecked();

                switch (selectedOption) {
                    case "Prix":
                        Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getPrixComparator(isAscending));
                        break;
                    case "Rareté":
                        Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getRareteComparator(isAscending));
                        break;
                    case "Fleurs/Tirs":
                        Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getFleursTirComparator(isAscending));
                        break;
                    case "Arme":
                        Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getNomComparator(isAscending));
                        break;
                }
                updateGridLayout();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                    for (Item playerItem : fragments_items){
                        if ((arme.getNom().equals(playerInformation.getArme_selectionné().getNom())) && (arme.getNom().equals(playerItem.getNom()))) {
                            iterator.remove();
                            removeItemFragment(playerItem);
                            binding.tvNumberOfWeapon.setText("Nombres d'armes : " + String.valueOf(playerInformation.getPlayer_armes().size()));
                            fragments_items.remove(playerItem);
                            updateGridLayout();
                            break;
                        }
                    }
                }
                Random random = new Random();
                int randomIndex = random.nextInt(playerInformation.getPlayer_armes().size());
                Arme nextArme = playerInformation.getPlayer_armes().get(randomIndex);
                binding.tvArmeSelectionne.setText(nextArme.getNom());
                binding.tvWeaponsPerformance.setText(nextArme.getFlower_per_click() + " fleurs/tirs");
                binding.tvWeaponPrice.setText("Prix : " + nextArme.getPrix());
                int newImageResId = getResources().getIdentifier(nextArme.getFileName(), "drawable", getPackageName());
                binding.ivWeaponSelected.setImageResource(newImageResId);
                binding.btnSelect.setText("Sélectionner");
                binding.btnSelect.setEnabled(true);
                playerInformation.getArme_selectionné().setFileName(nextArme.getFileName());
                playerInformation.getArme_selectionné().setNom(nextArme.getNom());
                playerInformation.getArme_selectionné().setPrix(nextArme.getPrix());
                playerInformation.getArme_selectionné().setRarete(nextArme.getRarete());
                playerInformation.getArme_selectionné().setFlower_per_click(nextArme.getFlower_per_click());
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

        binding.ascendingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSortedList(isChecked);
            }
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

    private void updateSortedList(boolean isAscending) {
        String selectedOption = binding.spinnerSort.getSelectedItem().toString();
        switch (selectedOption) {
            case "Prix":
                Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getPrixComparator(isAscending));
                break;
            case "Rareté":
                Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getRareteComparator(isAscending));
                break;
            case "Fleurs/Tirs":
                Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getFleursTirComparator(isAscending));
                break;
            case "Arme":
                Collections.sort(playerInformation.getPlayer_armes(), ArmeComparator.getNomComparator(isAscending));
                break;
        }
        updateGridLayout();
    }

    private void updateGridLayout() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        binding.gridLayout.removeAllViews();
        for (Arme arme : playerInformation.getPlayer_armes()) {
            int imageResId = getResources().getIdentifier(arme.getFileName(), "drawable", getPackageName());
            Item item = Item.newInstance(arme.getNom(), arme.getFileName(), arme.getPrix(), arme.getRarete(), imageResId, arme.getFlower_per_click());
            fragments_items.add(item);
            ft.add(binding.gridLayout.getId(), item);
        }
        ft.commit();
    }


    public static void select_weapons(String selected_weapons, String fileName, Drawable drawableID, Rarete rarete, Integer prix, String fleur_click, Resources resources) {
        playerInformation.getArme_selectionné().setFileName(fileName);
        playerInformation.getArme_selectionné().setNom(selected_weapons);
        playerInformation.getArme_selectionné().setPrix(prix);
        playerInformation.getArme_selectionné().setRarete(rarete);
        playerInformation.getArme_selectionné().setFlower_per_click(Integer.parseInt(fleur_click));
        binding.tvArmeSelectionne.setText(selected_weapons);
        binding.tvWeaponsPerformance.setText(fleur_click + " fleurs/tirs");
        binding.tvWeaponPrice.setText("Prix : " + prix);
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