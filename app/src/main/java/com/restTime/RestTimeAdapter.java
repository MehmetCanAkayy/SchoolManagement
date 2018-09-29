package com.restTime;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;

import java.util.List;

public class RestTimeAdapter extends RecyclerView.Adapter<RestTimeAdapter.ViewHolder> {
    private List<restTimeClass> values;
    private List<restTimeClass> filteredUserList;
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

    public void add(int position, restTimeClass item) {
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
    public RestTimeAdapter(List<restTimeClass> artistList) {
        values = artistList;
        filteredUserList = artistList;
        databaseSurvey = FirebaseDatabase.getInstance().getReference("restTime");

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RestTimeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        final String grade = filteredUserList.get(position).getPhoneNumber();
        final boolean phone = filteredUserList.get(position).isReturn();

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


                myDialog.setContentView(R.layout.custom_admin_rest_time);

                EditText baslangic = myDialog.findViewById(R.id.baslangic);
                EditText bitis = myDialog.findViewById(R.id.bitis);
                TextView textView = myDialog.findViewById(R.id.name);
                Button delete = myDialog.findViewById(R.id.delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });

                card = myDialog.findViewById(R.id.mycard);
                String txt1 = filteredUserList.get(position).getArtistName() + " kullanicisi icin secim yapin.";

                textView.setText(txt1);




                closeButton = myDialog.findViewById(R.id.close);
                update = myDialog.findViewById(R.id.update);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseSurvey.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot surveySnapshot : dataSnapshot.getChildren() ){
                                    //Create Artist Class Object and Returning Value
                                    restTimeClass surveyInfo =  surveySnapshot.getValue(restTimeClass.class);
                                    System.out.println(surveyInfo.getPhoneNumber());

                                    System.out.println(filteredUserList.get(position).getPhoneNumber());
                                    if(surveyInfo.getPhoneNumber().equals(filteredUserList.get(position).getPhoneNumber())){
                                        String startTime = baslangic.getText().toString();
                                        String endTime = bitis.getText().toString();
                                        surveyInfo.setStartTime(startTime);
                                        surveyInfo.setEndTime(endTime);
                                        surveyInfo.setReturn(true);
                                        surveySnapshot.getRef().setValue(surveyInfo);
                                    }


                                }
                                filteredUserList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,getItemCount());
                                myDialog.dismiss();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Student Verisi Çekilemedi.");

                            }
                        });


                    }
                });
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });

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


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

}