package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

//This class defines the rave hour feature
public class raveHour extends AppCompatActivity {
    final Handler handler = new Handler(); //handler for thread
    List<Integer> colors = new ArrayList<>(); //list of the three colors, taken from arrays.xml
    private int count = 0; //index for traversing list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rave_hour);

        //Instantiates the color list, pulling from the colors array located in arrays.xml
        String[] colorsTxt = getApplicationContext().getResources().getStringArray(R.array.colors);
        for (String s : colorsTxt) {
            int newColor = Color.parseColor(s);
            colors.add(newColor);
        }
        runnable.run();
    }

    //When activity is left, end it to save resources
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

    //Runs until activity is left. Cycles between the three colors every 200 milliseconds,
    // setting them to background. Resets index to 0 when the end of the loop is reached.
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
