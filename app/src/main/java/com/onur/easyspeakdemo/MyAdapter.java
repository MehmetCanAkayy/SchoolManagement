package com.onur.easyspeakdemo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebaseDemo.LessonInfo;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<LessonInfo> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtContent;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtContent = (TextView) v.findViewById(R.id.secondLine);

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



    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<LessonInfo> myDataset) {
        values = myDataset;
    }

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

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}