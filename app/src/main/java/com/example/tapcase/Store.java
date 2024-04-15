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

        bottomNavigationView
                .setOnItemSelectedListener(item -> {
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
            binding.tvTest.setText("Classic");
        });
        //ACTION FOR THE DREAMS BOX TAP
        binding.ivStoreBoxDreams.setOnClickListener(v -> {
            binding.tvTest.setText("Dreams");
        });
        //ACTION FOR THE BRAVO BOX TAP
        binding.ivStoreBoxBravo.setOnClickListener(v -> {
            binding.tvTest.setText("Bravo");
        });
        //ACTION FOR THE COBBLE BOX TAP
        binding.ivStoreBoxCobble.setOnClickListener(v -> {
            binding.tvTest.setText("Cobblestone");
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