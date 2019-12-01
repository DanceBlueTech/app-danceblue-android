package com.example.danceblue;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

//This class defines the events fragment, and the operations that it entails. The layout for the fragment
// can be found in events.xml
public class events extends Fragment {
    //data members
    private DatabaseReference databaseReference;
    private LinearLayout thisWeekLL, comingUpLL;
    private ArrayList<Event> thisWeekAL, comingUpAL;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setup
        databaseReference = FirebaseDatabase.getInstance().getReference();
        thisWeekLL = view.findViewById(R.id.thisWeekLL);
        comingUpLL = view.findViewById(R.id.comingUpLL);
        thisWeekAL = new ArrayList<>();
        comingUpAL = new ArrayList<>();
        this.view = view;

        //listen to the thisWeek child, populate the THIS WEEK section of the page
        databaseReference.child("events").child("thisWeek").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event event = new Event(dataSnapshot); //make an event from the added child
                if (event.isValid()) { //if a valid event object was made
                    thisWeekAL.add(event); //add it to the data arraylist
                    remakeThisWeekView(); //redraw the view with new data
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event event = new Event(dataSnapshot); //make an event object from the changed child
                if (event.isValid()) { //if a valid event object was made
                    for (Event event1 : thisWeekAL) { //check every event in the current data arraylist
                        if (event.getId().equals(event1.getId())) { //if the new id matches
                            thisWeekAL.remove(event1); //remove the old event w/ same id
                        }
                    }
                    thisWeekAL.add(event); //add it to the data arraylist
                    remakeThisWeekView(); //redraw the view with new data
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Event event = new Event(dataSnapshot); //make an event object from the removed child
                if (event.isValid()) { //if a valid event was made
                    for (Event event1 : thisWeekAL) { //check every event in the current data arraylist
                        if (event.getId().equals(event1.getId())) { //if the new id matches
                            thisWeekAL.remove(event1); //remove the old event w/ same id
                        }
                    }
                    remakeThisWeekView(); //redraw the view with new data
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

        //listen to the comingUp child, populate the COMING UP section of the page
        databaseReference.child("events").child("comingUp").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event event = new Event(dataSnapshot); //make an event of the added child
                if (event.isValid()) { //if a valid event object was made
                    comingUpAL.add(event); //add the new event to the data arraylist
                    remakeComingUpView(); //redraw the view with the new data
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Event event = new Event(dataSnapshot); //make an event of the changed child
                if (event.isValid()) { //if a valid event object was made
                    for (Event event1 : comingUpAL) { //check each event in the current data arraylist
                        if (event.getId().equals(event1.getId())) { //if the ids match
                            comingUpAL.remove(event1); //remove the old event w/ same id
                        }
                    }
                    comingUpAL.add(event); //add the new event to the data arraylist
                    remakeComingUpView(); //redraw the view with the new data
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Event event = new Event(dataSnapshot); //make an event of the removed child
                if (event.isValid()) { //if a valid event was made
                    for (Event event1 : comingUpAL) { //check each event in the current data arraylist
                        if (event.getId().equals(event1.getId())) { //if the ids match
                            comingUpAL.remove(event1); //remove the event w/ same id
                        }
                    }
                    remakeComingUpView(); //redraw the view with the new data
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //intentionally left blank
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //intentionally left blank
            }});
    }

    //remakes and draws the constituent views of the This Week section
    private void remakeThisWeekView() {
        Collections.sort(thisWeekAL); //sort the events by ascending start time, implemented in Event.java
        thisWeekLL.removeAllViews(); //clear the layout
        for (final Event event1 : thisWeekAL) { //do for each event in the data arraylist
            ImageView imageView = new ImageView(getActivity()); //make the image view
            Picasso.get().load(event1.getImageURL()).into(imageView);
            TextView textView = new TextView(getActivity()); //make the title view
            textView.setText(event1.getTitle());
            TextView textView1 = new TextView(getActivity()); //make the event date/time view
            textView1.setText(event1.getFormattedDate());
            LinearLayout linearLayout = new LinearLayout(getActivity()); //make a layout to hold above views
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(imageView); //add the constituent views
            linearLayout.addView(textView);
            linearLayout.addView(textView1);
            //make the view clickable to open details
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEventDetails(event1);
                }
            });
            thisWeekLL.addView(linearLayout); //add the layout to the screen
            view.invalidate(); //queue a redraw of the page with the new events
        }
    }

    //remakes and draws the constituent views of the Coming Up section
    //same as remakeThisWeek(), but reads from the Coming Up data arraylist and adds views to the
    //Coming Up visual section
    private void remakeComingUpView() {
        Collections.sort(comingUpAL);
        comingUpLL.removeAllViews();
        for (final Event event1 : comingUpAL) {
            ImageView imageView = new ImageView(getActivity());
            Picasso.get().load(event1.getImageURL()).into(imageView);
            TextView textView = new TextView(getActivity());
            textView.setText(event1.getTitle());
            TextView textView1 = new TextView(getActivity());
            textView1.setText(event1.getFormattedDate());
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(textView1);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEventDetails(event1);
                }
            });
            comingUpLL.addView(linearLayout);
            view.invalidate();
        }
    }

    //called in the onClick listeners of anonymous LinearLayouts made in the remake functions above
    //extracts relevant info from the event object into a bundle to be used in details fragment
    private void openEventDetails(Event event) {
        Bundle args = new Bundle(); //bundle the needed info
        ArrayList<String> stringsAL = new ArrayList<>();
        stringsAL.add(event.getImageURL());
        stringsAL.add(event.getTitle());
        stringsAL.add(event.getFormattedDate());
        stringsAL.add(event.getDescription());
        stringsAL.add(event.getMapURL());
        stringsAL.add(event.getAddress());
        stringsAL.add(event.getStartString());
        stringsAL.add(event.getEndString());
        stringsAL.add(event.getTime());
        args.putStringArrayList("stringsAL", stringsAL);

        EventDetails detailsFragment = new EventDetails(); //make the new fragment
        detailsFragment.setArguments(args); //attach the bundled info
        //replace the whole tabs fragment with the details fragment, adding to the backstack to
        //enable back-button functionality
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detailsFragment).addToBackStack(null).commit();
    }
}
