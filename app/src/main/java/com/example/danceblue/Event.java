package com.example.danceblue;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//This class defines the Event object to be used and displayed on the events page.
// Each item on that page is an Event type object
public class Event implements Comparable<Event> {
    //data members
    private boolean isValid;
    private String address, description, id, imageURL, mapURL, time, title, formattedDate;
    private Date startDate, endDate;
    private static final String TAG = "Event.java";

    //constructor
    public Event(DataSnapshot dataSnapshot) {
        isValid = true; //assume the created object is valid until proven otherwise

        //read DB data, convert to strings after checking for nulls
        Object tempAddr = dataSnapshot.child("address").getValue();
        Object tempDescr = dataSnapshot.child("description").getValue();
        Object tempEndTime = dataSnapshot.child("endTime").getValue();
        Object tempId = dataSnapshot.child("id").getValue();
        Object tempImg = dataSnapshot.child("image").getValue();
        Object tempMap = dataSnapshot.child("map").getValue();
        Object tempTime = dataSnapshot.child("time").getValue();
        Object tempTimestamp = dataSnapshot.child("timestamp").getValue();
        Object tempTitle = dataSnapshot.child("title").getValue();

        //convert to strings, checking for null DB children
        address = (tempAddr != null) ? tempAddr.toString() : "";
        description = (tempDescr != null) ? tempDescr.toString() : "";
        title = (tempTitle != null) ? tempTitle.toString() : "";
        time = (tempTime != null) ? tempTime.toString() : "";
        id = (tempId != null) ? tempId.toString() : "";
        imageURL = (tempImg != null) ? tempImg.toString() : "";
        mapURL = (tempMap != null) ? tempMap.toString() : "";
        String startTimeString = (tempTimestamp != null) ? tempTimestamp.toString() : "";
        String endTimeString = (tempEndTime != null) ? tempEndTime.toString() : "";


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

        SimpleDateFormat displayFormatter = new SimpleDateFormat("EEEE', 'MMMM' 'd', 'yyyy", Locale.US);
        formattedDate = displayFormatter.format(startDate)+" · "+time;
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

    public String getFormattedDate() {
        return formattedDate;
    }
}
