package com.mobilecomputing.sahayak.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Debug;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import com.mobilecomputing.sahayak.JavaClasses.CustomTimePickerDialog;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MentorOptionsActivity extends AppCompatActivity /*implements CalendarDatePickerDialogFragment.OnDateSetListener,
        RadialTimePickerDialogFragment.OnTimeSetListener*/ {
    CalendarDatePickerDialogFragment calDatePicker;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private CustomTimePickerDialog.OnTimeSetListener timeSetListener;
    private  int hourOfDay;
    private  int minute;
    private boolean is24HourView;
    private int flagDate;
    private int flagTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_options);
        final Spinner spinner = (Spinner) findViewById(R.id.MentorOptionCategory);
        List<String> categories = new ArrayList<String>();
        categories.add("Language");
        categories.add("Science");
        categories.add("Information Technology");
        categories.add("Sports");
        categories.add("Health");


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);

        final TextView skillsMentor = (TextView) findViewById(R.id.skillsMentor);
        final ProposalLab proposalLab = ProposalLab.get(getApplicationContext());
        Button setDate = (Button) findViewById(R.id.setDate);
        Button setTime = (Button) findViewById(R.id.setTime);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        monthOfYear = c.get(Calendar.MONTH);
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        hourOfDay = c.get(Calendar.HOUR_OF_DAY) + 1;
        minute = 0;
        is24HourView = true;
//        final Date date = new Date();
//        setDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
//                        .setOnDateSetListener(MentorOptionsActivity.this)
//                        .setFirstDayOfWeek(Calendar.MONDAY)
//                        .setDoneText("Done")
//                        .setCancelText("Cancel");
//                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
//            }
//        });

        final TextView textViewDateTime = (TextView) findViewById(R.id.textViewDateTime);

        setDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
//                year = c.get(Calendar.YEAR);
//                monthOfYear = c.get(Calendar.MONTH);
//                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MentorOptionsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, monthOfYear, dayOfMonth
                );
                dialog.getDatePicker().setMinDate(c.getTime().getTime());
                c.add(Calendar.DATE, 7);
                dialog.getDatePicker().setMaxDate(c.getTime().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                monthOfYear = i1;
                dayOfMonth = i2;
                flagDate = 1;
                textViewDateTime.setText(dateAndTime());
            }
        };

//        setTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
//                        .setOnTimeSetListener(MentorOptionsActivity.this)
//                        .setDoneText("Done")
//                        .setCancelText("Cancel");
//                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
//            }
//        });

        setTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
//                c.add(Calendar.HOUR_OF_DAY, 1);
//                hourOfDay = c.get(Calendar.HOUR_OF_DAY);
//                minute = 0;

                CustomTimePickerDialog dialog = new CustomTimePickerDialog(
                        MentorOptionsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListener,
                        hourOfDay, minute, is24HourView
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hourOfDay = i;
                minute = i1;
                flagTime = 1;
                textViewDateTime.setText(dateAndTime());
            }
        };

        textViewDateTime.setText(dateAndTime());

//        final Spinner spinnerMinutes = (Spinner) findViewById(R.id.selectMinutes2);
//        List<String> minutes = new ArrayList<String>();
//        minutes.add("15");
//        minutes.add("30");
//        minutes.add("45");
//        minutes.add("60");
//        minutes.add("75");

        final SeekBar seekBarSessionTime = (SeekBar) findViewById(R.id.seekBarSessionTime);
        final TextView textViewSessionTime = (TextView) findViewById(R.id.textViewSessionTime);
        textViewSessionTime.setText(quartersToHHMM(seekBarSessionTime.getProgress()));

        final SeekBar seekBarTimeCap = (SeekBar) findViewById(R.id.seekBarTimeCap);
        seekBarTimeCap.setMax(seekBarSessionTime.getProgress());
        seekBarTimeCap.setProgress(seekBarTimeCap.getMax());
        final TextView textViewTimeCap = (TextView) findViewById(R.id.textViewTimeCap);
        textViewTimeCap.setText(quartersToHHMM(seekBarTimeCap.getProgress()));

        seekBarSessionTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String sessionTime = quartersToHHMM(i);
                textViewSessionTime.setText(sessionTime);

                seekBarTimeCap.setMax(seekBarSessionTime.getProgress());
                seekBarTimeCap.setProgress(Math.min(seekBarTimeCap.getProgress(), seekBarTimeCap.getMax()));
                textViewTimeCap.setText(quartersToHHMM(seekBarTimeCap.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        final ArrayAdapter<String> minutesadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes);
//        spinnerMinutes.setAdapter(minutesadapter);

//        final Spinner spinnerMinutes2 = (Spinner) findViewById(R.id.selectMinutes);
//        List<String> minutes2 = new ArrayList<String>();
//        minutes2.add("15");
//        minutes2.add("30");
//        minutes2.add("45");
//        minutes2.add("60");
//        minutes2.add("75");

        seekBarTimeCap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String timeCap = quartersToHHMM(i);
                textViewTimeCap.setText(timeCap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        final ArrayAdapter<String> minutesadapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes2);
//        spinnerMinutes2.setAdapter(minutesadapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(flagDate*flagTime==1) {
                if(flagTime==1) {
                    String sSkillsMentor = skillsMentor.getText().toString();
                    String sCategoryMentor = spinner.getSelectedItem().toString();
//                    Integer endtime = Integer.parseInt(spinnerMinutes.getSelectedItem().toString());
                    Integer endtime = seekBarSessionTime.getProgress() * 15;
//                    Integer timeCap = Integer.parseInt(spinnerMinutes2.getSelectedItem().toString());
                    Integer timeCap = seekBarTimeCap.getProgress() * 15;
                    Integer endtimeHour = hourOfDay + endtime/60+ (minute + endtime)/60;
                    Integer endtimeMinute = (minute + endtime)%60;
                    Proposal proposal = new Proposal(proposalLab.getProposals().size(), sSkillsMentor);
                    proposal.setCategory(sCategoryMentor);
                    proposal.setDurationCap(timeCap);
                    proposal.setTimeWindow(hourOfDay,minute,endtimeHour,endtimeMinute);
                    proposalLab.AddProposal(proposal);
                    skillsMentor.setText("");
                    Toast.makeText(getApplicationContext(), "Succesfully added", Toast.LENGTH_LONG).show();
                    flagTime=0;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Select Time", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private String dateAndTime()
    {
        String dt = "";
        dt += year + "/";
        if(monthOfYear < 10)
            dt += "0";
        dt += monthOfYear + "/";
        if(dayOfMonth < 10)
            dt += "0";
        dt += dayOfMonth + " ";
        if(hourOfDay < 10)
            dt += "0";
        dt += hourOfDay + ":";
        if(minute < 10)
            dt += 0;
        dt += minute;
        return dt;
    }

    private String quartersToHHMM(int i)
    {
        int hours = i / 4;
        int minutes = i % 4 * 15;
        String hhmm = "";
        if(hours < 10)
            hhmm += "0";
        hhmm += hours + ":";
        if(minutes < 10)
            hhmm += "0";
        hhmm += minutes;
        return hhmm;
    }

//    @Override
//    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
//        this.year=year;
//        this.monthOfYear=monthOfYear;
//        this.dayOfMonth=dayOfMonth;
//        flagDate=1;
//
//
//    }
//    @Override
//    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
//        this.hourOfDay=hourOfDay;
//        this.minute=minute;
//        flagTime=1;
//
//    }

}
