package com.example.danceblue;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

//This class defines the home fragment, and the operations that it entails. The layout for the fragment
// can be found in home.xml
public class home extends Fragment {
    private SimpleDateFormat formatter; //declared as data members so inner classes can access
    private Date countdownEnd, countdownStart;
    private TextView countdownTitle, dayText, dayLabel, hourText, hourLabel, minText, minLabel,
        secText, secLabel;
    private CountDownTimer timer;
    //TODO dynamic background img will have to be declared up here too
    private LinearLayout announcementsLL;
    private ArrayList<Announcement> announcementsAL;
    private static final String TAG = "home.java";
    private View view;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        //If image is clicked on, load activity to view image in fullscreen
        ImageButton layoutButton = Objects.requireNonNull(getView()).findViewById(R.id.DB_layout);
        layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), viewLayout.class);
                startActivity(intent);
            }
        });

        databaseRef = FirebaseDatabase.getInstance().getReference();

        //begin announcements code
        //layout to hold variable amount of announcements
        announcementsLL = view.findViewById(R.id.announceLL);
        //arraylist to hold announcement objects that are in the layout above.
        //allows the announcements to be easily sorted and added/re/moved
        announcementsAL = new ArrayList<>();
        //listens for any changes within announcements node
        databaseRef.child("announcements").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                //make a representative announcement object from the DB info
                Announcement announcement = new Announcement(dataSnapshot);
                if (announcement.isValid()) { //if the new child had no null fields
                    announcementsAL.add(announcement); //add to data to be drawn
                    Collections.sort(announcementsAL); //sort the data for the layout by timestamp
                    announcementsLL.removeAllViews(); //clear the layout
                    for (Announcement announcement1 : announcementsAL) { //redraw layout w/ new order
                        //todo make imageView from announcement1.getImageURL(), add to horizontal sub layout
                        TextView textView = new TextView(getActivity());
                        textView.setText(announcement1.getText());
                        LinearLayout horizontalLL = new LinearLayout(getActivity()); //horizontal by default
                        horizontalLL.addView(textView);
                        announcementsLL.addView(horizontalLL);//add the new img/text to announcements view
                        view.invalidate(); //schedule a redraw
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                //make a representative announcement object from the DB info
                Announcement announcement = new Announcement(dataSnapshot);
                if (announcement.isValid()) { //if the new child had no null fields
                    //find the old announcement(s) with the same ID as the new announcement
                    for (Announcement announcement1 : announcementsAL) {
                        if (announcement.getId().equals(announcement1.getId())) {
                            announcementsAL.remove(announcement1);
                        }
                    }
                    announcementsAL.add(announcement); //add to data to be drawn
                    Collections.sort(announcementsAL); //sort the data for the layout by timestamp
                    announcementsLL.removeAllViews(); //clear the layout
                    for (Announcement announcement1 : announcementsAL) { //redraw layout w/ new order
                        //todo make imageView from announcement1.getImageURL(), add to horizontal sub layout
                        TextView textView = new TextView(getActivity());
                        textView.setText(announcement1.getText());
                        LinearLayout horizontalLL = new LinearLayout(getActivity()); //horizontal by default
                        horizontalLL.addView(textView);
                        announcementsLL.addView(horizontalLL);//add the new img/text to announcements view
                        view.invalidate(); //schedule a redraw
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved() called with: dataSnapshot = [" + dataSnapshot + "]");
                //make a representative announcement object from the DB info
                Announcement announcement = new Announcement(dataSnapshot);
                if (announcement.isValid()) { //if the new child had no null fields
                    //find the old announcement(s) with the same ID as the new announcement
                    for (Announcement announcement1 : announcementsAL) {
                        if (announcement.getId().equals(announcement1.getId())) {
                            announcementsAL.remove(announcement1);
                        }
                    }
                    Collections.sort(announcementsAL); //sort the data for the layout by timestamp
                    announcementsLL.removeAllViews(); //clear the layout
                    for (Announcement announcement1 : announcementsAL) { //redraw layout w/ new order
                        //todo make imageView from announcement1.getImageURL(), add to horizontal sub layout
                        TextView textView = new TextView(getActivity());
                        textView.setText(announcement1.getText());
                        LinearLayout horizontalLL = new LinearLayout(getActivity()); //horizontal by default
                        horizontalLL.addView(textView);
                        announcementsLL.addView(horizontalLL);//add the new img/text to announcements view
                        view.invalidate(); //schedule a redraw
                    }
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

    @Override
    public void onResume() {
        super.onResume();
        //begin countdown timer code
        countdownTitle = view.findViewById(R.id.countdownTitle);
        dayText = view.findViewById(R.id.dayText);
        dayLabel = view.findViewById(R.id.dayLabel);
        hourText = view.findViewById(R.id.hourText);
        hourLabel = view.findViewById(R.id.hourLabel);
        minText = view.findViewById(R.id.minText);
        minLabel = view.findViewById(R.id.minLabel);
        secText = view.findViewById(R.id.secText);
        secLabel = view.findViewById(R.id.secLabel);
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        countdownStart = new Date();
        //add an anonymous listener implementation to the children of "countdown" node
        databaseRef.child("countdown").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                makeCountdownTimer(dataSnapshot); //whenever a new event is added in the database
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                makeCountdownTimer(dataSnapshot); //whenever an event is changed, reordered, etc.
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //intentionally blank
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //intentionally blank
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //intentionally blank
            }

            //gets database date/time for next event, cancels the last timer, makes a new one
            private void makeCountdownTimer(@NonNull DataSnapshot dataSnapshot) {
                //get the db info, make sure it's not null before string conversion
                Object tempDate = dataSnapshot.child("date").getValue();
                Object tempImg = dataSnapshot.child("image").getValue();
                Object tempTitle = dataSnapshot.child("title").getValue();
                String dateStr = (tempDate != null) ? tempDate.toString() : "";
                String imgStr = (tempImg != null) ? tempImg.toString() : "";
                String titleStr = (tempTitle != null) ? tempTitle.toString() : "No event found";

                //set the event title and image(NYI)
                countdownTitle.setText(titleStr);
                //TODO dynamically create the imageview behind the countdown from this snapshot,
                //TODO overlaps with Kendall's thing; we'll talk
                try { //catch date string parsing errors
                    countdownEnd = formatter.parse(dateStr);
                } catch (ParseException e) { //print error to log
                    countdownEnd = new Date();
                    Log.e("onViewCreated", "countdownEnd date couldn't be parsed");
                    Log.e("onViewCreated", e.getMessage());
                }

                long timeDiff = countdownEnd.getTime() - countdownStart.getTime();

                if (timer != null) timer.cancel(); //to be super safe wrt memory leaks
                //start a timer length = milliseconds till next event, ticks every second,
                //custom anonymous implementation
                timer = new CountDownTimer(timeDiff, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) { //runs every tick
                        //convert and round directly from milliseconds
                        int days = (int) TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                        //remaining milliseconds - days
                        long hours = (int) TimeUnit.MILLISECONDS.toHours(millisUntilFinished -
                                TimeUnit.DAYS.toMillis(days));
                        //remaining milliseconds - days - hours
                        long mins = (int) TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished -
                                TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours));
                        //remaining milliseconds - days - hours - minutes
                        long secs = (int) TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished -
                                TimeUnit.DAYS.toMillis(days) - TimeUnit.HOURS.toMillis(hours) -
                                TimeUnit.MINUTES.toMillis(mins));
                        //set textViews with newest times, test for singular/plural label
                        dayText.setText(String.valueOf(days));
                        dayLabel.setText((days == 1) ? "Day" : "Days");
                        hourText.setText(String.valueOf(hours));
                        hourLabel.setText((hours == 1) ? "Hour" : "Hours");
                        minText.setText(String.valueOf(mins));
                        minLabel.setText((mins == 1) ? "Minute" : "Minutes");
                        secText.setText(String.valueOf(secs));
                        secLabel.setText((secs == 1) ? "Second" : "Seconds");
                    }

                    @Override
                    public void onFinish() {
                        this.start(); //timer always runs
                    }
                }.start(); //start the timer once created
                view.invalidate(); //schedule a redraw
            }
        });
    }

    @Override
    public void onPause() {
        if (timer != null) timer.cancel(); //to be super safe wrt memory leaks
        super.onPause();
    }
}
