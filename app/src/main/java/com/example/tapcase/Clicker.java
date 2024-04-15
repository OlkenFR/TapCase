package com.example.tapcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class Clicker extends AppCompatActivity {
    private ActivityClickerBinding binding;
    BottomNavigationView bottomNavigationView;
    MediaPlayer mediaPlayer;

    private int score;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaPlayer = MediaPlayer.create(this, R.raw.bruit_balle_2);

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

    }

    private void update() {
        score = prefs.getInt("SCORE", 0);
        binding.tvScore.setText(String.valueOf(score));
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.clicker){
                return true;
            } else if (id == R.id.inventory){
                startActivity(new Intent(getApplicationContext(), Inventory.class).putExtra("SCORE", score));
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.store){
                startActivity(new Intent(getApplicationContext(), Store.class).putExtra("SCORE", score));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });

        ViewGroup parent = (ViewGroup) binding.getRoot();
        parent.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mediaPlayer.start();
                score++;
                editor.putInt("SCORE", score);
                editor.apply();
                update();

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
                texteViewTir.setPadding(newMarginX, newMarginY, 0, 0);
                texteViewTir.setLayoutParams(paramsLayout);

                //CODE FOR PRINTING IMAGE WHILE SHOOTING
                ImageView imageView = new ImageView(Clicker.this);
                imageView.setImageResource(R.drawable.fleur);
                paramsLayout.width = newMarginX + 200;
                paramsLayout.height = newMarginY + 200;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(newMarginX, newMarginY, 0, 0);
                imageView.setLayoutParams(paramsLayout);

                binding.main.addView(imageView);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.main.removeView(imageView);
                    }
                }, 1000); //for 1 seconds
            } else if (event.getAction() == MotionEvent.ACTION_UP){
                mediaPlayer.seekTo(0);
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}


