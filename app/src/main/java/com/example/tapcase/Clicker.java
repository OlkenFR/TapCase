package com.example.tapcase;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapcase.databinding.ActivityClickerBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clicker extends AppCompatActivity {
    private ActivityClickerBinding binding;
    BottomNavigationView bottomNavigationView;
    private Integer flower_per_click;
    private Gson gson;
    private Integer score;
    private Integer prix;
    private Rarete rarete;
    private Integer intRarete;
    private Arme weapon_selected;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private String playerWeaponsList;
    private List<Arme> armeAvailable = new ArrayList<>();
    private List<Arme> armesPlayer = new ArrayList<>();
    private Arme default_weapon;
 	MediaPlayer mediaPlayer;
    private AutoClickService autoClicker;
    private Intent intentService;
    static public final String BROADCAST = "com.cfc.slides.event";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClickerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gson = new Gson();
        default_weapon = new Arme(Rarete.BASE, 1, 0, "Glock-17", "glock_classique");
        mediaPlayer = MediaPlayer.create(this, R.raw.bruit_balle_2);
        prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        editor = prefs.edit();
        weapon_selected = default_weapon;
        loadAvailableWeapons();
        loadPlayerWeapons();
        if (prefs.contains("SCORE") || prefs.contains("FLOWER_PER_CLICK") || prefs.contains("WEAPON_SELECTED") || prefs.contains("NOM") || prefs.contains("PRIX") || prefs.contains("RARETE")) {
            try {
                score = prefs.getInt("SCORE", 0);
                flower_per_click = prefs.getInt("FLOWER_PER_CLICK", 1);
                prix = prefs.getInt("PRIX", 2);
                intRarete = prefs.getInt("RARETE", 3);
                switch (intRarete){
                    case 0:
                        rarete = Rarete.BASE;
                        binding.ivBackgroundInfoWeapon.setBackground(getDrawable(R.drawable.arrondir_base));
                        break;
                    case 1:
                        rarete = Rarete.SUPERIEUR;
                        binding.ivBackgroundInfoWeapon.setBackground(getDrawable(R.drawable.arrondir_superieur));
                        break;
                    case 2:
                        rarete = Rarete.EXOTIQUE;
                        binding.ivBackgroundInfoWeapon.setBackground(getDrawable(R.drawable.arrondir_exotique));
                        break;
                    case 3:
                        rarete = Rarete.CLASSIFIE;
                        binding.ivBackgroundInfoWeapon.setBackground(getDrawable(R.drawable.arrondir_classifie));
                        break;
                    case 4:
                        rarete = Rarete.SECRET;
                        binding.ivBackgroundInfoWeapon.setBackground(getDrawable(R.drawable.arrondir_secret));
                        break;
                }
                weapon_selected.setFlower_per_click(flower_per_click);
                weapon_selected.setPrix(prix);
                weapon_selected.setFileName(prefs.getString("WEAPON_SELECTED", "0"));
                weapon_selected.setNom(prefs.getString("NOM", "1"));
                weapon_selected.setRarete(rarete);
            } catch (ClassCastException e) {
                try {
                    score = Integer.parseInt(prefs.getString("SCORE", "0"));
                    flower_per_click = Integer.parseInt(prefs.getString("FLOWER_PER_CLICK", "0"));
                    weapon_selected.setNom(prefs.getString("WEAPON_SELECTED", "0"));
                    editor.putInt("SCORE", score);
                    editor.putInt("FLOWER_PER_CLICK", flower_per_click);
                    editor.putString("WEAPON_SELECTED", weapon_selected.getFileName());
                    editor.apply();
                } catch (NumberFormatException ex) {
                    score = 0;
                    flower_per_click = 1;
                    weapon_selected.setFileName("glock_classique");
                    editor.putInt("SCORE", score);
                    editor.putInt("FLOWER_PER_CLICK", flower_per_click);
                    editor.putString("WEAPON_SELECTED", weapon_selected.getFileName());
                    editor.apply();
                }
            }
        } else {
            score = 0;
            flower_per_click = 1;
            weapon_selected.setFileName("glock_classique");
            editor.putInt("FLOWER_PER_CLICK", flower_per_click);
            editor.putInt("SCORE", score);
            editor.putString("WEAPON_SELECTED", weapon_selected.getFileName());
            editor.apply();
        }
        update();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.clicker);
        binding.tvNomArme.setText(weapon_selected.getNom());
        binding.tvPrixArme.setText("Prix : " + String.valueOf(weapon_selected.getPrix()));
        binding.tvPerfArme.setText("Performance : " + String.valueOf(weapon_selected.getFlower_per_click()) + " fleurs/tirs");


        intentService = new Intent(this, AutoClickService.class);
    }

    private void update() {
        score = prefs.getInt("SCORE", 0);
        flower_per_click = prefs.getInt("FLOWER_PER_CLICK", 1);
        weapon_selected.setFileName(prefs.getString("WEAPON_SELECTED", "glock_classique"));
        binding.tvScore.setText(String.valueOf(score));

        int resourceId = getResources().getIdentifier(weapon_selected.getFileName(), "drawable", getPackageName());
        if (resourceId != 0) {
            binding.ivArme.setImageResource(resourceId);
        } else {
            binding.ivArme.setImageResource(R.drawable.glock_bullet_queen);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PlayerInformation playerInformation = new PlayerInformation(score, weapon_selected, armesPlayer);
        GameInformation gameInformation = new GameInformation(armeAvailable, playerInformation);
        Intent clickerToStore = new Intent(Clicker.this, Store.class);
        Intent clickerToInventory = new Intent(Clicker.this, Inventory.class);
        Bundle bundleClickerToStore = new Bundle();
        Bundle bundleClickerToInventory = new Bundle();
        bundleClickerToStore.putSerializable("GAME_INFO", gameInformation);
        bundleClickerToInventory.putSerializable("GAME_INFO", gameInformation);
        clickerToInventory.putExtras(bundleClickerToInventory);
        clickerToStore.putExtras(bundleClickerToInventory);

        // registerReceiver(receiver,new IntentFilter(BROADCAST));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            registerReceiver(receiver,new IntentFilter(BROADCAST),RECEIVER_EXPORTED);
        }
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.clicker){
                return true;
            } else if (id == R.id.inventory){
                stopService(intentService);
                startActivity(clickerToInventory);
                overridePendingTransition(0,0);
                return true;
            } else if (id == R.id.store){
                stopService(intentService);
                startActivity(clickerToStore);
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });
        ViewGroup parent = (ViewGroup) binding.getRoot();
        parent.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                score = score + (1*flower_per_click);
                editor.putInt("SCORE", score);
                editor.apply();
                update();

                RelativeLayout.LayoutParams paramsLayout = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT
                );
                //SOUND PART FOR SHOOTING
                if (!mediaPlayer.isPlaying()) {
                    String fileName = playerInformation.getArme_selectionné().getFileName();
                    if (fileName.startsWith("glock")) {
                        play_sound(R.raw.glock_cut);
                    } else if (fileName.startsWith("usp")) {
                        play_sound(R.raw.usp_cut);
                    } else if (fileName.startsWith("mp")) {
                        play_sound(R.raw.mp9_cut);
                    } else if (fileName.startsWith("m_")) {
                        play_sound(R.raw.m4_cut);
                    } else if (fileName.startsWith("ak")) {
                        play_sound(R.raw.bruit_balle_2);
                    } else if (fileName.startsWith("awp")) {
                        play_sound(R.raw.awp_cut);
                    } else if (fileName.startsWith("knife")) {
                        play_sound(R.raw.sound_knife);
                    }
                }

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
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mediaPlayer.seekTo(0);
            }
            return true;
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            registerReceiver(receiver,new IntentFilter(BROADCAST),RECEIVER_EXPORTED);
        }

        //AUTOCLICKER ON WHEN THE BOX IS CHECKED
        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Intent intent = new Intent(Clicker.this, AutoClickService.class);
            if (isChecked) {
                startService(intentService);
            } else {
                stopService(intentService);
            }
        });
    }

    //UNREGISTER THE RECEIVER FOR THE AUTOCLICKER
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

         private void loadAvailableWeapons() {
        InputStream inputStream = getResources().openRawResource(R.raw.item_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] itemData = line.split(",");
                String nom = itemData[0];
                int prix = Integer.parseInt(itemData[1]);
                Rarete rarete = Rarete.valueOf(itemData[2]);
                int imageResId = getResources().getIdentifier(itemData[3], "drawable", getPackageName());
                int fleurs_par_click = Integer.parseInt(itemData[4]);
                this.armeAvailable.add(new Arme(rarete, fleurs_par_click,prix, nom, itemData[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadPlayerWeapons() {
        if (prefs.contains("NOM_ARMES_JOUEUR")) {
            String json = prefs.getString("NOM_ARMES_JOUEUR", null);
            System.out.println("JSON String: " + json);

            if (json != null) {
                Type type = new TypeToken<List<Arme>>(){}.getType();
                armesPlayer = gson.fromJson(json, type);
            }
        } else {
            armesPlayer.add(default_weapon);
            playerWeaponsList = gson.toJson(armesPlayer);
            System.out.println("Default Player Weapons List: " + playerWeaponsList);
            editor.putString("NOM_ARMES_JOUEUR", playerWeaponsList);
            editor.apply();
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
//                Log.println(Log.DEBUG, "AutoClickService", "CLICKING");

                ViewGroup parent = (ViewGroup) binding.getRoot();
                MotionEvent event = MotionEvent.obtain(
                        System.currentTimeMillis(),
                        System.currentTimeMillis() + 100, // Durée de l'événement (100 ms par exemple)
                        MotionEvent.ACTION_DOWN,
                        parent.getWidth() / 2, // Coordonnée X du centre du ViewGroup
                        parent.getHeight() / 2, // Coordonnée Y du centre du ViewGroup
                        0
                );

                parent.dispatchTouchEvent(event);

                event.setAction(MotionEvent.ACTION_UP);
                parent.dispatchTouchEvent(event);
                event.recycle();
            }
        }
    };
    private void play_sound(int id_sound) {
        MediaPlayer sound = MediaPlayer.create(this, id_sound);
        sound.start();
        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }
}


