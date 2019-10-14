package com.example.danceblue;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class home extends Fragment {
    private SimpleDateFormat formatter; //declared as data members so inner classes can access
    private Date countdownEnd;
    private TextView countdownTitle, dayText, dayLabel, hourText, hourLabel, minText, minLabel,
        secText, secLabel;
    //TODO dynamic background img will have to be declared up here too
    private CountDownTimer timer; //declared here so both onViewCreated and onPause can access

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

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
        formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date countdownStart = new Date();
        databaseRef.child("countdown").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Object tempDate = dataSnapshot.child("date").getValue();
                Object tempImg = dataSnapshot.child("image").getValue();
                Object tempTitle = dataSnapshot.child("title").getValue();
                String dateStr = (tempDate != null) ? tempDate.toString(): "";
                String imgStr = (tempImg != null) ? tempImg.toString() : "";
                String titleStr = (tempTitle != null) ? tempTitle.toString() : "";
                countdownTitle.setText(titleStr);
                //TODO dynamically create the imageview behind the countdown from this snapshot,
                //TODO overlaps with Kendall's thing; we'll talk
                try {
                    countdownEnd = formatter.parse(dateStr);
                } catch (ParseException e) {
                    countdownEnd = new Date();
                    //TODO log this
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Object tempDate = dataSnapshot.child("date").getValue();
                Object tempImg = dataSnapshot.child("image").getValue();
                Object tempTitle = dataSnapshot.child("title").getValue();
                String dateStr = (tempDate != null) ? tempDate.toString(): "";
                String imgStr = (tempImg != null) ? tempImg.toString() : "";
                String titleStr = (tempTitle != null) ? tempTitle.toString() : "";
                countdownTitle.setText(titleStr);
                //TODO dynamically create the imageview behind the countdown from this snapshot,
                //TODO overlaps with Kendall's thing; we'll talk
                try {
                    countdownEnd = formatter.parse(dateStr);
                } catch (ParseException e) {
                    countdownEnd = new Date();
                    //TODO log this
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        long timeDiff = countdownEnd.getTime() - countdownStart.getTime();
        timer = new CountDownTimer(timeDiff, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) -
                        TimeUnit.DAYS.toHours(days);
                long mins = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(hours);
                long secs = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(mins);
                dayText.setText(String.valueOf(days));
                dayLabel.setText((days == 1) ? "Day" : "Days");
                hourText.setText(String.valueOf(days));
                hourLabel.setText((hours == 1) ? "Hour" : "Hours");
                minText.setText(String.valueOf(days));
                minLabel.setText((mins == 1) ? "Minute" : "Minutes");
                secText.setText(String.valueOf(days));
                secLabel.setText((secs == 1) ? "Second" : "Seconds");
            }

            @Override
            public void onFinish() {
                this.start(); //timer always runs, with new info when Firebase updates
            }
        };
        timer.start();
    }

    @Override
    public void onPause() {
        timer.cancel(); //to be super safe wrt memory leaks
        super.onPause();
    }
}
