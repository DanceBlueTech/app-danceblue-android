package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

//This class displays a webview, which opens a webpage inside the app.
// The website to be displayed is determined by which variable is passed from more.java.
public class WebViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viewer);

        //Link to webviewer in activity_web_viewer.xml
        WebView webpage = findViewById(R.id.webViewer);

        //Unpack variables, test to see if they are null, then open link and display it on webpage.
        Bundle b = getIntent().getExtras();
        if(b!= null && b.getString("link") != null){
            webpage.loadUrl(b.getString("link"));
        }
    }
}
