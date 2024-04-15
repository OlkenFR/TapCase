package com.example.tapcase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityStoreBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class Store extends AppCompatActivity {

    private ActivityStoreBinding binding;
    BottomNavigationView bottomNavigationView;
    private double scrollSpeed;
    private double scrollPos;
    private Timer timer;
    private final Handler handler = new Handler();
    private int score;
    private int BOX_PRICE_CLASSIC = 10;
    private int BOX_PRICE_DREAMS = 20;
    private int BOX_PRICE_BRAVO = 30;
    private int BOX_PRICE_COBBLE = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

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
        if(intent != null) {
            score = intent.getIntExtra("SCORE", 0);
            binding.tvScore.setText("" + score);
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.clicker){
                startActivity(new Intent(getApplicationContext(), Clicker.class));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.inventory){
                startActivity(new Intent(getApplicationContext(), Inventory.class).putExtra("SCORE", score));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.store){
                return true;
            }
            return false;
        });


        //ACTION FOR THE CLASSIC BOX TAP
        binding.ivStoreBoxClassic.setOnClickListener(v -> {
            startActivity(new Intent(Store.this, CaseOpening.class)
                    .putExtra("SCORE",score)
                    .putExtra("PRICE",BOX_PRICE_CLASSIC)
                    .putExtra("CASE_ID", 0));
        });
        //ACTION FOR THE DREAMS BOX TAP
        binding.ivStoreBoxDreams.setOnClickListener(v -> {
            startActivity(new Intent(Store.this, CaseOpening.class)
                    .putExtra("SCORE",score)
                    .putExtra("PRICE",BOX_PRICE_DREAMS)
                    .putExtra("CASE_ID", 1));
        });
        //ACTION FOR THE BRAVO BOX TAP
        binding.ivStoreBoxBravo.setOnClickListener(v -> {
            startActivity(new Intent(Store.this, CaseOpening.class)
                    .putExtra("SCORE",score)
                    .putExtra("PRICE",BOX_PRICE_BRAVO)
                    .putExtra("CASE_ID", 2));
        });
        //ACTION FOR THE COBBLE BOX TAP
        binding.ivStoreBoxCobble.setOnClickListener(v -> {
            startActivity(new Intent(Store.this, CaseOpening.class)
                    .putExtra("SCORE",score)
                    .putExtra("PRICE",BOX_PRICE_COBBLE)
                    .putExtra("CASE_ID", 3));
        });
    }
}