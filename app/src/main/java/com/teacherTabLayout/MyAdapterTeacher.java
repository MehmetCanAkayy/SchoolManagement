package com.teacherTabLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebaseDemo.Artist;
import com.firebaseDemo.LessonInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MyAdapterTeacher extends RecyclerView.Adapter<MyAdapterTeacher.ViewHolder> {
    private List<LessonInfo> values;
    private AdapterView.OnItemClickListener listener;
    public Dialog myDialog;
    public ImageView closeButton;
    public CardView card;
    public TextView messageTv;
    String lessonKey;
    Button close;
    private DatabaseReference databaseReference;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtContent;
        public View layout;
        public RelativeLayout relativeLayout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtContent = (TextView) v.findViewById(R.id.secondLine);
            relativeLayout=v.findViewById(R.id.relativeLayout);

        }
    }

    public void add(int position, LessonInfo item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    /*public MyAdapterTeacher(List<LessonInfo> myDataset,String lessonKey) {
        values = myDataset;
        this.lessonKey = lessonKey;
        databaseUpdate = FirebaseDatabase.getInstance().getReference("students");

    }*/

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterTeacher(List<LessonInfo> myDataset) {
        values = myDataset;
        databaseReference = FirebaseDatabase.getInstance().getReference("students");

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterTeacher.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_teacher_layout, parent, false);
        myDialog= new Dialog(v.getContext());
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        Calendar startTime=Calendar.getInstance();
        Calendar endTime=Calendar.getInstance();

        String[] calendarStartItem = values.get(position).getBaslangic().split("%");
        String[] calendarEndItem = values.get(position).getBitis().split("%");

        for (String t : calendarStartItem)
            System.out.println(t);


        startTime.set(Integer.parseInt(calendarStartItem[0]),Integer.parseInt(calendarStartItem[1]),Integer.parseInt(calendarStartItem[2]),Integer.parseInt(calendarStartItem[3]),Integer.parseInt(calendarStartItem[4]));
        endTime.set(Integer.parseInt(calendarEndItem[0]),Integer.parseInt(calendarEndItem[1]),Integer.parseInt(calendarEndItem[2]),Integer.parseInt(calendarEndItem[3]),Integer.parseInt(calendarEndItem[4]));

        String day = null;

        if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.MONDAY){
            day = "MONDAY";
        }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.TUESDAY){
            day = "TUESDAY";
        }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.WEDNESDAY){
            day = "WEDNESDAY";
        }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.THURSDAY){
            day = "THURSDAY";
        }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.FRIDAY){
            day = "FRIDAY";
        }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.SATURDAY){
            day = "SATURDAY";
        }else if(startTime.get((Calendar.DAY_OF_WEEK))==Calendar.SUNDAY){
            day = "SUNDAY";
        }
        holder.txtHeader.setText(startTime.get(Calendar.DAY_OF_MONTH)+"." + startTime.get(Calendar.MONTH)+"." + startTime.get(Calendar.YEAR) + " " + day);
        String content = values.get(position).getTeacher() + "\n" + values.get(position).getDers()  + "\n" +startTime.get(Calendar.HOUR_OF_DAY) + ":" +startTime.get(Calendar.MINUTE) + " - " +endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE) + "\n" +values.get(position).getIcerik();
                holder.txtContent.setText(content);
        if(values.get(position).getDers().equals("Activity")){
            holder.txtContent.setBackgroundColor(Color.parseColor("#59dbe0"));
        }else if(values.get(position).getDers().equals("Social")){
            holder.txtContent.setBackgroundColor(Color.parseColor("#f57f68"));
        }else if(values.get(position).getDers().equals("Chat")){
            holder.txtContent.setBackgroundColor(Color.parseColor("#87d288"));
        }else if(values.get(position).getDers().equals("Speaking")){
            holder.txtContent.setBackgroundColor(Color.parseColor("#f8b552"));
        }

        holder.txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> ogrenci = new ArrayList<>();
                        for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren() ){

                            Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);
                            String LessonKey =studentInfo.getLessonKey();
                            Boolean found= Arrays.asList(LessonKey.split(" ")).contains(values.get(position).getLessonKey());
                            if(found){

                                ogrenci.add(studentInfo.getArtistName());


                            }


                        }

                        myDialog.setContentView(R.layout.custom_dialog_box);
                        messageTv = myDialog.findViewById(R.id.content);
                        card = myDialog.findViewById(R.id.mycard);
                        close = myDialog.findViewById(R.id.closee);
                        String txt ="";
                        for(int i=0 ; i<ogrenci.size(); i++){


                            txt+=ogrenci.get(i);

                        }

                        messageTv.setText(txt);


                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        myDialog.show();



                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Student Verisi Çekilemedi.");

                    }


                });




//                values.remove(position);
//                notifyDataSetChanged();

            }

        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}