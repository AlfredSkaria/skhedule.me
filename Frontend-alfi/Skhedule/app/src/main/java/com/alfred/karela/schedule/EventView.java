package com.alfred.karela.schedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanks.library.AnimateCheckBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Alfred on 18-03-2017.
 */

public class EventView extends AppCompatActivity {

    public static String[] eventname = {"TechHub Meeting"};
    public static String[] eventdescp = {"Hi, I have a question for you. I am trying to make a biography app which includes the life story of people. I think I dont need to create tons of activities for each person. As a detail activity, one activity must be enough for each rows in RecyclerView. So I have searched a lot about that but there is no useful answer to that. Would you explain that how can I use only one activity for all row items? and how can I implement this detail activity to all row items. Thanks..."};
    public static String[] eventloctn = {"Palakkad"};
    public static String[] eventdate = {"17/03/2017"};

    TextView ename;
    TextView edescp;
    TextView eloc;
    TextView edate;

    private List<Demo> dataList = new ArrayList<>();
    private Set<Demo> checkedSet = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventview);

        ename = (TextView) findViewById(R.id.event_title);
        edescp = (TextView) findViewById(R.id.event_descp);
        eloc = (TextView) findViewById(R.id.event_loctn);
        edate = (TextView) findViewById(R.id.event_date);

        ename.setText(eventname[0]);
        edescp.setText(eventdescp[0]);
        eloc.setText(eventloctn[0]);
        edate.setText(eventdate[0]);

        bindDatas();
        bindViews();

    }

    private void bindDatas() {

        for (int i = 0; i <5; i++) {
            Demo demo = new Demo();
            demo.setContent("this is a simple item : "+ i);
            dataList.add(demo);
        }

    }



    private void bindViews() {

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }



            @Override
            public Object getItem(int position) {
                return null;
            }



            @Override public long getItemId(int position) {
                return 0;

            }



            @Override public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {

                    convertView = LayoutInflater.from(parent.getContext())

                            .inflate(R.layout.item_list_demo, parent, false);

                }



                TextView text = (TextView) convertView.findViewById(R.id.text);

                final AnimateCheckBox checkBox = (AnimateCheckBox) convertView.findViewById(R.id.checkbox);



                final Demo item = dataList.get(position);

                text.setText(item.getContent());

                if(checkedSet.contains(item)){

                    checkBox.setChecked(true);

                }else {

                    //checkBox.setChecked(false); //has animation

                    checkBox.setUncheckStatus();

                }

                checkBox.setOnCheckedChangeListener(new AnimateCheckBox.OnCheckedChangeListener() {

                    @Override public void onCheckedChanged(View buttonView, boolean isChecked) {

                        if (isChecked) {

                            checkedSet.add(item);

                        } else {

                            checkedSet.remove(item);

                        }

                    }

                });



                return convertView;

            }

        });

    }

}