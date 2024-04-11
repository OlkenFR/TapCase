package com.example.tapcase;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Clicker extends AppCompatActivity {
    private ActivityClickerBinding binding;
    BottomNavigationView bottomNavigationView;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.clicker);

        bottomNavigationView
                .setOnItemSelectedListener(item -> {
                    int id = item.getItemId();
                    if(id == R.id.clicker){
                        return true;
                    } else if (id == R.id.inventory){
                        startActivity(new Intent(getApplicationContext(), Inventory.class));
                        finish();
                        return true;
                    } else if (id == R.id.store){
                        startActivity(new Intent(getApplicationContext(), Store.class));
                        finish();
                        return true;
                    }
                    return false;
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.btnClicker.setOnClickListener(v -> {
            score = score + 1;
            binding.tvScore.setText("" + score);
        });
    }
}
