package com.firebaseDemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>  implements Filterable {
    private List<Artist> values;
    private List<Artist> filteredUserList;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;
        public TextView txtGrade;
        public TextView txtPhoneNumber;

        public ImageView Image ;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.isim);
            txtGrade = v.findViewById(R.id.grade);
            txtPhoneNumber = v.findViewById(R.id.phoneNumber);


        }
    }

    public void add(int position, Artist item) {
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
    public MyAdapter(List<Artist> artistList) {
        values = artistList;
        filteredUserList = artistList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.students, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = filteredUserList.get(position).getArtistName();
        final String grade = filteredUserList.get(position).getArtistGrade();
        final String phone = filteredUserList.get(position).getPhoneNumber();

        holder.txtName.setText("Name: " + name);
        holder.txtGrade.setText("Grade: " + grade);
        holder.txtPhoneNumber.setText("Phone Number: " + phone);


    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredUserList = values;

                } else {

                    List<Artist> tempFilteredList = new ArrayList<>();

                    for (Artist user : values) {

                        // search for user name
                        if (user.getArtistName().toLowerCase().contains(searchString)||user.getPhoneNumber().toLowerCase().contains(searchString)||user.getArtistGrade().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(user);
                        }
                    }

                    filteredUserList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (List<Artist>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

}