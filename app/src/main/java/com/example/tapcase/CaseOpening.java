package com.example.tapcase;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tapcase.databinding.ActivityCaseOpenerBinding;
import com.example.tapcase.databinding.ActivityCaseOpeningBinding;

public class CaseOpening extends AppCompatActivity {
    private ActivityCaseOpeningBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}