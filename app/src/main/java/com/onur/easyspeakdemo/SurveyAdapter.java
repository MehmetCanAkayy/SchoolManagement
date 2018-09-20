package com.onur.easyspeakdemo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.StudentMenu.SurveyInfo;
import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder>  implements Filterable {
    private List<SurveyInfo> values;
    private List<SurveyInfo> filteredUserList;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtName;
        public TextView txtGrade;
        public TextView txtPhoneNumber;
        public Button viewButton;

        public ImageView Image ;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.isim);
            txtGrade = v.findViewById(R.id.grade);
            txtPhoneNumber = v.findViewById(R.id.phoneNumber);
            viewButton = v.findViewById(R.id.viewButton);

        }
    }

    public void add(int position, SurveyInfo item) {
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
    public SurveyAdapter(List<SurveyInfo> artistList) {
        values = artistList;
        filteredUserList = artistList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SurveyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.survey_request_card, parent, false);

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
        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog myDialog;
                ImageView closeButton;
                myDialog = new Dialog(view.getContext());

                TextView time1,time2,time3;
                CardView card;

                myDialog.setContentView(R.layout.custom_dialog_survey_request);
                time1 = myDialog.findViewById(R.id.time1);
                time2 = myDialog.findViewById(R.id.time2);
                time3 = myDialog.findViewById(R.id.time3);

                card = myDialog.findViewById(R.id.mycard);
                String txt1 = filteredUserList.get(position).getDay() + " " + filteredUserList.get(position).getStartTime() + " " + filteredUserList.get(position).getEndTime();

                time1.setText(txt1);
                time2.setText(filteredUserList.get(position).getDay2() + " " + filteredUserList.get(position).getStartTime2() + " " + filteredUserList.get(position).getEndTime2());
                time3.setText(filteredUserList.get(position).getDay3() + " " + filteredUserList.get(position).getStartTime3() + " " + filteredUserList.get(position).getEndTime3());

                closeButton = myDialog.findViewById(R.id.close);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();



            }
        });
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

                    List<SurveyInfo> tempFilteredList = new ArrayList<>();

                    for (SurveyInfo user : values) {

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
                filteredUserList = (List<SurveyInfo>) filterResults.values;
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