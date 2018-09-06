package com.firebaseDemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.ImageView;





import com.onur.easyspeakdemo.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>{
    private List<LessonInfo> values;
    private AdapterView.OnItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    /*public void ContentAdapter(List<Teacher> content, AdapterView.OnItemClickListener listener){
        this.content=values;
        this.listener=listener;
    }*/
    public Dialog myDialog;
    public TextView titleTv,messageTv;
    public ImageView closeButton;
    public CardView card;




    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtDay;
        public TextView txtClass;
        public TextView txtGrade;
        public TextView txtContent;
        public View layout;
        public RelativeLayout relativeLayout;




        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtDay = v.findViewById(R.id.firstLine);
            txtGrade = v.findViewById(R.id.seviye);
            txtClass=v.findViewById(R.id.sinif);
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

    public void clear() {
        int size = values.size();
        values.clear();
        notifyItemRangeRemoved(0, size);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TeacherAdapter(List<LessonInfo> teacherList) {
        values = teacherList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TeacherAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                        int viewType) {
        // create a new view
        final LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        myDialog= new Dialog(parent.getContext());
        //        AlertDialog.Builder builder = new AlertDialog.Builder(AlamKanakActivity.this);
//        builder.setTitle("Lesson Content");
//        builder.setMessage(event.getGrade() + "\n" + event.getTeacher() + "\n" + event.getStartEnd() + "\n" + event.mGetContent());

                View v =
                inflater.inflate(R.layout.activity_teacher, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TeacherAdapter.ViewHolder vh = new TeacherAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TeacherAdapter.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String sinif = values.get(position).getTeacher();
        final String seviye = values.get(position).getGrade();
        final String tarih = values.get(position).getBaslangic();
        final String icerik=values.get(position).getIcerik();


        holder.txtClass.setText(sinif);
        holder.txtGrade.setText(seviye);
        holder.txtDay.setText(tarih);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.custom_dialog_teacher);
                messageTv = myDialog.findViewById(R.id.content);
                card = myDialog.findViewById(R.id.mycard);
                closeButton = myDialog.findViewById(R.id.close);
                messageTv.setText(sinif);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();

                        //builder.show();

                    }


                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

            }

            // Return the size of your dataset (invoked by the layout manager)


        });
    }
    @Override
    public int getItemCount() {
        return values.size();
    }
}
