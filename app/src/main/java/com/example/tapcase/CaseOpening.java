package com.example.tapcase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tapcase.databinding.ActivityCaseOpeningBinding;

import java.util.Timer;
import java.util.TimerTask;

public class CaseOpening extends AppCompatActivity {
    private ActivityCaseOpeningBinding binding;
    private double scrollSpeed;
    private double scrollPos;
    private Timer timer;
    private final Handler handler = new Handler();
    private int price;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null) {
            price = intent.getIntExtra("PRICE", 0);
            score = intent.getIntExtra("SCORE", 0);
            binding.tvOpenningCasePrice.setText("Price = " + price);
            binding.tvOpeningScore.setText("Score = " + score);
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.clicker){
                startActivity(new Intent(CaseOpening.this, Clicker.class).putExtra("SCORE",score));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.inventory){
                startActivity(new Intent(CaseOpening.this, Inventory.class).putExtra("SCORE",score));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.store){
                startActivity(new Intent(CaseOpening.this, Store.class).putExtra("SCORE",score));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });




        //STARTING THE ROLL OF WEAPON
        binding.btnOpenning.setOnClickListener(v -> {
            if (score >= price) {
                score = score - price;
                binding.tvOpeningScore.setText("Score = " + score);

                binding.btnOpenning.setClickable(false);
                scrollPos = 0;
                scrollSpeed = 30;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollPos += scrollSpeed; // Increment the scroll position
                                if (scrollSpeed > 5) {
                                    scrollSpeed -= 0.1;
                                } else {
                                    scrollSpeed -= 0.01;
                                }

                                binding.btnOpenning.setText("speed=" + (int) scrollSpeed + " pos=" + (int) scrollPos);
                                binding.horizontal.scrollTo((int) scrollPos, 0); // Scroll to the new position
                                if (scrollSpeed < 0.5) {
                                    scrollSpeed = 0;
                                    timer.cancel(); // Stop the timer when the activity is destroyed
                                    binding.btnOpenning.setClickable(true);
                                }
                            }
                        });
                    }
                }, 0, 5); // Exécute la tâche toutes les 1000 millisecondes (1 seconde)
            }
        });
    }
}