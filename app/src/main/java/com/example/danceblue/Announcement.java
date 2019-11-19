package com.example.danceblue;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//This class defines the Announcement object to be used and displayed on the home page.
// Each item on that page is an Announcement type object
public class Announcement implements Comparable<Announcement> {
    //data members
    private boolean isValid;
    private String id, imageURL, text;
    private Date date;
    private static final String TAG = "Announcement.java";

    //constructor
    public Announcement(DataSnapshot dataSnapshot) {
        isValid = true; //assume valid info passed until proved otherwise
        //read DB data, convert to strings after checking for nulls
        Object tempId = dataSnapshot.child("id").getValue();
        Object tempImage = dataSnapshot.child("image").getValue();
        Object tempText = dataSnapshot.child("text").getValue();
        Object tempTimestamp = dataSnapshot.child("timestamp").getValue();

        //convert to strings, checking for null DB children
        id = (tempId != null) ? tempId.toString() : "";
        imageURL = (tempImage != null) ? tempImage.toString() : "";
        text = (tempText != null) ? tempText.toString() : "";
        String timestamp = (tempTimestamp != null) ? tempTimestamp.toString() : "";

        //check validity of each string created above before moving on
        if (id.equals("") || imageURL.equals("") || text.equals("") || timestamp.equals("")) {
            isValid = false;
            return;
        }

        //convert timestamp to a date so they can be sorted
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try { //catch date string parsing errors
            date = formatter.parse(timestamp);
        } catch (ParseException e) { //print error to log, mark this object as invalid
            Log.e("onViewCreated", "countdownEnd date couldn't be parsed");
            Log.e("onViewCreated", e.getMessage());
            isValid = false;
        }

        Log.d(TAG, "Announcement made with: "+isValid()+" "+getId()+" "+getText());
    }

    //methods
    public boolean isValid() {
        return isValid;
    }
    public String getId() {
        return id;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getText() {
        return text;
    }

    @Override //allows Collections.sort() to sort these objects in descending order by timestamp
    public int compareTo(Announcement o) {
        return this.date.compareTo(o.date);
    }
}
