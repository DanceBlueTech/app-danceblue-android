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
            //Grab and load image
            ImageView imageView = new ImageView(getActivity());
            imageView.setAdjustViewBounds(true);
            Picasso.get().load(event1.getImageURL()).into(imageView);
            //Grab and load the title
            TextView textView = new TextView(getActivity());
            textView.setText(event1.getTitle());
            //Grab and load the date
            TextView textView1 = new TextView(getActivity());
            textView1.setText(event1.getFormattedDate());

            //Generate the new linearlayout to add above information to.
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(textView1);

            //make the view clickable to open details
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEventDetails(event1);
                }
            });

            //Add to thisWeekLL, then redraw the view.
            thisWeekLL.addView(linearLayout);
            view.invalidate();
        }
    }

    //remakes and draws the constituent views of the Coming Up section
    //same as remakeThisWeek(), but reads from the Coming Up data arraylist and adds views to the
    //Coming Up visual section
    private void remakeComingUpView() {
        Collections.sort(comingUpAL);
        comingUpLL.removeAllViews();
        for (final Event event1 : comingUpAL) {
            //Grab and load image
            ImageView imageView = new ImageView(getActivity());
            imageView.setAdjustViewBounds(true);
            Picasso.get().load(event1.getImageURL()).into(imageView);
            //Grab and load title
            TextView textView = new TextView(getActivity());
            textView.setText(event1.getTitle());
            //Grab and load date
            TextView textView1 = new TextView(getActivity());
            textView1.setText(event1.getFormattedDate());

            //Generate the new linearlayout to add above information to.
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(textView1);

            //Create a listener that loads the event detail on click.
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEventDetails(event1);
                }
            });

            //Add to comingUpLL, then redraw the view.
            comingUpLL.addView(linearLayout);
            view.invalidate();
        }
    }

    //called in the onClick listeners of anonymous LinearLayouts made in the remake functions above
    //extracts relevant info from the event object into a bundle to be used in details fragment
    private void openEventDetails(Event event) {
        Bundle args = new Bundle(); //bundle the needed info
        ArrayList<String> stringsAL = new ArrayList<>(0);
        stringsAL.add(event.getImageURL());
        stringsAL.add(event.getTitle());
        stringsAL.add(event.getFormattedDate());
        stringsAL.add(event.getDescription());
        stringsAL.add(event.getMapURL());
        stringsAL.add(event.getAddress());
        stringsAL.add(event.getStartMillis());
        stringsAL.add(event.getEndMillis());
        args.putStringArrayList("stringsAL", stringsAL);

        EventDetails detailsFragment = new EventDetails(); //make the new fragment
        detailsFragment.setArguments(args); //attach the bundled info
        //replace the whole tabs fragment with the details fragment, adding to the backstack to
        //enable back-button functionality, null value means no retrieval ID from backstack
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detailsFragment).addToBackStack(null).commit();
    }
}
