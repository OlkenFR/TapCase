package com.example.tapcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityCaseOpeningBinding;

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
    private int score;
    private int caseID;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseOpeningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mediaPlayer = MediaPlayer.create(this, R.raw.open_sound);
        prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        editor = prefs.edit();

//        Arme glock = new Arme(Rarete.SUPERIEUR, 2, 10, "Glock-17 Weasel");
//        Arme usp = new Arme(getResources().getDrawable(R.drawable.usp_flashback), Rarete.SUPERIEUR, 7, 28, "USP-S Flashback");
//        Arme mp9 = new Arme(getResources().getDrawable(R.drawable.mp_goo), Rarete.SUPERIEUR, 20, 67, "MP9 Goo");
//        Arme m4a1 = new Arme(getResources().getDrawable(R.drawable.m_printstream), Rarete.EXOTIQUE, 28, 110, "M4A1-S Printstream");
//        Arme ak47 = new Arme(getResources().getDrawable(R.drawable.ak_elite_build), Rarete.BASE, 28, 108, "AK-47 Elite build");
//        Arme awp = new Arme(getResources().getDrawable(R.drawable.awp_atheris), Rarete.SUPERIEUR, 53, 178, "AWP Atheris");
//        Arme knife = new Arme(getResources().getDrawable(R.drawable.knife_freehand), Rarete.SECRET, 91, 275, "Huntsman Freehand");
//
//        if(caseID== 1){
//            glock = new Arme(getResources().getDrawable(R.drawable.glock_neo_noir), Rarete.EXOTIQUE, 7, 40, "Glock-17 Black-Neo");
//            usp = new Arme(getResources().getDrawable(R.drawable.usp_cyrex), Rarete.BASE, 22, 78, "USP-S Cyrex");
//            mp9 = new Arme(getResources().getDrawable(R.drawable.mp_hydra), Rarete.SUPERIEUR, 16, 54, "MP9 Hydra");
//            m4a1 = new Arme(getResources().getDrawable(R.drawable.m_cyrex), Rarete.SUPERIEUR, 22, 78, "M4A1-S Cyrex");
//            ak47 = new Arme(getResources().getDrawable(R.drawable.ak_panthera), Rarete.EXOTIQUE, 37, 156, "AK-47 Panthera");
//            awp = new Arme(getResources().getDrawable(R.drawable.awp_worm_god), Rarete.BASE, 44, 154, "AWP Worm god");
//            knife = new Arme(getResources().getDrawable(R.drawable.knife_boreal_forest), Rarete.SECRET, 80, 234, "Classic Boreal forest");
////            binding.ivCase.setImageDrawable(getResources().getDrawable(R.drawable.box_dreams_nightmares));
//        } else if(caseID== 2){
//            glock = new Arme(getResources().getDrawable(R.drawable.glock_water_element), Rarete.EXOTIQUE, 4, 25, "Glock-17 Water element");
//            usp = new Arme(getResources().getDrawable(R.drawable.usp_traitor), Rarete.CLASSIFIE, 18, 67, "USP-S The traitor");
//            mp9 = new Arme(getResources().getDrawable(R.drawable.mp_dry_season), Rarete.BASE, 12, 40, "MP9 Dry season");
//            m4a1 = new Arme(getResources().getDrawable(R.drawable.m_hyperbeast), Rarete.CLASSIFIE, 32, 144, "M4A1-S Hyperbeast");
//            ak47 = new Arme(getResources().getDrawable(R.drawable.ak_point_disarray), Rarete.SUPERIEUR, 33, 128, "AK-47 Dissaray point");
//            awp = new Arme(getResources().getDrawable(R.drawable.awp_neo_noir), Rarete.EXOTIQUE, 60, 200, "AWP Black-Neo");
//            knife = new Arme(getResources().getDrawable(R.drawable.knife_fade), Rarete.SECRET, 108, 322, "Bayonet Fade");
////            binding.ivCase.setImageDrawable(getResources().getDrawable(R.drawable.box_bravo_operation));
//        } else if(caseID== 3){
//            glock = new Arme(getResources().getDrawable(R.drawable.glock_bullet_queen), Rarete.CLASSIFIE, 15, 75, "Glock-17 Bullet queen");
//            usp = new Arme(getResources().getDrawable(R.drawable.usp_orion), Rarete.EXOTIQUE, 12, 37, "USP-S Orion");
//            mp9 = new Arme(getResources().getDrawable(R.drawable.mp_mount_fuji), Rarete.EXOTIQUE, 25, 95, "MP9 Mount Fudji");
//            m4a1 = new Arme(getResources().getDrawable(R.drawable.m_nitro), Rarete.BASE, 17, 60, "M4A1-S Nitro");
//            ak47 = new Arme(getResources().getDrawable(R.drawable.ak_bloodsport), Rarete.CLASSIFIE, 42, 185, "AK-47 Bloodsport");
//            awp = new Arme(getResources().getDrawable(R.drawable.awp_hyperbeast), Rarete.CLASSIFIE, 69, 232, "AWP Hyperbeast");
//            knife = new Arme(getResources().getDrawable(R.drawable.knife_lore), Rarete.SECRET, 122, 366, "Karambit Lore");
////            binding.ivCase.setImageDrawable(getResources().getDrawable(R.drawable.box_cobblestone_memory));
//        }
//
//        //CREATE THE CASE WITH ALL THE WEAPON AVAILABLE FOR DISPLAY
//        List<Arme> weaponAvailableList = new ArrayList<>();
//        weaponAvailableList.add(glock);
//        weaponAvailableList.add(usp);
//        weaponAvailableList.add(mp9);
//        weaponAvailableList.add(m4a1);
//        weaponAvailableList.add(ak47);
//        weaponAvailableList.add(awp);
//        weaponAvailableList.add(knife);
//        Collections.sort(weaponAvailableList, new TriRarity());
//        int compteur = 0;
//        LinearLayout.LayoutParams layoutParams = new TableRow.LayoutParams(270, 270);
//        for(Arme weapon : weaponAvailableList){
//
//            ImageView imageView = new ImageView(getApplicationContext());
//            imageView.setImageDrawable(weapon.getImage_arme());
//            Rarete weaponRarity = weapon.getRarete();
//            if (weaponRarity == Rarete.BASE){
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_base));
//            } else if (weaponRarity == Rarete.SUPERIEUR) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_superieur));
//            } else if (weaponRarity == Rarete.EXOTIQUE) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_exotique));
//            } else if (weaponRarity == Rarete.CLASSIFIE) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_classifie));
//            } else if (weaponRarity == Rarete.SECRET) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_secret));
//            }
//
//            imageView.setLayoutParams(layoutParams);
//            if(compteur<4){
//                binding.tr1.addView(imageView);
//            } else {
//                binding.tr2.addView(imageView);
//            }
//            compteur++;
//        }
//
//
//
//        //CREATE THE CASE WITH 15 RANDOM WEAPONS
//        List<Arme> weaponList = new ArrayList<>();
//        for (int i=0; i<15; i++){
//            Random random = new Random();
//            int randomNumber = random.nextInt(100);
//
//            if (randomNumber<13){
//                weaponList.add(glock);
//            } else if (randomNumber<35) {
//                weaponList.add(usp);
//            } else if (randomNumber<70) {
//                weaponList.add(mp9);
//            } else if (randomNumber<80) {
//                weaponList.add(m4a1);
//            } else if (randomNumber<90) {
//                weaponList.add(ak47);
//            } else if (randomNumber<98) {
//                weaponList.add(awp);
//            } else if (randomNumber<100) {
//                weaponList.add(knife);
//            }
//        }
//
//        Case weaponCase = new Case(weaponList, 20, Rarete.BASE, weaponList.get(10));
//        for(Arme weapon : weaponCase.getArmeDispo()){
//            ImageView imageView = new ImageView(getApplicationContext());
////            imageView.setImageDrawable(weapon.getImage_arme());
//            Rarete weaponRarity = weapon.getRarete();
//            if (weaponRarity == Rarete.BASE){
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_base));
//            } else if (weaponRarity == Rarete.SUPERIEUR) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_superieur));
//            } else if (weaponRarity == Rarete.EXOTIQUE) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_exotique));
//            } else if (weaponRarity == Rarete.CLASSIFIE) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_classifie));
//            } else if (weaponRarity == Rarete.SECRET) {
//                imageView.setBackground(getResources().getDrawable(R.drawable.arrondir_secret));
//            }
//            binding.linear.addView(imageView);
//        }
//
//
    }


    @Override
    public void onResume() {
            super.onResume();
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            this.caseInformation = (CaseInformation) bundle.getSerializable("CASE_INFO");
            this.gameInformation = this.caseInformation.getGameInformation();
            this.playerInformation = this.gameInformation.getPlayerInformation();
            //CANCEL BUTTON SIMULATE A CLICK ON THE STORE
            binding.btnCancel.setOnClickListener(v -> {
                binding.bottomNavigationView.getMenu().performIdentifierAction(R.id.store, 0);
            });
            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.clicker) {
                    startActivity(new Intent(CaseOpening.this, Clicker.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.inventory) {
                    startActivity(new Intent(CaseOpening.this, Inventory.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (id == R.id.store) {
                    startActivity(new Intent(CaseOpening.this, Store.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
//
//
//
//
//        //STARTING THE ROLL OF WEAPON
//        binding.btnOpenning.setOnClickListener(v -> {
//            if (score >= price) {
//                binding.btnOpenning.setClickable(false);
//
//                binding.linearCaseOpen.setVisibility(View.INVISIBLE);
//                binding.rectangleView.setVisibility(View.VISIBLE);
//                binding.linearOpenOpen.setVisibility(View.VISIBLE);
//
//                score = score - price;
//
//                editor.putInt("SCORE", score);
//                editor.apply();
//                binding.tvScore.setText("" + score);
//
//                //SOUND PART
//                if (!mediaPlayer.isPlaying()) {
//                    mediaPlayer.start();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mediaPlayer.isPlaying()) {
//                                mediaPlayer.stop();
//                                mediaPlayer.prepareAsync();
//                                mediaPlayer.seekTo(0);
//                            }
//                        }
//                    }, 3500); //STOP AFTER 3500MS
//                }
//
//
//                scrollPos = 0;
//                scrollSpeed = 30;
//                Random randomTimer = new Random();
//                double randomNumber = randomTimer.nextInt(5);
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                scrollPos += scrollSpeed; // Increment the scroll position
//                                if (scrollSpeed > 5) {
//                                    scrollSpeed -= 0.1;
//                                } else {
//                                    scrollSpeed -= (randomNumber+11)/1000;//entre:0.011 et 0.015
//                                }
//                                binding.linearOpenOpen.scrollTo((int) scrollPos, 0); // Scroll to the new position
//                                if (scrollSpeed < 0.5) {
//                                    scrollSpeed = 0;
//                                    timer.cancel();
//                                }
//                            }
//                        });
//                    }
//                }, 0, 5);//ROLL EVERY 5MS
//            }
//        });
//    }
//    //DESTROY MEDIAPLAYER
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }

}