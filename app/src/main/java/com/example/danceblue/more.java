package com.example.danceblue;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Objects;

//This class defines the more fragment, and the operations that it entails. The layout for the fragment
// can be found in more.xml
public class more extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Links to the donate button, and creates a listener that launches the viewDonate class when clicked.
        ImageButton donateButton = Objects.requireNonNull(getView()).findViewById(R.id.Donate);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), viewDonate.class);
                startActivity(intent);
            }
        });

        //Links to the faqw button, and creates a listener that launches the viewFAQ class when clicked.
        ImageButton faqButton = Objects.requireNonNull(getView()).findViewById(R.id.FAQs);
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), viewFAQ.class);
                startActivity(intent);
            }
        });

        //Links to the contact button, and creates a listener that launches the viewContact class when clicked.
        ImageButton contactButton = Objects.requireNonNull(getView()).findViewById(R.id.Contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), viewContact.class);
                startActivity(intent);
            }
        });
    }
}
