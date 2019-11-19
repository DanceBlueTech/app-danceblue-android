package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

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
