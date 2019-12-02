package com.example.danceblue;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

//This class defines the BlogItem object to be used and displayed on the blog page.
public class BlogItem implements Comparable<BlogItem>{
    private boolean isValid;
    private String id, imageURL, author, title;
    private String date;
    private static final String TAG = "BlogItem.java";

    public BlogItem (DataSnapshot dataSnapshot){
        isValid = true; //assume valid info passed until proved otherwise

        //read id
        Object tempId = dataSnapshot.child("id").getValue();

        //read details info
        DataSnapshot detailsSnapshot = dataSnapshot.child("details");
        Object tempAuthor = detailsSnapshot.child("author").getValue();
        Object tempTitle = detailsSnapshot.child("title").getValue();
        Object tempTimeStamp = detailsSnapshot.child("timestamp").getValue();
        Object tempImage = detailsSnapshot.child("image").getValue();

        //TODO Finish this section by adding ways to get paragraphs.
        //TODO Add conversions to strings and validity checks after finishing.
        //read article info
        DataSnapshot chunksSnapshot = dataSnapshot.child("chunks");

        //convert to strings, checking for null DB children
        id = (tempId != null) ? tempId.toString() : "";
        author = (tempAuthor != null) ? tempAuthor.toString() : "";
        title = (tempTitle != null) ? tempTitle.toString() : "";
        imageURL = (tempImage != null) ? tempImage.toString() : "";
        String timestamp = (tempTimeStamp != null) ? tempTimeStamp.toString() : "";

        //check validity of each string created above before moving on
        if (id.equals("") || author.equals("") || title.equals("") || timestamp.equals("")) {
            isValid = false;
            return;
        }

        //Defines the format of the date timestamp, and casts timestamp to this format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        date = formatter.format(timestamp);

        Log.d(TAG, "Announcement made with: "+isValid()+" "+getId()+" "+getAuthor()+" "+getTitle());
    }

    //methods
    public boolean isValid() {
        return isValid;
    }
    public String getId() {
        return id;
    }
    public String getAuthor() {
        return author;
    }
    public String getTitle() {
        return title;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getDate() { return date;}

    @Override //allows Collections.sort() to sort these objects in descending order by timestamp
    public int compareTo(BlogItem o) {
        return this.date.compareTo(o.date);
    }
}
