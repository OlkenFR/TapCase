package com.example.tapcase;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.example.tapcase.databinding.FragmentClickerBinding;

public class ClickerFragment extends Fragment {
    private FragmentClickerBinding binding;
    private int score;
    public ClickerFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.btnClicker.setOnClickListener(v -> {
            score = score + 1;
            binding.tvScore.setText("" + score);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClickerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}