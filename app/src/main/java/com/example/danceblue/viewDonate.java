package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

//This class handles opening the donate Webview. Layout can be found in activity_view_donate.xml
public class viewDonate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donate);

        //Finds webview object and loads the following url
        WebView contact = findViewById(R.id.donateViewer);
        contact.loadUrl("https://danceblue.networkforgood.com");
    }
}
