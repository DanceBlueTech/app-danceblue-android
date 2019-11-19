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

        //Instantiates the color list, pulling the colors array located in arrays.xml
        String[] colorsTxt = getApplicationContext().getResources().getStringArray(R.array.colors);
        for (String s : colorsTxt) {
            int newColor = Color.parseColor(s);
            colors.add(newColor);
        }
        runnable.run();
    }

    //Runs until activity is left. Cycles between the three colors every 200 milliseconds,
    // resetting index to 0 when the end of the loop is reached.
    private Runnable runnable = new Runnable(){
        @Override
        public void run(){
            View background = findViewById(R.id.rave_back);
            background.setBackgroundColor(colors.get(count));
            count++;
            if (count == 3) {count = 0;}
            handler.postDelayed(runnable, 200);
        }
    };
}
