package com.example.danceblue;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class EventDetails extends Fragment {
    //data members for bundled args
    private String imgURL, title, formattedDate, description, mapURL, address, startString,
            endString, time;
    private String[] dataStrings = new String[]{imgURL, title, formattedDate, description, mapURL,
    address, startString, endString, time};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //debundle the args and assign as class variables
        Bundle args = this.getArguments();
        if (args != null) {
            ArrayList<String> bundledStrings = args.getStringArrayList("stringsAL");
            if (bundledStrings != null) {
                for (int i = 0; i < dataStrings.length; i++) {dataStrings[i] = bundledStrings.get(i);}
            } else {
                for (int i = 0; i < dataStrings.length; i++) {dataStrings[i] = "";}
            }
        } else {
            for (int i = 0; i < dataStrings.length; i++) {dataStrings[i] = "";}
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
