package com.example.danceblue;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

//This class defines the Sponsor object to be used and displayed on the home page.
// Each item on that page is an Sponsor type object
public class Sponsor {
    //data members
    private boolean isValid;
    private String link, imageURL;
    private static final String TAG = "Sponsor.java";

    //constructor
    public Sponsor(DataSnapshot dataSnapshot){
        isValid = true; //assume valid info passed until proved otherwise

        //read DB data, convert to strings after checking for nulls
        Object tempLink = dataSnapshot.child("link").getValue();
        Object tempImage = dataSnapshot.child("image").getValue();

        //convert to strings, checking for null DB children
        link = (tempLink != null) ? tempLink.toString() : "";
        imageURL = (tempImage != null) ? tempImage.toString() : "";

        //check validity of each string created above before moving on
        if (link.equals("") || imageURL.equals("")) {
            isValid = false;
            return;
        }

        Log.d(TAG, "Sponsor made with: "+isValid()+" "+getLink()+" "+getImageURL());
    }

    //methods
    public boolean isValid() {
        return isValid;
    }
    public String getLink() {
        return link;
    }
    public String getImageURL() {
        return imageURL;
    }
}