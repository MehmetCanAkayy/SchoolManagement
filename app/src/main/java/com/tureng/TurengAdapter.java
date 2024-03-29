package com.tureng;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebaseDemo.Artist;
import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.List;

public class TurengAdapter extends RecyclerView.Adapter<TurengAdapter.ViewHolder>  {
    private List<Result> values;
    private List<Result> filteredUserList;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;


        public ImageView Image ;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.word);



        }
    }

    public void add(int position, Result item) {
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
    public TurengAdapter(List<Result> artistList) {
        values = artistList;
        filteredUserList = artistList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TurengAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.tureng_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = filteredUserList.get(position).toString();

        holder.txtName.setText( name);


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

}