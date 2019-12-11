//Created by Adrian Carideo, Joe Clements, Kendall Conley
//Copyright © 2019 DanceBlue. All rights reserved.
package com.example.danceblue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BlogDetails extends Fragment {
    //data members
    private ArrayList<String> keys, values;
    private Boolean isValid = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //debundle the args
        Bundle args = this.getArguments();
        if (args != null) {
            keys = args.getStringArrayList("keys");
            values = args.getStringArrayList("values");
            if (keys == null || values == null || keys.size() != values.size()) isValid = false;
        } else isValid = false;

        //inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout linearLayout = view.findViewById(R.id.blogDetailsLL);

        if (!isValid) {
            TextView textView = new TextView(getActivity());
            textView.setText(R.string.invalidBlogMsg);
            linearLayout.addView(textView);
            return;
        }

        //make the appropriate views for each key-value pair
        for (int i = 0; i < keys.size(); i++) {
            Log.d("BlogDetails.java", "k: "+keys.get(i)+" v: "+values.get(i));
            switch (keys.get(i)) {
                case "image" : //same handling as next case, so allow to fall through

                case "imageURL":
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setAdjustViewBounds(true);
                    Picasso.get().load(values.get(i)).into(imageView);
                    linearLayout.addView(imageView);
                    break;

                case "section": //same handling as next case, so allow to fall through

                case "title":
                    TextView titleView = new TextView(getActivity());
                    titleView.setText(values.get(i));
                    titleView.setTextAppearance(R.style.TitleFont);
                    linearLayout.addView(titleView);
                    break;

                case "author":
                    TextView authorView = new TextView(getActivity());
                    String comboString = values.get(i)+" · "+values.get(keys.indexOf("formattedDate"));
                    authorView.setText(comboString);
                    linearLayout.addView(authorView);
                    break;

                case "text":
                    TextView textView = new TextView(getActivity());
                    textView.setText(values.get(i));
                    linearLayout.addView(textView);
                    break;

                case "quote":
                    //todo quote styling
                    TextView quoteView = new TextView(getActivity());
                    String quoteString = '"'+values.get(i)+'"';
                    quoteView.setText(quoteString);
                    linearLayout.addView(quoteView);
                    break;

                default: //do nothing by default
                    break;
            }
        }
    }
}
