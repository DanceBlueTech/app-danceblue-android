package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//This class handles displaying the db_floormap.png image.
// Layout can be found in activity_view_layout.xml
public class viewLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layout);
    }
}
