package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class raveHour extends AppCompatActivity {
    final Handler handler = new Handler();
    List<Integer> colors = new ArrayList<>();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rave_hour);

        String[] colorsTxt = getApplicationContext().getResources().getStringArray(R.array.colors);
        for (String s : colorsTxt) {
            int newColor = Color.parseColor(s);
            colors.add(newColor);
        }
        runnable.run();
    }

    private Runnable runnable = new Runnable(){
        @Override
        public void run(){
            View background = findViewById(R.id.rave_back);
            background.setBackgroundColor(colors.get(count));
            count++;
            if (count == 3) {count = 0;}
            handler.postDelayed(runnable, 100);
        }
    };
}
