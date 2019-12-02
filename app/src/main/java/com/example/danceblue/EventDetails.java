package com.example.danceblue;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
            "mapURL", "address", "startString", "endString", "time"};
    //mapping of above string names to values sent in the bundled args
    private HashMap<String, String> stringsMap;
    private static final String TAG = "EventDetails.java";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //debundle the args and assign as class variables
        stringsMap = new HashMap<>();
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

        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);

        //Sets up a button to save event information to the user's calendar.
        ImageButton calendarAddBtn = new ImageButton(getActivity());
        calendarAddBtn.setImageResource(R.drawable.baseline_add_black_24);
        calendarAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo add event with saved details to calendar
            }
        });
        calendarAddBtn.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(calendarAddBtn);

        //Grabs and displays the image
        ImageView imageView = new ImageView(getActivity());
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

        TextView descriptionTitleView = new TextView(getActivity());
        descriptionTitleView.setText(getString(R.string.description_title));
        descriptionTitleView.setTypeface(descriptionTitleView.getTypeface(),
                Typeface.BOLD);
        linearLayout.addView(descriptionTitleView);

        TextView descriptionView = new TextView(getActivity());
        descriptionView.setText(stringsMap.get("description"));
        linearLayout.addView(descriptionView);

        TextView directionsTitleView = new TextView(getActivity());
        directionsTitleView.setText(getString(R.string.directions_title));
        directionsTitleView.setTypeface(directionsTitleView.getTypeface(),
                Typeface.BOLD);
        linearLayout.addView(directionsTitleView);

        ImageButton directionsBtn = new ImageButton(getActivity());
        Picasso.get().load(stringsMap.get("mapURL")).into(directionsBtn);
        directionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open map app w/ directions to address
            }
        });
        linearLayout.addView(directionsBtn);
    }
}
