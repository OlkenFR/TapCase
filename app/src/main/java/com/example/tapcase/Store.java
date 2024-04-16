package com.example.tapcase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityStoreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        bundleStoreToCaseOpening.putSerializable("GAME_INFO", caseInformation);
        storeToCaseOpening.putExtras(bundleStoreToCaseOpening);
        //ACTION FOR THE CLASSIC BOX TAP
        binding.ivStoreBoxClassic.setOnClickListener(v -> {
            // TODO case à changer et à réassigner au bundle
            //  bundleStoreToCaseOpening.putSerializable("GAME_INFO", caseInformation);
            //  Faire fonction createCase
            startActivity(storeToCaseOpening);
        });
        //ACTION FOR THE DREAMS BOX TAP
        binding.ivStoreBoxDreams.setOnClickListener(v -> {
            startActivity(storeToCaseOpening);
        });
        //ACTION FOR THE BRAVO BOX TAP
        binding.ivStoreBoxBravo.setOnClickListener(v -> {
            startActivity(storeToCaseOpening);
        });
        //ACTION FOR THE COBBLE BOX TAP
        binding.ivStoreBoxCobble.setOnClickListener(v -> {
            startActivity(storeToCaseOpening);
        });
    }
}