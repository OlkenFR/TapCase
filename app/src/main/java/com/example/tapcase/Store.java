package com.example.tapcase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityStoreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Store extends AppCompatActivity {

    private ActivityStoreBinding binding;
    BottomNavigationView bottomNavigationView;
    private GameInformation gameInformation;
    private PlayerInformation playerInformation;
    private CaseInformation caseInformation;
    private int score;
    private List<Case> caseAvailable;
    private List<Arme> armeAvailable;
    private int BOX_PRICE_CLASSIC = 10;
    private int BOX_PRICE_DREAMS = 20;
    private int BOX_PRICE_BRAVO = 30;
    private int BOX_PRICE_COBBLE = 40;

    private static final String BOX_CLASSIC_LINK = "box_classic";
    private static final String BOX_BRAVO_LINK = "box_bravo_operation";
    private static final String BOX_DREAMS_LINK = "box_dreams_nightmares";
    private static final String BOX_COBBLESTONE_LINK = "box_cobblestone_memory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.store);
        binding.tvStoreBoxClassic.setText("Price = " + BOX_PRICE_CLASSIC);
        binding.tvStoreBoxDreams.setText("Price = " + BOX_PRICE_DREAMS);
        binding.tvStoreBoxBravo.setText("Price = " + BOX_PRICE_BRAVO);
        binding.tvStoreBoxCooblestone.setText("Price = " + BOX_PRICE_COBBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        this.gameInformation = (GameInformation) bundle.getSerializable("GAME_INFO");
        this.playerInformation = this.gameInformation.getPlayerInformation();
        this.armeAvailable = this.gameInformation.getWeaponAvailable();
        binding.tvScore.setText(String.valueOf(playerInformation.getScore()));
        Intent storeToInventory = new Intent(Store.this, Inventory.class);
        Bundle bundleStoreToInventory = new Bundle();
        bundleStoreToInventory.putSerializable("GAME_INFO", gameInformation);
        storeToInventory.putExtras(bundleStoreToInventory);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.clicker){
                startActivity(new Intent(Store.this, Clicker.class));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.inventory){
                startActivity(storeToInventory);
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.store){
                return true;
            }
            return false;
        });





        Intent storeToCaseOpening = new Intent(Store.this, CaseOpening.class);
        Bundle bundleStoreToCaseOpening = new Bundle();



        //ACTION FOR THE CLASSIC BOX TAP
        binding.ivStoreBoxClassic.setOnClickListener(v -> {
            // TODO case à changer et à réassigner au bundle
            //  bundleStoreToCaseOpening.putSerializable("GAME_INFO", caseInformation);
            //  Faire fonction createCase

            List<Arme> weaponList = new ArrayList<>();
            List<Integer> list = new ArrayList<>(Arrays.asList(1, 6, 12, 17, 20, 26, 31));
            weaponList = loadCase(list, weaponList);
            Case weaponCase = new Case(weaponList, CaseType.ClassicCase, BOX_PRICE_CLASSIC, BOX_CLASSIC_LINK, weaponList.get(0));
            caseInformation = new CaseInformation(gameInformation, weaponCase);


            bundleStoreToCaseOpening.putSerializable("CASE_INFO", caseInformation);
            storeToCaseOpening.putExtras(bundleStoreToCaseOpening);
            startActivity(storeToCaseOpening);
        });
        //ACTION FOR THE DREAMS BOX TAP
        binding.ivStoreBoxDreams.setOnClickListener(v -> {
            List<Arme> weaponList = new ArrayList<>();
            List<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 11, 16, 22, 25, 30));
            weaponList = loadCase(list, weaponList);
            Case weaponCase = new Case(weaponList, CaseType.DreamsCase, BOX_PRICE_DREAMS, BOX_DREAMS_LINK, weaponList.get(0));
            caseInformation = new CaseInformation(gameInformation, weaponCase);

            bundleStoreToCaseOpening.putSerializable("CASE_INFO", caseInformation);
            storeToCaseOpening.putExtras(bundleStoreToCaseOpening);
            startActivity(storeToCaseOpening);
        });
        //ACTION FOR THE BRAVO BOX TAP
        binding.ivStoreBoxBravo.setOnClickListener(v -> {
            List<Arme> weaponList = new ArrayList<>();
            List<Integer> list = new ArrayList<>(Arrays.asList(2, 8, 10, 18, 21, 27, 32));
            weaponList = loadCase(list, weaponList);
            Case weaponCase = new Case(weaponList, CaseType.BravoCase, BOX_PRICE_BRAVO, BOX_BRAVO_LINK, weaponList.get(0));
            caseInformation = new CaseInformation(gameInformation, weaponCase);

            bundleStoreToCaseOpening.putSerializable("CASE_INFO", caseInformation);
            storeToCaseOpening.putExtras(bundleStoreToCaseOpening);
            startActivity(storeToCaseOpening);
        });
        //ACTION FOR THE COBBLE BOX TAP
        binding.ivStoreBoxCobble.setOnClickListener(v -> {
            List<Arme> weaponList = new ArrayList<>();
            List<Integer> list = new ArrayList<>(Arrays.asList(4, 7, 13, 15, 23, 28, 33));
            weaponList = loadCase(list, weaponList);
            Case weaponCase = new Case(weaponList, CaseType.CobbleCase, BOX_PRICE_COBBLE, BOX_COBBLESTONE_LINK, weaponList.get(0));
            caseInformation = new CaseInformation(gameInformation, weaponCase);

            bundleStoreToCaseOpening.putSerializable("CASE_INFO", caseInformation);
            storeToCaseOpening.putExtras(bundleStoreToCaseOpening);
            startActivity(storeToCaseOpening);
        });
    }
    private List<Arme> loadCase(List<Integer> list, List<Arme> weaponList){
        for(Integer id : list) {
            weaponList.add(armeAvailable.get(id));
        }
        return weaponList;
    }

}