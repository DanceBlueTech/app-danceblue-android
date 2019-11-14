package com.example.danceblue;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event implements Comparable<Event> {
    //data members
    private boolean isValid;
    private String address, description, id, imageURL, mapURL, time, title;
    private Date startDate, endDate;
    private static final String TAG = "Event.java";

    //constructor
    public Event(DataSnapshot dataSnapshot) {
        isValid = true; //assume the created object is valid until proven otherwise

        //read DB data, null-safely convert to strings
        Object tempAddr = dataSnapshot.child("address").getValue();
        address = (tempAddr != null) ? tempAddr.toString() : "";
        Object tempDescr = dataSnapshot.child("description").getValue();
        description = (tempDescr != null) ? tempDescr.toString() : "";
        Object tempEndTime = dataSnapshot.child("endTime").getValue();
        String endTimeString = (tempEndTime != null) ? tempEndTime.toString() : "";
        Object tempId = dataSnapshot.child("id").getValue();
        id = (tempId != null) ? tempId.toString() : "";
        Object tempImg = dataSnapshot.child("image").getValue();
        imageURL = (tempImg != null) ? tempImg.toString() : "";
        Object tempMap = dataSnapshot.child("map").getValue();
        mapURL = (tempMap != null) ? tempMap.toString() : "";
        Object tempTime = dataSnapshot.child("time").getValue();
        time = (tempTime != null) ? tempTime.toString() : "";
        Object tempTimestamp = dataSnapshot.child("timestamp").getValue();
        String startTimeString = (tempTimestamp != null) ? tempTimestamp.toString() : "";
        Object tempTitle = dataSnapshot.child("title").getValue();
        title = (tempTitle != null) ? tempTitle.toString() : "";

        //check for invalid db info before trying to make Date objects
        if (address.equals("") || description.equals("") || endTimeString.equals("") ||
                id.equals("") || imageURL.equals("") || mapURL.equals("") || time.equals("") ||
                startTimeString.equals("") || title.equals("")) {
            isValid = false;
            return;
        }

        //make startDate and endDate Date objects
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            endDate = formatter.parse(endTimeString);
        } catch (ParseException e) {
            Log.e(TAG, "event endDate couldn't be parsed!");
            Log.e(TAG, e.getMessage());
            isValid = false;
        }
        try {
            startDate = formatter.parse(startTimeString);
        } catch (ParseException e) {
            Log.e(TAG, "event startDate couldn't be parsed!");
            Log.e(TAG, e.getMessage());
            isValid = false;
        }
    }

    //methods
    @Override //allows Collections.sort() to sort Event objects in ascending startDate order
    public int compareTo(Event o) {
       return o.startDate.compareTo(this.startDate);
    }

    public boolean isValid() {
        return isValid;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getMapURL() {
        return mapURL;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
