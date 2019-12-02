package com.example.danceblue;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

//This class defines the blog fragment, and the operations that it entails. The layout for the fragment
// can be found in blog.xml
public class blog extends Fragment {
    //data members
    private DatabaseReference databaseReference;
    private LinearLayout featuredLL, recentLL;
    private ArrayList<BlogItem> featuredAL, recentAL;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup
        databaseReference = FirebaseDatabase.getInstance().getReference();
        featuredLL = view.findViewById(R.id.featruedLL);
        recentLL = view.findViewById(R.id.recentLL);
        featuredAL = new ArrayList<>();
        recentAL = new ArrayList<>();
        this.view = view;

        databaseReference.child("blog").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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

    //remakes and draws the constituent views of the Featured section
    private void remakeFeaturedView() {
        Collections.sort(featuredAL);
        featuredLL.removeAllViews();
        for (final BlogItem blog1 : featuredAL) {
            //Grab and load image
            ImageView imageView = new ImageView(getActivity());
            Picasso.get().load(blog1.getImageURL()).into(imageView);
            //Grab and load title
            TextView textViewTitle = new TextView(getActivity());
            textViewTitle.setText(blog1.getTitle());
            //Grab and load author
            TextView textViewAuthor = new TextView(getActivity());
            textViewAuthor.setText(blog1.getAuthor());
            //Grab and load date
            TextView textViewDate = new TextView(getActivity());
            textViewDate.setText(blog1.getDate());

            //Generate the new linearlayout to add above information to.
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textViewTitle);
            linearLayout.addView(textViewAuthor);
            linearLayout.addView(textViewDate);

            //Create a listener that loads the event detail on click.
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBlogDetails(blog1);
                }
            });

            //Add to comingUpLL, then redraw the view.
            featuredLL.addView(linearLayout);
            view.invalidate();
        }
    }

    //remakes and draws the constituent views of the recent section
    //same as remakeFeaturedView(), but reads from the recent data arraylist and adds views to the
    //Recent visual section
    private void remakeRecentView() {
        Collections.sort(recentAL);
        recentLL.removeAllViews();
        for (final BlogItem blog1 : recentAL) {
            //Grab and load image
            ImageView imageView = new ImageView(getActivity());
            Picasso.get().load(blog1.getImageURL()).into(imageView);
            //Grab and load title
            TextView textViewTitle = new TextView(getActivity());
            textViewTitle.setText(blog1.getTitle());
            //Grab and load author
            TextView textViewAuthor = new TextView(getActivity());
            textViewAuthor.setText(blog1.getAuthor());
            //Grab and load date
            TextView textViewDate = new TextView(getActivity());
            textViewDate.setText(blog1.getDate());

            //Generate the new linearlayout to add above information to.
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textViewTitle);
            linearLayout.addView(textViewAuthor);
            linearLayout.addView(textViewDate);

            //Create a listener that loads the event detail on click.
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBlogDetails(blog1);
                }
            });

            //Add to comingUpLL, then redraw the view.
            recentLL.addView(linearLayout);
            view.invalidate();
        }
    }

    //TODO finish this once blogItem is finished
    //called in the onClick listeners of anonymous LinearLayouts made in the remake functions above
    //extracts relevant info from the blog object into a bundle to be used in details fragment
    private void openBlogDetails(BlogItem blog) {}
}
