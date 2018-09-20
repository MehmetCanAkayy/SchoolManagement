package com.onur.easyspeakdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.StudentMenu.StudentSurveyActivity;
import com.StudentMenu.Survey;
import com.StudentMenu.SurveyInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder>  implements Filterable {
    private List<SurveyInfo> values;
    private List<SurveyInfo> filteredUserList;
    Context mContext;
    private DatabaseReference databaseSurvey;






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
        databaseSurvey = FirebaseDatabase.getInstance().getReference("surveys");

    }

    // Create new views (invoked by the layout manager)
    @Override
    public SurveyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());

        mContext = parent.getContext();
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
                Button update;
                myDialog = new Dialog(view.getContext());

                TextView time1,time2,time3,time4;
                CardView card;

                myDialog.setContentView(R.layout.custom_dialog_survey_request);
                time1 = myDialog.findViewById(R.id.time1);
                time2 = myDialog.findViewById(R.id.time2);
                time3 = myDialog.findViewById(R.id.time3);
                time4 = myDialog.findViewById(R.id.time4);

                card = myDialog.findViewById(R.id.mycard);
                String txt1 = filteredUserList.get(position).getDay() + " " + filteredUserList.get(position).getStartTime() + " " + filteredUserList.get(position).getEndTime();

                time1.setText(txt1);
                time2.setText(filteredUserList.get(position).getDay2() + " " + filteredUserList.get(position).getStartTime2() + " " + filteredUserList.get(position).getEndTime2());
                time3.setText(filteredUserList.get(position).getDay3() + " " + filteredUserList.get(position).getStartTime3() + " " + filteredUserList.get(position).getEndTime3());

                databaseSurvey.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot surveySnapshot : dataSnapshot.getChildren() ){
                            //Create Artist Class Object and Returning Value
                            Survey surveyInfo =  surveySnapshot.getValue(Survey.class);
                            System.out.println(surveyInfo.getStudentsKey());

                            System.out.println(surveyInfo.getStudentsKey());
                            System.out.println(filteredUserList.get(position).getStudentKey());
                            if(surveyInfo.getStudentsKey().equals(filteredUserList.get(position).getStudentKey())){
                                time4.setText("Ogrenci icin secmis oldugunuz survey saati" + "\n" +surveyInfo.getDay() + " " + surveyInfo.getStartTime() +" "+ surveyInfo.getEndTime());
                            }


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Student Verisi Çekilemedi.");

                    }
                });


                closeButton = myDialog.findViewById(R.id.close);
                update = myDialog.findViewById(R.id.update);


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Intent studentSurvey=new Intent((Activity)mContext, SurveyRequest.class);

                        studentSurvey.putExtra("phoneNumber",filteredUserList.get(position).getPhoneNumber());
                        studentSurvey.putExtra("grade",filteredUserList.get(position).getArtistGrade());
                        studentSurvey.putExtra("name",filteredUserList.get(position).getArtistName());
                        studentSurvey.putExtra("studentKey",filteredUserList.get(position).getStudentKey());
                        studentSurvey.putExtra("day1",filteredUserList.get(position).getDay());
                        studentSurvey.putExtra("day2",filteredUserList.get(position).getDay2());
                        studentSurvey.putExtra("day3",filteredUserList.get(position).getDay3());
                        studentSurvey.putExtra("start1",filteredUserList.get(position).getStartTime());
                        studentSurvey.putExtra("start2",filteredUserList.get(position).getStartTime2());
                        studentSurvey.putExtra("start3",filteredUserList.get(position).getStartTime3());
                        studentSurvey.putExtra("end1",filteredUserList.get(position).getEndTime());
                        studentSurvey.putExtra("end2",filteredUserList.get(position).getEndTime2());
                        studentSurvey.putExtra("end3",filteredUserList.get(position).getEndTime3());
                        studentSurvey.putExtra("survey",time4.getText().toString());






                        ((Activity)mContext).startActivityForResult(studentSurvey, 1);

                    }
                });
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

    public class CustomAdapter extends ArrayAdapter<String> {

        private int hidingItemIndex;

        public CustomAdapter(Context context, int textViewResourceId, List<String> objects, int hidingItemIndex) {
            super(context, textViewResourceId, objects);
            this.hidingItemIndex = hidingItemIndex;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v = null;

            for(int i = 0 ; i < hidingItemIndex; i++){
                if (position < hidingItemIndex) {
                    TextView tv = new TextView(getContext());
                    tv.setVisibility(View.GONE);
                    v = tv;
                } else {
                    v = super.getDropDownView(position, null, parent);
                }
            }

            return v;
        }
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