package com.example.danceblue;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;

public class FAQ {
    //data members
    private boolean isValid;
    private String Question, Answer;
    private static final String TAG = "FAQ.java";

    //constructor
    public FAQ(DataSnapshot dataSnapshot){
        isValid = true; //assume valid info passed until proved otherwise
        //read DB data, convert to strings after checking for nulls
        Object tempQ = dataSnapshot.child("question").getValue();
        Object tempA = dataSnapshot.child("answer").getValue();

        //convert to strings, checking for null DB children
        Question = (tempQ != null) ? tempQ.toString() : "";
        Answer = (tempA != null) ? tempA.toString() : "";

        //check validity of each string created above before moving on
        if (tempQ.equals("") || tempA.equals("")) {
            isValid = false;
            return;
        }

        Log.d(TAG, "FAQ entry made with: "+isValid()+" "+getQuestion()+" "+getAnswer());
    }

    //methods
    public boolean isValid() {
        return isValid;
    }
    public String getQuestion() {
        return Question;
    }
    public String getAnswer() {
        return Answer;
    }
}
