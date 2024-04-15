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
            binding.tvStore.setText("Score = " + score);
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {
                    int id = item.getItemId();
                    if(id == R.id.clicker){
                        startActivity(new Intent(getApplicationContext(), Clicker.class));
                        overridePendingTransition(0,0);
                        return true;
                    } else if (id == R.id.inventory){
                        startActivity(new Intent(getApplicationContext(), Inventory.class));
                        overridePendingTransition(0,0);
                        return true;
                    } else if (id == R.id.store){
                        return true;
                    }
                    return false;
                });
    }

    @Override
    public void onResume() {
        super.onResume();

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





        //STARTING THE ROLL OF WEAPON
        binding.btnStore.setOnClickListener(v -> {
            scrollPos=0;
            scrollSpeed=30;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollPos += scrollSpeed; // Increment the scroll position
                            if(scrollSpeed>5){
                                scrollSpeed -= 0.1;
                            }else{
                                scrollSpeed -= 0.01;
                            }

                            binding.tvStore.setText("speed=" + (int) scrollSpeed + " pos=" + (int) scrollPos);
                            binding.horizontal.scrollTo((int) scrollPos, 0); // Scroll to the new position
                            if (scrollSpeed <= 0) {
                                timer.cancel(); // Stop the timer when the activity is destroyed
                            }
                        }
                    });
                }
            }, 0, 5); // Exécute la tâche toutes les 1000 millisecondes (1 seconde)
        });
    }
}