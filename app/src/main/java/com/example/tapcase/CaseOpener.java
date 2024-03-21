package com.example.tapcase;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tapcase.databinding.ActivityCaseOpenerBinding;

public class CaseOpener extends AppCompatActivity {
    private ActivityCaseOpenerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseOpenerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}