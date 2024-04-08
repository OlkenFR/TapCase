package com.example.tapcase;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tapcase.R;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Clicker extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private ActivityClickerBinding binding;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.inventory);
    }

    ClickerFragment clickerFragment = new ClickerFragment();
    InventoryFragment inventoryFragment = new InventoryFragment();
    StoreFragment storeFragment = new StoreFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.clicker){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, clickerFragment)
                    .commit();
            return true;
        } else if (id == R.id.inventory){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, inventoryFragment)
                    .commit();
            return true;
        } else if (id == R.id.store){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, storeFragment)
                    .commit();
            return true;
        }
        return false;
    }
}
