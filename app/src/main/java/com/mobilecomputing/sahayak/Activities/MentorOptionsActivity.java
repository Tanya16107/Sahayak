package com.mobilecomputing.sahayak.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.mobilecomputing.sahayak.Fragments.proposalShowFragment;
import com.mobilecomputing.sahayak.JavaClasses.CustomTimePickerDialog;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.UserClass;
import com.mobilecomputing.sahayak.JavaClasses.UserClassDBHelper;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.TimePicker;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MentorOptionsActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private CustomTimePickerDialog.OnTimeSetListener mTimeSetListener;
    private int mHourOfDay;
    private int mMinute;
    private boolean mIs24HourView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_options);

        UserClass u= UserClassDBHelper.get(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        final Spinner spinner = (Spinner) findViewById(R.id.MentorOptionCategory);
        List<String> categories = new ArrayList<String>();
        categories.add("Language");
        categories.add("Science");
        categories.add("Technology");
        categories.add("Sports");
        categories.add("Health");
        categories.add("Music");
        categories.add("Art");
        categories.add("Other");

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);

        final TextView skillsMentor = (TextView) findViewById(R.id.skillsMentor);
        final ProposalLab proposalLab = ProposalLab.get(getApplicationContext());

        Button setDate = (Button) findViewById(R.id.setDate);
        Button setTime = (Button) findViewById(R.id.setTime);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        mHourOfDay = c.get(Calendar.HOUR_OF_DAY) + 1;
        mMinute = 0;
        mIs24HourView = true;

        final TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate.setText("");
        final TextView textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime.setText("");

        setDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        MentorOptionsActivity.this,
                        android.R.style.Theme_Material_Light_Dialog_MinWidth,
                        mDateSetListener,
                        mYear, mMonth, mDayOfMonth
                );
                dialog.getDatePicker().setMinDate(c.getTime().getTime());
                c.add(Calendar.DATE, 7);
                dialog.getDatePicker().setMaxDate(c.getTime().getTime());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear = i;
                mMonth = i1;
                mDayOfMonth = i2;
                textViewDate.setText(dateAndTime().split(" ")[0]);
            }
        };

        setTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CustomTimePickerDialog dialog = new CustomTimePickerDialog(
                        MentorOptionsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        mHourOfDay, mMinute, mIs24HourView
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mHourOfDay = i;
                mMinute = i1;
                textViewTime.setText(dateAndTime().split(" ")[1]);
            }

        };

        textViewDate.setText(dateAndTime().split(" ")[0]);
        textViewTime.setText(dateAndTime().split(" ")[1]);


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

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.set(mYear, mMonth, mDayOfMonth, mHourOfDay, mMinute);
                Calendar hourLaterCalendar = Calendar.getInstance();
                //hourLaterCalendar.add(Calendar.HOUR_OF_DAY, 1);

                String category = spinner.getSelectedItem().toString();
                String skill = skillsMentor.getText().toString();
                Date startDate = startCalendar.getTime();
                int duration = seekBarSessionTime.getProgress() * 15;
                int durationCap = seekBarTimeCap.getProgress() * 15;

                if(category.isEmpty() || skill.isEmpty() || startCalendar == null || duration == 0 || durationCap == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
                }
//                else if(startCalendar.compareTo(hourLaterCalendar) < 0)
//                {
//                    Toast.makeText(getApplicationContext(), "Start time must be at least one hour from now", Toast.LENGTH_LONG).show();
//                }
                else
                {
                    Proposal proposal = new Proposal(category, skill, startDate, duration, durationCap);
                    proposal.setRating(u.getRating());
                    proposalLab.AddProposal(proposal);
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Session added successfully!")
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(view.getContext(), UserDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private String dateAndTime()
    {
        String dt = "";
        dt += mYear + "/";
        if(mMonth < 10)
            dt += "0";
        dt += mMonth + "/";
        if(mDayOfMonth < 10)
            dt += "0";
        dt += mDayOfMonth + " ";
        if(mHourOfDay < 10)
            dt += "0";
        dt += mHourOfDay + ":";
        if(mMinute < 10)
            dt += 0;
        dt += mMinute;
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
}
