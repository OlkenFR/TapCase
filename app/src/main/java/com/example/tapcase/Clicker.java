package com.example.tapcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Clicker extends AppCompatActivity {
    private ActivityClickerBinding binding;
    BottomNavigationView bottomNavigationView;
    private int score;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        editor = prefs.edit();

        if (prefs.contains("SCORE")) {
            try {
                score = prefs.getInt("SCORE", 0);
            } catch (ClassCastException e) {
                try {
                    score = Integer.parseInt(prefs.getString("SCORE", "0"));
                    editor.putInt("SCORE", score);
                    editor.apply();
                } catch (NumberFormatException ex) {
                    score = 0;
                    editor.putInt("SCORE", score);
                    editor.apply();
                }
            }
        } else {
            score = 0;
            editor.putInt("SCORE", score);
            editor.apply();
        }
        update();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.clicker);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.clicker){
                return true;
            } else if (id == R.id.inventory){
                startActivity(new Intent(getApplicationContext(), Inventory.class));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.store){
                startActivity(new Intent(getApplicationContext(), Store.class));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });
    }

    private void update() {
        score = prefs.getInt("SCORE", 0);
        binding.tvScore.setText(String.valueOf(score));
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnClicker.setOnClickListener(view -> {
            score++;
            editor.putInt("SCORE", score);
            editor.apply();
            update();
        });
    }
}
