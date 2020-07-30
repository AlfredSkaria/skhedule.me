package com.alfred.karela.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfred.karela.schedule.notificationview.DataMo;
import com.alfred.karela.schedule.notificationview.MineData;
import com.alfred.karela.schedule.notificationview.NotifyAdapter;

import java.util.ArrayList;

/**
 * Created by Alfred on 17-03-2017.
 */

public class Notification_Activity extends Fragment {

    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<DataMo> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    public Notification_Activity(){

    }

    private static String navigateFrom;//String to get Intent Value
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_content, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


/*
       layoutManager = new LinearLayoutManager(getActivity());
*/

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Swipe to Delete
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {

                // Do Stuff
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(position);

            }

        });
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);



        data = new ArrayList<DataMo>();
        for (int i = 0; i < MineData.etitleArray.length; i++) {
            data.add(new DataMo(
                    MineData.etitleArray[i],
                    MineData.elocArray[i],
                    MineData.edateArray[i]
            ));
        }

        removedItems = new ArrayList<Integer>();
        adapter = new NotifyAdapter(data);
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
