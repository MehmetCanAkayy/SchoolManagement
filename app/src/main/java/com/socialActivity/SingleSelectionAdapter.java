package com.socialActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


import com.onur.easyspeakdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sunil on 12/17/16.
 */

public class SingleSelectionAdapter extends RecyclerView.Adapter {

    private List<SocialActivityClass> itemModels;
    private Context context;
    private int lastCheckedPosition = -1;

    public SingleSelectionAdapter(Context context, List<SocialActivityClass> itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_single, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SocialActivityClass model = itemModels.get(position);
        initializeViews(model, holder, position);
    }


    private void initializeViews(final SocialActivityClass model, final RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).name.setText(model.getName());
        if (model.getId() == lastCheckedPosition){
            ((ItemViewHolder)holder).radioButton.setChecked(true);
        }else{
            ((ItemViewHolder)holder).radioButton.setChecked(false);
        }
        ((ItemViewHolder)holder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                notifyItemRangeChanged(0, itemModels.size());

            }
        });
    }

    public SocialActivityClass getSelectedItem(){
        SocialActivityClass model = itemModels.get(lastCheckedPosition);
        return model;
    }
    public int selectedPosition(){
        return lastCheckedPosition;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.radio)
        RadioButton radioButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}