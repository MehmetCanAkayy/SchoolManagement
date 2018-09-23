package com.studentsTabLayout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseDemo.Artist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onur.easyspeakdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    ArrayList<DummyParentDataItem> dummyParentDataItems;

    int maxValue = 2;
    Dialog myDialog;
    TextView titleTv,messageTv;
    ImageView closeButton;
    CardView card;
    Button delete,update;
    int color;
    private DatabaseReference databaseUpdate;
    String studentNumber;


    RecyclerDataAdapter(ArrayList<DummyParentDataItem> dummyParentDataItems,String studentNumber) {
        this.dummyParentDataItems = dummyParentDataItems;
        this.studentNumber = studentNumber;
    }

    @Override
    public RecyclerDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_child_listing, parent, false);
        myDialog = new Dialog(itemView.getContext());
        color = itemView.getContext().getResources().getColor(R.color.a1LessonColor);
        databaseUpdate = FirebaseDatabase.getInstance().getReference("students");


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerDataAdapter.MyViewHolder holder, int position) {
        DummyParentDataItem dummyParentDataItem = dummyParentDataItems.get(position);
        holder.textView_parentName.setText(dummyParentDataItem.getParentName());
        //
        int noOfChildTextViews = holder.linearLayout_childItems.getChildCount();
        int noOfChild = dummyParentDataItem.getChildDataItems().size();
        if (noOfChild < noOfChildTextViews) {
            for (int index = noOfChild; index < noOfChildTextViews; index++) {
                TextView currentTextView = (TextView) holder.linearLayout_childItems.getChildAt(index);
                currentTextView.setVisibility(View.GONE);
            }
        }
        for (int textViewIndex = 1; textViewIndex < noOfChild; textViewIndex++) {

            Calendar startTime=Calendar.getInstance();
            Calendar endTime=Calendar.getInstance();

            String[] calendarStartItem = dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getBaslangic().split("%");
            String[] calendarEndItem = dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getBitis().split("%");


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



            CheckBox currentTextView = (CheckBox) holder.linearLayout_childItems.getChildAt(textViewIndex);
            currentTextView.setChecked(true);
            currentTextView.setChecked(dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getSelected());
            String content = dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getTeacher()+ "\n" + dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getDers()
                    + "\n" +startTime.get(Calendar.HOUR_OF_DAY) + ":" +startTime.get(Calendar.MINUTE) + " - " +endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE) +
                    "\n" +dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getIcerik();
            if(dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getDers().equals("Activity")){
                currentTextView.setBackgroundColor(Color.parseColor("#59dbe0"));
            }else if(dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getDers().equals("Social")){
                currentTextView.setBackgroundColor(Color.parseColor("#f57f68"));
            }else if(dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getDers().equals("Chat")){
                currentTextView.setBackgroundColor(Color.parseColor("#87d288"));
            }else if(dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getDers().equals("Speaking")){
                currentTextView.setBackgroundColor(Color.parseColor("#f8b552"));
            }
            currentTextView.setText(content);

            currentTextView.setChecked(dummyParentDataItems.get(position).getChildDataItems().get(textViewIndex).getSelected());

            int finalTextViewIndex = textViewIndex;
            currentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getSelected()==true){
                        currentTextView.setChecked(false);
                        dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(false);

                        databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {



                                for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren() ){


                                    Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);


                                    String[] control= studentInfo.getControlLesson().split(" ");




                                    if(studentInfo.getPhoneNumber().equals(studentNumber)){

                                        if(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Activity")&&control[0].equals("false")){
                                            String LessonKeys = studentInfo.getLessonKey();
                                            String[] out = LessonKeys.split(" ");
                                            if(out.length==1){
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                            }
                                            else{
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                                LessonKeys = LessonKeys.replaceAll("  ", " ");
                                                if(LessonKeys.startsWith(" ")){
                                                    System.out.println("girdi");
                                                    LessonKeys=LessonKeys.substring(1);
                                                }
                                                if(LessonKeys.equals(" ")){
                                                    LessonKeys = "";
                                                }
                                            }
                                            control[0] = "true";
                                            String result = TextUtils.join(" ", control);
                                            Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(),studentInfo.getArtistGrade(),studentInfo.getPhoneNumber(),LessonKeys,result);
                                            studentInfoSnapshot.getRef().setValue(newStudent);

                                        }else if(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Chat")&&control[1].equals("false")){
                                            String LessonKeys = studentInfo.getLessonKey();
                                            String[] out = LessonKeys.split(" ");
                                            if(out.length==1){
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                            }
                                            else{
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                                LessonKeys = LessonKeys.replaceAll("  ", " ");
                                                if(LessonKeys.startsWith(" ")){
                                                    System.out.println("girdi");
                                                    LessonKeys=LessonKeys.substring(1);
                                                }
                                                if(LessonKeys.equals(" ")){
                                                    LessonKeys = "";
                                                }
                                            }
                                            control[1] = "true";
                                            String result = TextUtils.join(" ", control);
                                            Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(),studentInfo.getArtistGrade(),studentInfo.getPhoneNumber(),LessonKeys,result);
                                            studentInfoSnapshot.getRef().setValue(newStudent);

                                        }else if(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Social")&&control[2].equals("false")){
                                            String LessonKeys = studentInfo.getLessonKey();
                                            String[] out = LessonKeys.split(" ");
                                            if(out.length==1){
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                            }
                                            else{
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                                LessonKeys = LessonKeys.replaceAll("  ", " ");
                                                if(LessonKeys.startsWith(" ")){
                                                    System.out.println("girdi");
                                                    LessonKeys=LessonKeys.substring(1);
                                                }
                                                if(LessonKeys.equals(" ")){
                                                    LessonKeys = "";
                                                }
                                            }
                                            control[2] = "true";
                                            String result = TextUtils.join(" ", control);
                                            Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(),studentInfo.getArtistGrade(),studentInfo.getPhoneNumber(),LessonKeys,result);
                                            studentInfoSnapshot.getRef().setValue(newStudent);

                                        }else if(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Speaking")&&control[3].equals("false")){
                                            String LessonKeys = studentInfo.getLessonKey();
                                            String[] out = LessonKeys.split(" ");
                                            if(out.length==1){
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                            }
                                            else{
                                                LessonKeys = LessonKeys.replaceAll(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey(), "");
                                                LessonKeys = LessonKeys.replaceAll("  ", " ");
                                                if(LessonKeys.startsWith(" ")){
                                                    System.out.println("girdi");
                                                    LessonKeys=LessonKeys.substring(1);
                                                }
                                                if(LessonKeys.equals(" ")){
                                                    LessonKeys = "";
                                                }
                                            }
                                            control[3] = "true";
                                            String result = TextUtils.join(" ", control);
                                            Artist newStudent = new Artist(studentInfo.getArtistKey(),studentInfo.getArtistName(),studentInfo.getArtistGrade(),studentInfo.getPhoneNumber(),LessonKeys,result);
                                            studentInfoSnapshot.getRef().setValue(newStudent);

                                        }

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Student Verisi Çekilemedi.");

                            }


                        });


                    }else {

                        databaseUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                            int sayac = 0;

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren()) {
                                    Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);
                                    String LessonKeys = studentInfo.getLessonKey();

                                    Boolean found = Arrays.asList(LessonKeys.split(" ")).contains(dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey());
                                    if (found) {
                                        System.out.println("Dersi alan ogrenci = " + studentInfo.getArtistName());
                                        sayac++;
                                    }
                                }
                                for (DataSnapshot studentInfoSnapshot : dataSnapshot.getChildren()) {


                                    Artist studentInfo = studentInfoSnapshot.getValue(Artist.class);

                                    String[] control = studentInfo.getControlLesson().split(" ");


                                    if (studentInfo.getPhoneNumber().equals(studentNumber)) {

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

                                        } else if (dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Activity") && control[0].equals("true")) {
                                            String LessonKeys = studentInfo.getLessonKey();
                                            if (LessonKeys.equals("") && !dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getSelected()) {
                                                LessonKeys = dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(false);

                                            } else {
                                                LessonKeys += " " + dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(true);

                                            }
                                            control[0] = "false";
                                            String result = TextUtils.join(" ", control);

                                            Artist newStudent = new Artist(studentInfo.getArtistKey(), studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);

                                            studentInfoSnapshot.getRef().setValue(newStudent);

                                            // values.remove(position);
                                            // notifyDataSetChanged();
                                        } else if (dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Chat") && control[1].equals("true")) {
                                            String LessonKeys = studentInfo.getLessonKey();
                                            if (LessonKeys.equals("") && !dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getSelected()) {
                                                LessonKeys = dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(false);

                                            } else {
                                                LessonKeys += " " + dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(true);

                                            }
                                            control[1] = "false";
                                            String result = TextUtils.join(" ", control);

                                            Artist newStudent = new Artist(studentInfo.getArtistKey(), studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);
                                            studentInfoSnapshot.getRef().setValue(newStudent);

                                            //values.remove(position);
                                            //notifyDataSetChanged();
                                        } else if (dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Social") && control[2].equals("true")) {
                                            String LessonKeys = studentInfo.getLessonKey();
                                            if (LessonKeys.equals("") && !dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getSelected()) {
                                                LessonKeys = dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(false);

                                            } else {
                                                LessonKeys += " " + dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(true);

                                            }
                                            control[2] = "false";
                                            String result = TextUtils.join(" ", control);

                                            Artist newStudent = new Artist(studentInfo.getArtistKey(), studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);
                                            studentInfoSnapshot.getRef().setValue(newStudent);


                                            //values.remove(position);
                                            //notifyDataSetChanged();
                                        } else if (dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getDers().equals("Speaking") && control[3].equals("true")) {
                                            String LessonKeys = studentInfo.getLessonKey();
                                            if (LessonKeys.equals("") && !dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getSelected()) {
                                                LessonKeys = dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(false);


                                            } else {
                                                LessonKeys += " " + dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).getLessonKey();
                                                dummyParentDataItems.get(position).getChildDataItems().get(finalTextViewIndex).setSelected(true);


                                            }
                                            control[3] = "false";
                                            String result = TextUtils.join(" ", control);

                                            Artist newStudent = new Artist(studentInfo.getArtistKey(), studentInfo.getArtistName(), studentInfo.getArtistGrade(), studentInfo.getPhoneNumber(), LessonKeys, result);
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
                                            currentTextView.setChecked(false);

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
                    }


//                values.remove(position);
//                notifyDataSetChanged();



                }});



                /*currentTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "" + ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
        }
    }

    @Override
    public int getItemCount() {
        return dummyParentDataItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView textView_parentName ;
        private LinearLayout linearLayout_childItems;

        MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textView_parentName = itemView.findViewById(R.id.tv_parentName);
            linearLayout_childItems = itemView.findViewById(R.id.layout);
            linearLayout_childItems.setVisibility(View.GONE);
            int intMaxNoOfChild = 0;
            for (int index = 0; index < dummyParentDataItems.size(); index++) {
                int intMaxSizeTemp = dummyParentDataItems.get(index).getChildDataItems().size();
                if (intMaxSizeTemp > intMaxNoOfChild) intMaxNoOfChild = intMaxSizeTemp;
            }
            for (int indexView = 0; indexView < intMaxNoOfChild; indexView++) {


                CheckBox checkBox = new CheckBox(context);
                checkBox.setId(indexView);
                checkBox.setPadding(0, 20, 0, 20);
                checkBox.setGravity(Gravity.CENTER);
                checkBox.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                checkBox.setOnClickListener(this);
                linearLayout_childItems.addView(checkBox, layoutParams);


//                    TextView textView = new TextView(context);
//                    textView.setId(indexView);
//                    textView.setPadding(0, 20, 0, 20);
//                    textView.setGravity(Gravity.CENTER);
//                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.background_sub_module_text));
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//
//                    textView.setOnClickListener(this);
//
//                    linearLayout_childItems.addView(textView, layoutParams);


            }
            textView_parentName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.tv_parentName) {
                if (linearLayout_childItems.getVisibility() == View.VISIBLE) {
                    linearLayout_childItems.setVisibility(View.GONE);
                } else {
                    linearLayout_childItems.setVisibility(View.VISIBLE);
                }
            } else {
                TextView textViewClicked = (TextView) view;
                Toast.makeText(context, "" + textViewClicked.getText().toString() + "  " + textViewClicked.getId(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}