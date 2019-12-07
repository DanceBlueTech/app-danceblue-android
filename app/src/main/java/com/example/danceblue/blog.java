package com.example.danceblue;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

//This class defines the blog fragment, and the operations that it entails. The layout for the fragment
// can be found in blog.xml
public class blog extends Fragment {
    //data members
    private DatabaseReference databaseReference;
    private LinearLayout entriesLL;
    private ArrayList<BlogItem> blogEntries;
    private View view;
    private Context context;
    private final String TAG = "blog.java";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(R.layout.blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup
        databaseReference = FirebaseDatabase.getInstance().getReference();
        entriesLL = view.findViewById(R.id.entriesLL);
        //initialize with size 0 for the size dependent log in remakeEntriesView()
        //no-args constructor starts w/ size 10, was leading to NPEs
        blogEntries = new ArrayList<>(0);
        this.view = view;

        //Start of the blog code
        //Listen to the 'blog' child.
        databaseReference.child("blog").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BlogItem blog = new BlogItem(dataSnapshot); //make a blog from the added child
                blogEntries.add(blog);
                if (blogEntries.size() >= dataSnapshot.getChildrenCount()) {
                    //only remake the views if the ArrayList is fully populated with all the
                    //children from the DataSnapshot. helps with initial performance when the
                    //blog tab is first opened
                    remakeEntriesView();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BlogItem blog = new BlogItem(dataSnapshot); //make a blog from the added child
                for (BlogItem blog1 : blogEntries){
                    if (blog.getId().equals(blog1.getId())) { //if the new id matches
                        blogEntries.remove(blog1); //remove the old blog w/ same id
                    }
                }
                blogEntries.add(blog); //add it to the data arraylist
                if (blogEntries.size() >= dataSnapshot.getChildrenCount()) {
                    remakeEntriesView();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                BlogItem blog = new BlogItem(dataSnapshot); //make a blog from the added child
                for (BlogItem blog1 : blogEntries){
                    if (blog.getId().equals(blog1.getId())) { //if the new id matches
                        blogEntries.remove(blog1); //remove the old blog w/ same id
                    }
                }
                if (blogEntries.size() >= dataSnapshot.getChildrenCount()) {
                    remakeEntriesView();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //intentionally left blank
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //intentionally left blank
            }
        });
    }

    //todo convert this into an async task to prevent over-loading of the main ui thread
    //todo https://developer.android.com/reference/android/os/AsyncTask
    //todo https://www.journaldev.com/9708/android-asynctask-example-tutorial
    //private static class AsyncRedrawBlogEntries extends AsyncTask<BlogItem, null, LinearLayout>

    //remakes and draws the constituent views of the recent section
    //same as remakeFeaturedView(), but reads from the recent data arraylist and adds views to the
    //Recent visual section
    private void remakeEntriesView() {
        Collections.sort(blogEntries);
        entriesLL.removeAllViews();
        boolean firstEntry = true;
        for (final BlogItem blog1 : blogEntries) {
            if (blog1.isValid()) { //only generate views from valid BlogItems
                //Grab and load image
                ImageView imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);
                Picasso.get().load(blog1.getImageURL()).into(imageView);
                //Grab and load title
                TextView textViewTitle = new TextView(context);
                textViewTitle.setText(blog1.getTitle());
                //Grab and load author
                TextView textViewAuthor = new TextView(context);
                textViewAuthor.setText(blog1.getAuthor());
                //Grab and load date
                TextView textViewDate = new TextView(context);
                textViewDate.setText(blog1.getFormattedDate());

                //Generate the new linearlayout to add above information to.
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                if (firstEntry) {
                    TextView featuredTitleView = new TextView(context);
                    featuredTitleView.setTypeface(Typeface.DEFAULT_BOLD);
                    featuredTitleView.setText(R.string.featured);
                    linearLayout.addView(featuredTitleView);
                }

                linearLayout.addView(imageView);
                linearLayout.addView(textViewTitle);
                linearLayout.addView(textViewAuthor);
                linearLayout.addView(textViewDate);

                if (firstEntry) {
                    TextView recentTitleView = new TextView(context);
                    recentTitleView.setTypeface(Typeface.DEFAULT_BOLD);
                    recentTitleView.setText(R.string.recent);
                    linearLayout.addView(recentTitleView);
                    firstEntry = false;
                }

                //Create a listener that loads the event detail on click.
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openBlogDetails(blog1);
                    }
                });

                //Add to comingUpLL, then redraw the view.
                entriesLL.addView(linearLayout);
                view.invalidate();
            }
        }
    }

    //TODO finish this once blogItem is finished
    //called in the onClick listeners of anonymous LinearLayouts made in the remake functions above
    //extracts relevant info from the blog object into a bundle to be used in details fragment
    private void openBlogDetails(BlogItem blog) {}
}
