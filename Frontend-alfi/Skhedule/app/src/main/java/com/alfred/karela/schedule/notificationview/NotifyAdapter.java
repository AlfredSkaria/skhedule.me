package com.alfred.karela.schedule.notificationview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.alfred.karela.schedule.EventView;
import com.alfred.karela.schedule.R;

import java.util.ArrayList;

/**
 * Created by Alfred on 17-03-2017.
 */

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.MyViewHolder> {

    private ArrayList<DataMo> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView eventitle;
        TextView eventloc;
        TextView eventdate;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.eventitle = (TextView) itemView.findViewById(R.id.etitle);
            this.eventloc = (TextView) itemView.findViewById(R.id.eloctn);
            this.eventdate = (TextView) itemView.findViewById(R.id.edate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),EventView.class);
                    v.getContext().startActivity(i);
                }
            });
        }

    }

    public NotifyAdapter(ArrayList<DataMo> data) {
        this.dataSet = data;
    }

    @Override
    public NotifyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_items, parent, false);

/*
       view.setOnClickListener(Fragment_Complete_Work.myOnClickListener);
*/
        NotifyAdapter.MyViewHolder myViewHolder = new NotifyAdapter.MyViewHolder(view);
        return myViewHolder;
    }



    @Override
    public void onBindViewHolder(final NotifyAdapter.MyViewHolder holder, final int listPosition) {

        TextView eventtitle = holder.eventitle;
        TextView eventloc  = holder.eventloc;
        TextView eventdate = holder.eventdate;



        eventtitle.setText(dataSet.get(listPosition).getName());
        eventloc.setText(dataSet.get(listPosition).getloctn());
        eventdate.setText(dataSet.get(listPosition).getdate());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
