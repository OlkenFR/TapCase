package com.example.tapcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityCaseOpeningBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CaseOpening extends AppCompatActivity {
    private ActivityCaseOpeningBinding binding;
    private double scrollSpeed;
    private double scrollPos;
    private Timer timer;
    private final Handler handler = new Handler();
    private CaseInformation caseInformation;
    private GameInformation gameInformation;
    private PlayerInformation playerInformation;
    private int price;
    private String name;
    private int score;
    private int caseID;
    private SharedPreferences prefs;
    private String playerWeaponsList;
    private SharedPreferences.Editor editor;
    MediaPlayer mediaPlayer;
    private Arme unlockedWeapon;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mediaPlayer = MediaPlayer.create(this, R.raw.open_sound);
        prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }


    @Override
    public void onResume() {
            super.onResume();
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            this.caseInformation = (CaseInformation) bundle.getSerializable("CASE_INFO");
            this.gameInformation = this.caseInformation.getGameInformation();
            this.playerInformation = this.gameInformation.getPlayerInformation();
            score = prefs.getInt("SCORE", 0);
            binding.tvScore.setText("" + score);
            price = caseInformation.getCaseInfomation().getPrix();
            binding.tvOpenningCasePrice.setText("" + price);
            name = caseInformation.getCaseInfomation().getCaseName();
            binding.tvCaseName.setText("" + name);
            binding.ivCase.setImageResource(getResources().getIdentifier(caseInformation.getCaseInfomation().getCaseFileName(), "drawable", getPackageName()));
            //CANCEL BUTTON SIMULATE A CLICK ON THE STORE
            binding.btnCancel.setOnClickListener(v -> {
                binding.bottomNavigationView.getMenu().performIdentifierAction(R.id.store, 0);
            });
        binding.btnSeeInventory.setOnClickListener(v -> {
            binding.bottomNavigationView.getMenu().performIdentifierAction(R.id.inventory, 0);
        });

        //DISPLAY THE AVAILABLE WEAPON IN THE CASE
        boolean isAscending = true;
        Case weaponCase = caseInformation.getCaseInfomation();
        Collections.sort(weaponCase.getArmeDispo(), ArmeComparator.getRareteComparator(isAscending));
        int compteur = 0;
        LinearLayout.LayoutParams layoutParams = new TableRow.LayoutParams(270, 270);
        for(Arme weapon : weaponCase.getArmeDispo()){

            ImageView imageView = new ImageView(getApplicationContext());
            int newImageRessourceId = getResources().getIdentifier(weapon.getFileName(), "drawable", getPackageName());
            imageView.setImageResource(newImageRessourceId);
            Rarete weaponRarity = weapon.getRarete();
            if (weaponRarity == Rarete.BASE){
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_base));
            } else if (weaponRarity == Rarete.SUPERIEUR) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_superieur));
            } else if (weaponRarity == Rarete.EXOTIQUE) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_exotique));
            } else if (weaponRarity == Rarete.CLASSIFIE) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_classifie));
            } else if (weaponRarity == Rarete.SECRET) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_secret));
            }

            imageView.setLayoutParams(layoutParams);
            if(compteur<4){
                binding.tr1.addView(imageView);
            } else {
                binding.tr2.addView(imageView);
            }
            compteur++;
        }


        //RANDOMIZE THE CASE
        List<Arme> randomList = new ArrayList<>();
        Arme randomWeapon = weaponCase.getArmeDispo().get(0);
        Random random = new Random();
        for (int i=0; i<15; i++){
            int randomNumber = random.nextInt(100);
            if (randomNumber<13){
                randomWeapon = weaponCase.getArmeDispo().get(0);
            } else if (randomNumber<35) {
                randomWeapon = weaponCase.getArmeDispo().get(1);
            } else if (randomNumber<70) {
                randomWeapon = weaponCase.getArmeDispo().get(2);
            } else if (randomNumber<80) {
                randomWeapon = weaponCase.getArmeDispo().get(3);
            } else if (randomNumber<90) {
                randomWeapon = weaponCase.getArmeDispo().get(4);
            } else if (randomNumber<98) {
                randomWeapon = weaponCase.getArmeDispo().get(5);
            } else if (randomNumber<100) {
                randomWeapon = weaponCase.getArmeDispo().get(6);
            }

            ImageView imageView = new ImageView(getApplicationContext());

            int newImageRessourceId = getResources().getIdentifier(randomWeapon.getFileName(), "drawable", getPackageName());
            imageView.setImageResource(newImageRessourceId);

            Rarete weaponRarity = randomWeapon.getRarete();
            if (weaponRarity == Rarete.BASE){
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_base));
            } else if (weaponRarity == Rarete.SUPERIEUR) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_superieur));
            } else if (weaponRarity == Rarete.EXOTIQUE) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_exotique));
            } else if (weaponRarity == Rarete.CLASSIFIE) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_classifie));
            } else if (weaponRarity == Rarete.SECRET) {
                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_secret));
            }
            randomList.add(randomWeapon);
            binding.linear.addView(imageView);
        }

        //STARTING THE ROLL OF WEAPON
        binding.btnOpenning.setOnClickListener(v -> {
            if (score >= price) {
                binding.btnOpenning.setClickable(false);

                binding.linearCaseOpen.setVisibility(View.INVISIBLE);
                binding.rectangleView.setVisibility(View.VISIBLE);
                binding.linearOpenOpen.setVisibility(View.VISIBLE);
                binding.linearLayoutCasePrice.setVisibility(View.INVISIBLE);

                score = score - price;

                editor.putInt("SCORE", score);
                editor.apply();
                binding.tvScore.setText("" + score);

                //SOUND PART FOR OPENNING
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.stop();
                                mediaPlayer.prepareAsync();
                                mediaPlayer.seekTo(0);
                            }
                        }
                    }, 3500);
                    unlockedWeapon = randomList.get(10);
                    playerInformation.getPlayer_armes().add(unlockedWeapon);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            displayToastUnlockedWeapon();
                            binding.btnSeeInventory.setVisibility(View.VISIBLE);
                        }
                    }, 3500);
                }
                scrollPos = 0;
                scrollSpeed = 30;
                Random randomTimer = new Random();
                double randomNumber = randomTimer.nextInt(5);
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
                                    scrollSpeed -= (randomNumber+11)/1000;//entre:0.011 et 0.015
                                }
                                binding.linearOpenOpen.scrollTo((int) scrollPos, 0); // Scroll to the new position
                                if (scrollSpeed < 0.5) {
                                    scrollSpeed = 0;
                                    timer.cancel();
                                }
                            }
                        });
                    }
                }, 0, 5);//ROLL EVERY 5MS
                playerWeaponsList = gson.toJson(playerInformation.getPlayer_armes());
                editor.putString("NOM_ARMES_JOUEUR", playerWeaponsList);
                editor.apply();
            } else {
                displayToastCantBuy();
            }
        });

        Intent caseOpeningToStore = new Intent(CaseOpening.this, Store.class);
        Intent caseOpeningToInventory = new Intent(CaseOpening.this, Inventory.class);
        Intent caseOpeningToClicker = new Intent(CaseOpening.this, Clicker.class);
        Bundle bundleCaseOpeningToStore = new Bundle();
        Bundle bundleCaseOpeningToInventory = new Bundle();
        bundleCaseOpeningToStore.putSerializable("GAME_INFO", gameInformation);
        caseOpeningToStore.putExtras(bundleCaseOpeningToStore);
        bundleCaseOpeningToInventory.putSerializable("GAME_INFO", gameInformation);
        caseOpeningToInventory.putExtras(bundleCaseOpeningToInventory);
        String playerWeaponsList = gson.toJson(playerInformation.getPlayer_armes());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.clicker) {
                startActivity(caseOpeningToClicker);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.inventory) {
                startActivity(caseOpeningToInventory);
                overridePendingTransition(0, 0);
                return true;
            } else if (id == R.id.store) {
                startActivity(caseOpeningToStore);
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

    }
    //DESTROY MEDIAPLAYER
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void displayToastUnlockedWeapon(){
        Toast.makeText(CaseOpening.this, "Nouvelle arme débloqué : "+ unlockedWeapon.getNom(), Toast.LENGTH_SHORT).show();
    }
    private void displayToastCantBuy(){
        Toast.makeText(CaseOpening.this, "Vous n'avez pas assez de fleurs.", Toast.LENGTH_SHORT).show();
    }
}