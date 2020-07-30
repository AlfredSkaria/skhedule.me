package com.alfred.karela.schedule;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import java.util.Calendar;

public class PolledEvent extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener
{

    private TextView dateTextView;
    private TextView timeTextView;
    private EditText enameedit;
    private EditText edecpedit;
    private EditText elocedit;
    private Button createprivate;
    private boolean mAutoHighlight;
   /* public static  String ename;
    public static  String eloc;
    public static  String edesc;
    public static  String edate;
    public static  String etime;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_event);

        // Find our View instances
        dateTextView = (TextView)findViewById(R.id.date_textview);
        timeTextView = (TextView)findViewById(R.id.time_textview);
        Button dateButton = (Button)findViewById(R.id.date_button);
        Button timeButton = (Button)findViewById(R.id.time_button);
        mAutoHighlight = true;

        enameedit = (EditText)findViewById(R.id.input_name);
        edecpedit = (EditText)findViewById(R.id.input_descp);
        elocedit = (EditText)findViewById(R.id.input_loc);
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
                        PolledEvent.this,
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
                        PolledEvent.this,
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

        final String ename = enameedit.getText().toString();
        final String eloc = elocedit.getText().toString();
        final String edesc = edecpedit.getText().toString();
        final String str = "mmmmmmm";

        createprivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append(ename).append(", ");;
                sb.append(eloc).append(", ");;
                sb.append(edesc);
                String finalMsgText = sb.toString();

                Toast toast = Toast.makeText(PolledEvent.this, finalMsgText , Toast.LENGTH_SHORT);
                toast.show();
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
        dateTextView.setText(date);

        //polling(year,yearEnd,monthOfYear,monthOfYearEnd);
    }

   /* public void polling(int yr,int yrend, int mnth, int mnthend){
        if(yr==yrend && mnth==mnthend) {

        }
    }
*/
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0"+hourOfDayEnd : ""+hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0"+minuteEnd : ""+minuteEnd;
        String time = hourString+"h"+minuteString+" To - "+hourStringEnd+"h"+minuteStringEnd;

        timeTextView.setText(time);
    }
}
