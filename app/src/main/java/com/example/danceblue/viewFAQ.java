package com.example.danceblue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

//This class handles opening the FAQ Webview. Layout can be found in activity_view_faq.xml
public class viewFAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faq);

        //Finds webview object and loads the following url
        WebView contact = findViewById(R.id.faqViewer);
        contact.loadUrl("http://www.danceblue.org/frequently-asked-questions/");
    }
}
