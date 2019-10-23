package com.mobilecomputing.sahayak.Activities;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Debug;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import androidx.appcompat.app.AppCompatActivity;

import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.Toast;

public class MentorOptionsActivity extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener,
        RadialTimePickerDialogFragment.OnTimeSetListener {
    CalendarDatePickerDialogFragment calDatePicker;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
    private  int year;
    private  int monthOfYear;
    private  int dayOfMonth;
    private  int hourOfDay;
    private  int minute;
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
//        Button setDate = (Button) findViewById(R.id.setDate);
        Button setTime = (Button) findViewById(R.id.setTime);
        final Date date = new Date();
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
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(MentorOptionsActivity.this)
                        .setDoneText("Done")
                        .setCancelText("Cancel");
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });

        final Spinner spinnerMinutes = (Spinner) findViewById(R.id.selectMinutes2);
        List<String> minutes = new ArrayList<String>();
        minutes.add("15");
        minutes.add("30");
        minutes.add("45");
        minutes.add("60");
        minutes.add("75");

        final ArrayAdapter<String> minutesadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes);
        spinnerMinutes.setAdapter(minutesadapter);

        final Spinner spinnerMinutes2 = (Spinner) findViewById(R.id.selectMinutes);
        List<String> minutes2 = new ArrayList<String>();
        minutes2.add("15");
        minutes2.add("30");
        minutes2.add("45");
        minutes2.add("60");
        minutes2.add("75");

        final ArrayAdapter<String> minutesadapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, minutes2);
        spinnerMinutes2.setAdapter(minutesadapter);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(flagDate*flagTime==1) {
                if(flagTime==1) {
                    String sSkillsMentor = skillsMentor.getText().toString();
                    String sCategoryMentor = spinner.getSelectedItem().toString();
                    Integer endtime = Integer.parseInt(spinnerMinutes.getSelectedItem().toString());
                    Integer timeCap = Integer.parseInt(spinnerMinutes2.getSelectedItem().toString());
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

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        this.year=year;
        this.monthOfYear=monthOfYear;
        this.dayOfMonth=dayOfMonth;
        flagDate=1;


    }
    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        this.hourOfDay=hourOfDay;
        this.minute=minute;
        flagTime=1;

    }

}
