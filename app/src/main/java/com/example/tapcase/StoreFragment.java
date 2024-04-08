package com.example.tapcase;

        import android.os.Bundle;

        import androidx.fragment.app.Fragment;

        import android.os.Handler;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.tapcase.databinding.ActivityClickerBinding;
        import com.example.tapcase.databinding.FragmentClickerBinding;
        import com.example.tapcase.databinding.FragmentStoreBinding;

        import java.util.Timer;
        import java.util.TimerTask;

public class StoreFragment extends Fragment {
    private FragmentStoreBinding binding;
    private double scrollSpeed;
    private double scrollPos;
    public StoreFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        binding.btnStore.setOnClickListener(v -> {
            scrollPos=0;
            scrollSpeed=30;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollPos += scrollSpeed; // Increment the scroll position
                            if(scrollSpeed>5){
                                scrollSpeed -= 0.1;
                            }else{
                                scrollSpeed -= 0.01;
                            }

                            binding.tvStore.setText("speed=" + (int) scrollSpeed + " pos=" + (int) scrollPos);
                            binding.horizontal.scrollTo((int) scrollPos, 0); // Scroll to the new position
                            if (scrollSpeed <= 0) {
                                timer.cancel(); // Stop the timer when the activity is destroyed
                            }
                        }
                    });
                }
            }, 0, 5); // Exécute la tâche toutes les 1000 millisecondes (1 seconde)
        });
    }


    private Timer timer;
    private final Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStoreBinding.inflate(inflater, container, false);




    
    
        return binding.getRoot();
    }
}