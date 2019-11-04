package com.example.danceblue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class viewFAQ extends AppCompatActivity {
    private LinearLayout faqLL;
    private ArrayList<FAQ> faqAL;
    private static final String TAG = "viewFAQ.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faq);

        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        fillQA(databaseRef);
    }

    protected void fillQA(DatabaseReference databaseRef){
        //begin faq code
        //layout to hold variable amount of faqs
        faqLL = findViewById(R.id.faqLL);
        //arraylist to hold faq objects that are in the layout above.
        //allows the faqs to be easily sorted and added/re/moved
        faqAL = new ArrayList<>();

        databaseRef.child("FAQs").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                //make a representative faq object from the DB info
                FAQ faq = new FAQ(dataSnapshot);
                if(faq.isValid()){ //if the new child had no null fields
                    faqAL.add(faq);//add to data to be drawn
                    faqLL.removeAllViews();//clear the layout
                    for(FAQ faq1 : faqAL){ //redraw layout
                        TextView Q = new TextView(viewFAQ.this);
                        Q.setText(faq1.getQuestion()); //set a textview to Question
                        TextView A = new TextView(viewFAQ.this);
                        Q.setTextAppearance(viewFAQ.this, R.style.TitleFont);
                        A.setText(faq1.getAnswer()); //set a textview to Answer
                        LinearLayout verticalLL = new LinearLayout(viewFAQ.this);//horizontal by default
                        verticalLL.addView(Q);
                        verticalLL.addView(A);
                        verticalLL.setOrientation(LinearLayout.VERTICAL);
                        faqLL.addView(verticalLL);//add the new Q/A to announcements view
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                //make a representative faq object from the DB info
                FAQ faq = new FAQ(dataSnapshot);
                if (faq.isValid()) { //if the new child had no null fields
                    faqAL.add(faq);//add to data to be drawn
                    faqLL.removeAllViews();//clear the layout
                    for(FAQ faq1 : faqAL){ //redraw layout
                        TextView Q = new TextView(viewFAQ.this);
                        Q.setText(faq1.getQuestion()); //set a textview to Question
                        TextView A = new TextView(viewFAQ.this);
                        Q.setTextAppearance(viewFAQ.this, R.style.TitleFont);
                        A.setText(faq1.getAnswer()); //set a textview to Answer
                        LinearLayout verticalLL = new LinearLayout(viewFAQ.this); //horizontal by default
                        verticalLL.addView(Q);
                        verticalLL.addView(A);
                        verticalLL.setOrientation(LinearLayout.VERTICAL);
                        faqLL.addView(verticalLL);//add the new Q/A to announcements view
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //intentionally left blank
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
}
