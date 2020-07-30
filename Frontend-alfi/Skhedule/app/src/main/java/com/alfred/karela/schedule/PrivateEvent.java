package com.alfred.karela.schedule;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateEvent extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener
{

    private TextView dateTextView;
    private TextView timeTextView;
    private EditText enameedit;
    private EditText edecpedit;
    private EditText elocedit;
    private EditText inv;
    private Button createprivate;
    private boolean mAutoHighlight;


    public static  String ename;
    public static  String eloc;
    public static  String edesc;
    public static  String edate;
    public static  String etime;

    public static int startYear,startMonth,startDay,endYear,endMonth,endDay,startHour,startMin,endHour,endMin;

        SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_event);

        session = new SessionManager(getApplicationContext());

        // Find our View instances
        dateTextView = (TextView)findViewById(R.id.date_textview);
        timeTextView = (TextView)findViewById(R.id.time_textview);
        Button dateButton = (Button)findViewById(R.id.date_button);
        Button timeButton = (Button)findViewById(R.id.time_button);
        mAutoHighlight = true;

        enameedit = (EditText)findViewById(R.id.input_name);
        edecpedit = (EditText)findViewById(R.id.input_descp);
        elocedit = (EditText)findViewById(R.id.input_loc);
        inv = (EditText)findViewById(R.id.inv_mail);
        createprivate = (Button)findViewById(R.id.btn_create_private);



/*        CheckBox ahl = (CheckBox) findViewById(R.id.autohighlight_checkbox);
        ahl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mAutoHighlight = b;
            }
        });*/

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        PrivateEvent.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        PrivateEvent.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });




        createprivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 String ename = enameedit.getText().toString();
                 String eloc = elocedit.getText().toString();
                 String edesc = edecpedit.getText().toString();
                 String invt = inv.getText().toString();

                Toast.makeText(PrivateEvent.this, "event name: "+ename+"event location: "+eloc+"event description: " + edesc , Toast.LENGTH_LONG).show();

                Date startDate = new Date(startYear,startMonth,startDay,startHour,startMin);
                Date endDate = new Date(endYear,endMonth,endDay,endHour,endMin);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


                //String s = "*** test@gmail.com&&^ test2@gmail.com((& ";
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(invt);

                JSONArray arr = new JSONArray();



                while (m.find()) {

                    try {
//                        inviteList.put("mail",m.group());
                        JSONObject obj = new JSONObject();
                        obj.put("mail",m.group());
                        //obj.put("response",0);
                        arr.put(obj);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                Map<String,String> params = new HashMap<String, String>();




                params.put("name",ename);
                params.put("description",edesc);
                params.put("start-date",formatter.format(startDate) );
                params.put("end-date",formatter.format(endDate));
                params.put("location",eloc);
                //params.put("invite-list",arr.toString());

                Log.d("StartDAte",startDate.toString());
                Log.d("EndDate",endDate.toString());
                Log.e("Invite List",arr.toString());

                //progress Bar
                final ProgressDialog progressDialog = new ProgressDialog(PrivateEvent.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Event...");
                progressDialog.show();

                JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, "http://skhedule.tk/api/event/private/add", new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {




                                try{
                                    if(response.getString("response").equals("success") ){
                                        Toast.makeText(PrivateEvent.this, "Event Created Successfull", Toast.LENGTH_LONG).show();



                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(PrivateEvent.this,"Couldnot create Event",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("User-agent", System.getProperty("http.agent"));
                        headers.put("X-Auth-Token",session.getAuthKey());
                        headers.put("X-User-Id",session.getUserId());
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year+" To "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;
        startDay = dayOfMonth;
        startMonth = monthOfYear -1;
        startYear = year - 1900;
        endDay=dayOfMonthEnd;
        endMonth = monthOfYearEnd - 1;
        endYear = yearEnd - 1900;
        dateTextView.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;
        String time = hourString+"h"+minuteString+" To - "+hourStringEnd+"h"+minuteStringEnd;

        startHour = hourOfDay;
        startMin = minute;
        endHour = hourOfDayEnd;
        endMin = minuteEnd;

        timeTextView.setText(time);
    }
}
