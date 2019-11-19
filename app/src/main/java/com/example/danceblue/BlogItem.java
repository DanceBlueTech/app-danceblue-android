package com.example.danceblue;

import android.provider.ContactsContract;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//This class defines the BlogItem object to be used and displayed on the blog page.
// Each item on that page is an BlogItem type object
public class BlogItem implements Comparable<BlogItem>{
    private boolean isValid;
    private String id, imageURL, author, title;
    private Date date;
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

        //TODO finish this section. Add conversions to strings and validity checks after finishing.
        //read article info
        DataSnapshot chunksSnapshot = dataSnapshot.child("chunks");

        //convert to strings, checking for null DB children
        id = (tempId != null) ? tempId.toString() : "";
        author = (tempAuthor != null) ? tempAuthor.toString() : "";
        title = (tempTitle != null) ? tempTitle.toString() : "";
        String timestamp = (tempTimeStamp != null) ? tempTimeStamp.toString() : "";

        //check validity of each string created above before moving on
        if (id.equals("") || author.equals("") || title.equals("") || timestamp.equals("")) {
            isValid = false;
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try { //catch date string parsing errors
            date = formatter.parse(timestamp);
        } catch (ParseException e) { //print error to log, mark this object as invalid
            Log.e("onViewCreated", "countdownEnd date couldn't be parsed");
            Log.e("onViewCreated", e.getMessage());
            isValid = false;
        }

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

    @Override //allows Collections.sort() to sort these objects in descending order by timestamp
    public int compareTo(BlogItem o) {
        return this.date.compareTo(o.date);
    }
}
