package com.example.tapcase;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tapcase.R;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Clicker extends AppCompatActivity {
    private ActivityClickerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
