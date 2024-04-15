package com.example.tapcase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

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
                        startActivity(new Intent(Clicker.this, Inventory.class).putExtra("SCORE",score));
                        overridePendingTransition(0,0);
                        return true;
                    } else if (id == R.id.store){
                        startActivity(new Intent(Clicker.this, Store.class).putExtra("SCORE",score));
                        overridePendingTransition(0,0);
                        return true;
                    }
                    return false;
                });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null) {
            score = intent.getIntExtra("SCORE", 0);
            binding.tvScore.setText("Score = " + score);
        }

        ViewGroup parent = (ViewGroup) binding.getRoot();
        parent.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                score += 1;
                binding.tvScore.setText("Score = " + score);

                RelativeLayout.LayoutParams paramsLayout = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );


                Random random = new Random();
                int randomX = random.nextInt(200);
                int randomY = random.nextInt(200);

                int newMarginX = (int) event.getX() - randomX;
                int newMarginY = (int) event.getY() - randomY;

                //CODE FOR PRINTING TEXT WHILE SHOOTING
                TextView texteViewTir = new TextView(Clicker.this);
                texteViewTir.setText("o");
                texteViewTir.setTextSize(20);
                texteViewTir.setPadding(newMarginX,newMarginY, 0, 0);
                texteViewTir.setLayoutParams(paramsLayout);

                //CODE FOR PRINTING IMAGE WHILE SHOOTING
                ImageView imageView = new ImageView(Clicker.this);
                imageView.setImageResource(R.drawable.fleur);
                paramsLayout.width = newMarginX+200;
                paramsLayout.height = newMarginY+200;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(newMarginX,newMarginY, 0, 0);
                imageView.setLayoutParams(paramsLayout);

                binding.main.addView(imageView);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.main.removeView(imageView);
                    }
                }, 1000); //for 1 seconds
            }
            return true;
        });
    }
}
