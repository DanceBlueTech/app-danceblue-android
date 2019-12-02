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

        //Links to the donate button, and creates a listener that launches the webViewer class when clicked.
        // Passes link that is to be opened in the bundle donateB.
        ImageButton donateButton = Objects.requireNonNull(getView()).findViewById(R.id.Donate);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewer.class);
                Bundle donateB = new Bundle();
                donateB.putString("link", "https://danceblue.networkforgood.com");
                intent.putExtras(donateB);
                startActivity(intent);
            }
        });

        //Links to the faq button, and creates a listener that launches the webViewer class when clicked.
        // Passes link that is to be opened in the bundle faqB.
        ImageButton faqButton = Objects.requireNonNull(getView()).findViewById(R.id.FAQs);
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewer.class);
                Bundle faqB = new Bundle();
                faqB.putString("link", "http://www.danceblue.org/frequently-asked-questions/");
                intent.putExtras(faqB);
                startActivity(intent);
            }
        });

        //Links to the contact button, and creates a listener that launches the webViewer class when clicked.
        // Passes link that is to be opened in the bundle contactB.
        ImageButton contactButton = Objects.requireNonNull(getView()).findViewById(R.id.Contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewer.class);
                Bundle contactB = new Bundle();
                contactB.putString("link", "http://www.danceblue.org/meet-the-team/");
                intent.putExtras(contactB);
                startActivity(intent);
            }
        });
    }
}
