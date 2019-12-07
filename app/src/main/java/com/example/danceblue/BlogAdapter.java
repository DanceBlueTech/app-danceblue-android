package com.example.danceblue;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//https://guides.codepath.com/android/using-the-recyclerview
public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogHolder> {
    //inner viewholder class
    public class BlogHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView categoryView, titleView, authorView, dateView;

        public BlogHolder(View blogItemView) {
            super(blogItemView);
            categoryView = blogItemView.findViewById(R.id.blogCategoryView);
            imageView = blogItemView.findViewById(R.id.blogImageView);
            titleView = blogItemView.findViewById(R.id.blogTitleView);
            authorView = blogItemView.findViewById(R.id.blogAuthorView);
            dateView = blogItemView.findViewById(R.id.blogDateView);
        }
    }

    private ArrayList<BlogItem> blogItems; //ArrayList of BlogItem objects to draw views from

    public BlogAdapter(ArrayList<BlogItem> data) {
        blogItems = data;
    }

    //callback to make a fresh blog entry layout template, wrap in a ViewHolder, and return it
    @NonNull
    @Override
    public BlogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext(); //setup
        LayoutInflater inflater = LayoutInflater.from(context);
        View blogEntryView = inflater.inflate(R.layout.blog_entry_item, parent, false);
        return new BlogHolder(blogEntryView);
    }

    //callback to populate the template's views with data from the correct BlogItem
    @Override
    public void onBindViewHolder(@NonNull BlogHolder holder, int position) {
        BlogItem blogItem = blogItems.get(position); //get the data model
        //populate the views with the relevant data from blogItem
        //set the category view based on position
        TextView categoryView = holder.categoryView;
        if (position == 0) {
            categoryView.setText(R.string.featured);
            categoryView.setTextAppearance(R.style.TitleFont);
        } else if (position == 1) {
            categoryView.setText(R.string.recent);
            categoryView.setTextAppearance(R.style.TitleFont);
        } else {
            categoryView.setVisibility(View.GONE);
        }
        //Grab and load image
        ImageView imageView = holder.imageView;
        imageView.setAdjustViewBounds(true);
        Picasso.get().load(blogItem.getImageURL()).into(imageView);
        //Grab and load title
        TextView textViewTitle = holder.titleView;
        textViewTitle.setText(blogItem.getTitle());
        //Grab and load author
        TextView textViewAuthor = holder.authorView;
        textViewAuthor.setText(blogItem.getAuthor());
        //Grab and load date
        TextView textViewDate = holder.dateView;
        textViewDate.setText(blogItem.getFormattedDate());
    }

    @Override
    public int getItemCount() {
        return blogItems.size();
    }
}
