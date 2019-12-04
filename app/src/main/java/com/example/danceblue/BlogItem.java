package com.example.danceblue;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//This class defines the BlogItem object to be used and displayed on the blog page.
public class BlogItem implements Comparable<BlogItem>{
    private boolean isValid;
    private String id, imageURL, author, title, formattedDate;
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

        //convert timestamp to a date so they can be sorted
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try { //catch date string parsing errors
            date = formatter.parse(timestamp);
        } catch (ParseException e) { //print error to log, mark this object as invalid
            Log.e("onViewCreated", "countdownEnd date couldn't be parsed");
            Log.e("onViewCreated", e.getMessage());
            isValid = false;
        }

        SimpleDateFormat displayFormatter = new SimpleDateFormat("EEEE', 'MMMM' 'd', 'yyyy", Locale.US);
        formattedDate = displayFormatter.format(date);

        Log.d(TAG, "Blog made with: "+isValid()+" "+getId()+" "+getAuthor()+" "+getTitle());
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
    public Date getDate() { return date; }
    public String getFormattedDate() { return formattedDate; }

    @Override //allows Collections.sort() to sort these objects in descending order by date
    public int compareTo(BlogItem o) {
        return o.date.compareTo(this.date);
    }
}
