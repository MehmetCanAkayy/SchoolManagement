package com.socialActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseDemo.Artist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SocialActivityAdapter extends RecyclerView.Adapter<SocialActivityAdapter.ViewHolder> {
    private List<SocialActivityClass> values;
    private List<SocialActivityClass> filteredUserList;
    String studentKey;
    DatabaseReference databaseActivities;
    DatabaseReference databaseUpdate;

    ScaleRatingBar ratingBar;







    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;


        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.isim);
            ratingBar = v.findViewById(R.id.simpleRatingBar);
            databaseActivities = FirebaseDatabase.getInstance().getReference("activities");
            databaseUpdate = FirebaseDatabase.getInstance().getReference("activities");



        }
    }

    public void add(int position, SocialActivityClass item) {
        values.add(position, item);
        filteredUserList.add(position,item);

        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        filteredUserList.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = values.size();
        filteredUserList.clear();
        values.clear();
        notifyItemRangeRemoved(0, size);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SocialActivityAdapter(List<SocialActivityClass> artistList,String studentKey) {
        values = artistList;
        filteredUserList = artistList;
        this.studentKey =studentKey;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SocialActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {


        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.social_activity_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);

        final ViewHolder holder = new ViewHolder(v);

        return holder;
        //return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = filteredUserList.get(position).getName();

        holder.txtName.setText( name);
        ratingBar.setRating(filteredUserList.get(position).getRating());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }
    public class EmployeeAgeComparator implements Comparator<SocialActivityClass> {

        @Override
        public int compare(SocialActivityClass emp1, SocialActivityClass emp2) {
            return (int) (emp2.getRating() - emp1.getRating());
        }
    }


}