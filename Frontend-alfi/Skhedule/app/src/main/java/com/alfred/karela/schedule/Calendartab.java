package com.alfred.karela.schedule;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.calendar.CalendarView;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.JsonArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.InjectView;
import butterknife.ButterKnife;

public class Calendartab extends Fragment implements CalendarPickerController {
    @InjectView(R.id.agenda_calendar_view)
    AgendaCalendarView agendaCalendarView;
    SessionManager session;
     String USER_ID ;
     String AUTH_KEY ;
    JSONArray privateEvents,publicEvents;

    public static List<CalendarEvent> eventList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agenda, container, false);
        ButterKnife.inject(this, view);


        session = new SessionManager(getContext());
         USER_ID = session.getUserId();
         AUTH_KEY = session.getAuthKey();




        mockList(this);



        return view;
    }

    private void mockList(final CalendarPickerController cp) {
//        Mock Events
//        Calendar startTime1 = Calendar.getInstance();
//        Calendar endTime1 = Calendar.getInstance();
//        endTime1.add(Calendar.MONTH, 1);
//        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
//                ContextCompat.getColor(getContext(), R.color.orange_dark), startTime1, endTime1, true);
//        eventList.add(event1);
//
//        Calendar startTime2 = Calendar.getInstance();
//        startTime2.add(Calendar.DAY_OF_YEAR, 1);
//        Calendar endTime2 = Calendar.getInstance();
//        endTime2.add(Calendar.DAY_OF_YEAR, 3);
//        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
//                ContextCompat.getColor(getContext(), R.color.yellow), startTime2, endTime2, true);
//        eventList.add(event2);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching Calendar...");
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://skhedule.tk/api/calendar/all", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            privateEvents=response.getJSONArray("privateEvents");
                            publicEvents = response.getJSONArray("publicEvents");

                            eventList = new ArrayList<CalendarEvent>();

                            if(privateEvents.length()>0){


                                for (int i=0;i<privateEvents.length();i++){
                                    try {
                                        JSONObject obj = privateEvents.getJSONObject(i);

                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                        Date sd =  formatter.parse(obj.getString("start-date")) ;
                                        Date ed = formatter.parse(obj.getString("end-date"));

                                        Calendar startCalendar = Calendar.getInstance();
                                        startCalendar.setTime(sd);
                                        Calendar endCalendar = Calendar.getInstance();
                                        endCalendar.setTime(ed);

                                        BaseCalendarEvent event1 = new BaseCalendarEvent(obj.getString("name"), obj.getString("description"), obj.getString("location"),
                                                ContextCompat.getColor(getContext(), R.color.orange_dark),startCalendar,endCalendar, true);
                                        eventList.add(event1);

                                        Toast.makeText(getActivity(),"Private lenth "+i+"Start date "+sd + "End Date  "+ed ,Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            if(publicEvents.length()>0){


                                for (int i=0;i<publicEvents.length();i++){
                                    try {
                                        JSONObject obj = publicEvents.getJSONObject(i);

                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                        Date sd =  formatter.parse(obj.getString("start-date")) ;
                                        Date ed = formatter.parse(obj.getString("end-date"));

                                        Calendar startCalendar = Calendar.getInstance();
                                        startCalendar.setTime(sd);
                                        Calendar endCalendar = Calendar.getInstance();
                                        endCalendar.setTime(ed);

                                        BaseCalendarEvent event2 = new BaseCalendarEvent(obj.getString("name"), obj.getString("description"), obj.getString("location"),
                                                ContextCompat.getColor(getContext(), R.color.green),startCalendar,endCalendar, true);
                                        eventList.add(event2);







                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }



                                Calendar minDate = Calendar.getInstance();
                                Calendar maxDate = Calendar.getInstance();

                                minDate.add(Calendar.MONTH, -2);
                                minDate.add(Calendar.YEAR,-30);
                                minDate.set(Calendar.DAY_OF_MONTH, 1);
                                maxDate.add(Calendar.YEAR,30);
                                agendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(),cp );

                                progressDialog.dismiss();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Calendar Details Not Found",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                headers.put("X-User-Id", USER_ID);
                headers.put("X-Auth-Token",AUTH_KEY);
                return headers;
            }
        };;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);



    }


    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Intent intent = new Intent(getActivity(), PrivateEvent.class);
        startActivity(intent);
    }

    @Override
    public void onScrollToDate(Calendar calendar) {

    }
}
