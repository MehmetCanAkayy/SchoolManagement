package com.studentsTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebaseDemo.Artist;
import com.firebaseDemo.LessonInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<LessonInfo> values;
    List<LessonInfo> tempValues;

    private String studentNumber;
    private DatabaseReference databaseUpdate;
    boolean activity = true;
    boolean chat = true;
    boolean social = true;
    boolean speaking = true;
    Dialog myDialog;
    TextView titleTv,messageTv;
    ImageView closeButton;
    CardView card;
    Button delete,update;
    int color;
    int maxValue = 2;




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtContent;
        public View layout;
        public CheckBox checkBox;
        private boolean[] mCheckState;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtContent = (TextView) v.findViewById(R.id.secondLine);
            checkBox= v.findViewById(R.id.checkBox);


        }
    }

    public void add(int position, LessonInfo item) {
        values.add(position, item);
        tempValues.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);

        tempValues.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<LessonInfo> myDataset,String studentNumber) {
        values = myDataset;
        tempValues=myDataset;
        this.studentNumber = studentNumber;
        databaseUpdate = FirebaseDatabase.getInstance().getReference("students");



    }
    /*public ArrayList<Boolean> SelectionLesson(List<LessonInfo> datasetBool, int position){
        datasetBool=values;
        ArrayList<Boolean> list = new ArrayList<Boolean>(100);
        //List<Boolean> liste=new ArrayList<Boolean>(values.get(position).getSelected()(new Boolean[100]));
        list=datasetBool.get(position).getSelected();


        return list;
    }*/

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        myDialog = new Dialog(v.getContext());
        color = v.getResources().getColor(R.color.a1LessonColor);

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
        holder.checkBox.setChecked(false);
        holder.checkBox.setChecked(values.get(position).getSelected());




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
        holder.checkBox.setChecked(values.get(position).getSelected());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
            int sayac = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren() ) {
              Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);
                   String LessonKeys = studentInfo.getLessonKey();

                   Boolean found = Arrays.asList(LessonKeys.split(" ")).contains(values.get(position).getLessonKey());
                   if(found){
                       System.out.println("Dersi alan ogrenci = " + studentInfo.getArtistName());
                       sayac++;
                   }
               }
               for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren() ){


                   Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);

                   String[] control= studentInfo.getControlLesson().split(" ");




                   if(studentInfo.getPhoneNumber().equals(studentNumber)) {

                           if (sayac >= maxValue) {
                               myDialog.setContentView(R.layout.custom_dialog_box);
                               messageTv = myDialog.findViewById(R.id.content);
                               card = myDialog.findViewById(R.id.mycard);

                               card.setBackgroundColor(color);

                               closeButton = myDialog.findViewById(R.id.close);
                               delete = myDialog.findViewById(R.id.delete);
                               update = myDialog.findViewById(R.id.update);
                               update.setVisibility(View.GONE);
                               delete.setVisibility(View.GONE);


                               messageTv.setText("Secmis oldugunuz dersi max kapasiteye ulasmistir. Lutfen baska bir saatte uygun dersi seciniz.");


                               closeButton.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       myDialog.dismiss();
                                   }
                               });


                               myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                               myDialog.show();

                           } else if (values.get(position).getDers().equals("Activity") && control[0].equals("true")) {
                               String LessonKeys = studentInfo.getLessonKey();
                               if (LessonKeys.equals("") && values.get(position).getSelected()) {
                                   LessonKeys = values.get(position).getLessonKey();
                                   values.get(position).setSelected(false);

                               } else {
                                   LessonKeys += " " + values.get(position).getLessonKey();
                                   values.get(position).setSelected(true);

                               }
                               control[0] = "false";
                               String result = TextUtils.join(" ", control);

                               Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);

                               studentInfoSnapshot.getRef().setValue(newStudent);

                              // values.remove(position);
                              // notifyDataSetChanged();
                           } else if (values.get(position).getDers().equals("Chat") && control[1].equals("true")) {
                               String LessonKeys = studentInfo.getLessonKey();
                               if (LessonKeys.equals("") &&values.get(position).getSelected()) {
                                   LessonKeys = values.get(position).getLessonKey();
                                   values.get(position).setSelected(false);

                               } else {
                                   LessonKeys += " " + values.get(position).getLessonKey();
                                   values.get(position).setSelected(true);

                               }
                               control[1] = "false";
                               String result = TextUtils.join(" ", control);

                               Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);
                               studentInfoSnapshot.getRef().setValue(newStudent);

                               //values.remove(position);
                               //notifyDataSetChanged();
                           } else if (values.get(position).getDers().equals("Social") && control[2].equals("true")) {
                               String LessonKeys = studentInfo.getLessonKey();
                               if (LessonKeys.equals("")&&values.get(position).getSelected()) {
                                   LessonKeys = values.get(position).getLessonKey();
                                   values.get(position).setSelected(false);

                               } else {
                                   LessonKeys += " " + values.get(position).getLessonKey();
                                   values.get(position).setSelected(true);

                               }
                               control[2] = "false";
                               String result = TextUtils.join(" ", control);

                               Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);
                               studentInfoSnapshot.getRef().setValue(newStudent);


                               //values.remove(position);
                               //notifyDataSetChanged();
                           } else if (values.get(position).getDers().equals("Speaking") && control[3].equals("true") ) {
                               String LessonKeys = studentInfo.getLessonKey();
                               if (LessonKeys.equals("")&&values.get(position).getSelected()) {
                                   LessonKeys = values.get(position).getLessonKey();
                                   values.get(position).setSelected(false);


                               } else {
                                   LessonKeys += " " + values.get(position).getLessonKey();
                                   values.get(position).setSelected(true);


                               }
                               control[3] = "false";
                               String result = TextUtils.join(" ", control);

                               Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);
                               studentInfoSnapshot.getRef().setValue(newStudent);

                               //values.remove(position);
                              // notifyDataSetChanged();
                           } else {
                               myDialog.setContentView(R.layout.custom_dialog_box);
                               messageTv = myDialog.findViewById(R.id.content);
                               card = myDialog.findViewById(R.id.mycard);

                               card.setBackgroundColor(color);

                               closeButton = myDialog.findViewById(R.id.close);
                               delete = myDialog.findViewById(R.id.delete);
                               update = myDialog.findViewById(R.id.update);
                               update.setVisibility(View.GONE);
                               delete.setVisibility(View.GONE);
                               holder.checkBox.setChecked(false);

                               messageTv.setText("Bu katogoride baska bir dersiniz daha bulunmaktadir. Islem yapmak icin sectiginiz dersi kaldirmaniz gerekmektedir.");


                               closeButton.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       myDialog.dismiss();
                                   }
                               });


                               myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                               myDialog.show();

                           }

//                                String LessonKeys = studentInfo.getLessonKey();
//                                if(LessonKeys.equals("")){
//                                    LessonKeys =values.get(position).getLessonKey();
//
//                                }else{
//                                    LessonKeys += " " +values.get(position).getLessonKey();
//
//                                }
//                                Artist newStudent = new Artist(studentInfo.getArtistName(),studentInfo.getArtistGrade(),studentInfo.getPhoneNumber(),LessonKeys);
//
//                                studentInfoSnapshot.getRef().setValue(newStudent);
//                                values.remove(position);
//                                notifyDataSetChanged();

                       }


                   }


               }


           @Override
           public void onCancelled(DatabaseError databaseError) {
               System.out.println("Student Verisi Çekilemedi.");

           }


       });


//                values.remove(position);
//                notifyDataSetChanged();



        }});





    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
