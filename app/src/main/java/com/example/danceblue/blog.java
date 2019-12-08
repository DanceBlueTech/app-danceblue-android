package com.example.danceblue;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

//This class defines the blog fragment, and the operations that it entails. The layout for the fragment
// can be found in blog.xml
public class blog extends Fragment {
    //data members
    private DatabaseReference databaseReference;
    private ArrayList<BlogItem> blogEntries;
    private View view;
    private Context context;
    private final String TAG = "blog.java";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(R.layout.blog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //initialize with size 0 for the size dependent log in remakeEntriesView()
        //no-args constructor starts w/ size 10, was leading to NPEs
        blogEntries = new ArrayList<>(0);
        this.view = view;
        //setup the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvBlog);
        final BlogAdapter adapter = new BlogAdapter(blogEntries, this);
        recyclerView.setAdapter(adapter); //populates the RecyclerView w/ empty ArrayList
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //populate the ArrayList with BlogItem objects constructed from the firebase data.
        //then notify the adapter of the relevant data changes to be reflect in RecyclerView
        databaseReference.child("blog").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BlogItem blog = new BlogItem(dataSnapshot); //make a blog from the added child
                if (blog.isValid()) {
                    blogEntries.add(blog);
                    Collections.sort(blogEntries);
                    adapter.notifyItemInserted(blogEntries.indexOf(blog));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BlogItem blog = new BlogItem(dataSnapshot); //make a blog from the added child
                if (blog.isValid()) {
                    for (BlogItem blog1 : blogEntries) {
                        if (blog.getId().equals(blog1.getId())) { //if the new id matches
                            blogEntries.remove(blog1); //remove the old blog w/ same id
                        }
                    }
                    blogEntries.add(blog); //add it to the data arraylist
                    Collections.sort(blogEntries);
                    adapter.notifyItemChanged(blogEntries.indexOf(blog));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                BlogItem blog = new BlogItem(dataSnapshot); //make a blog from the added child
                if (blog.isValid()) {
                    for (BlogItem blog1 : blogEntries) {
                        if (blog.getId().equals(blog1.getId())) { //if the new id matches
                            int index = blogEntries.indexOf(blog1);
                            blogEntries.remove(blog1); //remove the old blog w/ same id
                            adapter.notifyItemRemoved(index);
                        }
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

    //called in the anonymous onClick listeners of the parent LinearLayout of each blog_entry_item
    //in the RecyclerView. the listener is implemented in BlogAdapter.java
    public void openBlogDetailsListener(BlogItem blogItem) {
        Log.d(TAG, "openBlogDetailsListener: called");
        Bundle args = new Bundle();
        ArrayList<String> keyStrings = new ArrayList<>(0);
        ArrayList<String> valueStrings = new ArrayList<>(0);
        keyStrings.add("imageURL");
        valueStrings.add(blogItem.getImageURL());
        keyStrings.add("title");
        valueStrings.add(blogItem.getTitle());
        keyStrings.add("author");
        valueStrings.add(blogItem.getAuthor());
        keyStrings.add("formattedDate");
        valueStrings.add(blogItem.getFormattedDate());

        //encode the chunks into strings that can be bundled
        DataSnapshot chunks = blogItem.getChunksDSS();
        for (DataSnapshot chunk : chunks.getChildren()) {
            //if the chunk has valid structure
            if ((chunk.child("type").exists()) && (chunk.child("data").hasChildren())) {
                //ignore IDE warnings about NPEs within this switch statement. The if conditionals
                //checking .exists() means by definition they can't be null. Android Studio's
                //NPE finder doesn't recognize the .exists() calls I guess?
                switch (chunk.child("type").getValue().toString()) {
                    case "image":
                        if (chunk.child("data").child("image").exists()) {
                            keyStrings.add(chunk.child("type").getValue().toString());
                            valueStrings.add(
                                    chunk.child("data").child("image").getValue().toString());
                        }
                        break;

                    case "section":
                        if (chunk.child("data").child("title").exists()) {
                            keyStrings.add(chunk.child("type").getValue().toString());
                            valueStrings.add(
                                    chunk.child("data").child("title").getValue().toString());
                        }
                        break;

                    case "text":
                        if (chunk.child("data").child("text").exists()) {
                            keyStrings.add(chunk.child("type").getValue().toString());
                            valueStrings.add(
                                    chunk.child("data").child("text").getValue().toString());
                        }
                        break;

                    case "quote":
                        if (chunk.child("data").child("quote").exists()) {
                            keyStrings.add(chunk.child("type").getValue().toString());
                            valueStrings.add(
                                    chunk.child("data").child("quote").getValue().toString());
                        }
                        break;

                    default: //do nothing by default
                        break;
                }

            }
        } //end chunk encoding

        //attach the args
        args.putStringArrayList("keys", keyStrings);
        args.putStringArrayList("values", valueStrings);
        BlogDetails detailsFragment = new BlogDetails();
        detailsFragment.setArguments(args);
        //replace the whole tabs fragment with the details fragment, adding to the backstack to
        //enable back-button functionality, null value means no retrieval ID from backstack
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                detailsFragment).addToBackStack(null).commit();
    }
}
