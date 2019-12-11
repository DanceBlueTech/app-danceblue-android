//Created by Adrian Carideo, Joe Clements, Kendall Conley
//Copyright © 2019 DanceBlue. All rights reserved.
package com.example.danceblue;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class EventDetails extends Fragment {
    //data members
    private final String[] STRING_NAMES = {"imgURL", "title", "formattedDate", "description",
            "mapURL", "address", "startMillis", "endMillis"};
    //mapping of above string names to values sent in the bundled args
    private HashMap<String, String> stringsMap;
    private static final String TAG = "EventDetails.java";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //debundle the args and assign as class variables
        stringsMap = new HashMap<>(0);
        Bundle args = this.getArguments();
        if (args != null) {
            ArrayList<String> bundledStrings = args.getStringArrayList("stringsAL");
            if ((bundledStrings != null) && (bundledStrings.size() == STRING_NAMES.length)) {
                for (int i = 0; i < STRING_NAMES.length; i++) {
                    stringsMap.put(STRING_NAMES[i], bundledStrings.get(i));
                }
            } else {
                for (String name : STRING_NAMES) {
                    stringsMap.put(name, "bundled strings == bad");
                }

            }
        } else {
            for (String name : STRING_NAMES) {
                stringsMap.put(name, "bundle == bad");
            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout linearLayout = view.findViewById(R.id.eventDetailsLL);

        //Sets up a button to save event information to the user's calendar.
        final ImageButton calendarAddBtn = new ImageButton(getActivity());
        calendarAddBtn.setImageResource(R.drawable.baseline_add_black_24);
        //
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        calendarAddBtn.setLayoutParams(params);
        calendarAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make an intent to launch the calendar for inserting an item
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                //fill the intent with the relevant details
                calIntent.putExtra(CalendarContract.Events.TITLE,
                        stringsMap.get("title"));
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION,
                        stringsMap.get("description"));
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                        stringsMap.get("address"));
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        Long.parseLong(stringsMap.get("startMillis")));
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        Long.parseLong(stringsMap.get("endMillis")));
                startActivity(calIntent); //start the activity the intent describes
            }
        });
        linearLayout.addView(calendarAddBtn);

        //Grabs and displays the image
        ImageView imageView = new ImageView(getActivity());
        imageView.setAdjustViewBounds(true);
        Picasso.get().load(stringsMap.get("imgURL")).into(imageView);
        linearLayout.addView(imageView);

        //Grabs and displays the title
        TextView titleView = new TextView(getActivity());
        titleView.setText(stringsMap.get("title"));
        linearLayout.addView(titleView);

        //Grabs and displays the date
        TextView dateTimeView = new TextView(getActivity());
        dateTimeView.setText(stringsMap.get("formattedDate"));
        linearLayout.addView(dateTimeView);

        TextView descriptionTitleView = new TextView(getActivity(), null, R.style.TitleFont);
        descriptionTitleView.setText(getString(R.string.description_title));
        linearLayout.addView(descriptionTitleView);

        TextView descriptionView = new TextView(getActivity());
        descriptionView.setText(stringsMap.get("description"));
        linearLayout.addView(descriptionView);

        TextView directionsTitleView = new TextView(getActivity(), null, R.style.TitleFont);
        directionsTitleView.setText(getString(R.string.directions_title));
        linearLayout.addView(directionsTitleView);

        ImageButton directionsBtn = new ImageButton(getActivity());
        directionsBtn.setAdjustViewBounds(true);
        Picasso.get().load(stringsMap.get("mapURL")).into(directionsBtn);
        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q="+stringsMap.get("address"));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
            }
        });
        linearLayout.addView(directionsBtn);
    }
}
