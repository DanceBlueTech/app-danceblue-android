package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

//This class handles opening the contact Webview. Layout can be found in activity_view_contact.xml
public class viewContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        //Finds webview object and loads the following url
        WebView contact = findViewById(R.id.contactViewer);
        contact.loadUrl("http://www.danceblue.org/meet-the-team/");
    }
}
