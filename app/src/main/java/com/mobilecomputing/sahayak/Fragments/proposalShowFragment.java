package com.mobilecomputing.sahayak.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.JavaClasses.SessionLab;
import com.mobilecomputing.sahayak.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class proposalShowFragment extends Fragment {

    public proposalShowFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final int[] numberOfChecks = {0};
        final ArrayList<Date> checkedTimes = new ArrayList<Date>();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_proposal_show, container, false);
        final Button b = (Button) v.findViewById(R.id.submitButton);
        final Proposal mProposal = (Proposal) getArguments().getSerializable("PROPOSAL_INFO");

        LinearLayout sessionsLayout= (LinearLayout) v.findViewById(R.id.sessionsList);

        TextView mProposal_skill = (TextView) v.findViewById(R.id.proposal_skill);
        mProposal_skill.setText(mProposal.getSkill());

        TextView mProposal_rating = (TextView) v.findViewById(R.id.proposal_rating);
        mProposal_rating.setText("Instructor Rating: " + mProposal.getRating());

        TextView mProposal_description = (TextView) v.findViewById(R.id.proposal_description);
        mProposal_description.setText("Category: " + mProposal.getCategory());
        //mProposal_description.setText("Description: " + mProposal.getDescription());

        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(mProposal.getStartDate());

        int quanta = 15;
        int running_sum = 0;
        while(running_sum<mProposal.getDuration()){
            final Date start = startCalendar.getTime();
            startCalendar.add(Calendar.MINUTE, quanta);
            running_sum+=quanta;
            Date end = startCalendar.getTime();

            final CheckBox sessionCheckBox = new CheckBox(getContext());
            sessionCheckBox.setText(dateFormat.format(start)+" - "+dateFormat.format(end));
            sessionsLayout.addView(sessionCheckBox);

            sessionCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        numberOfChecks[0] +=1;
                        checkedTimes.add(start);
                     }
                    else if(!isChecked){
                        numberOfChecks[0] -=1;
                        checkedTimes.remove(start);
                    }
                    if(numberOfChecks[0]<=0){
                        b.setEnabled(false);
                    }
                    else if(numberOfChecks[0]>0){
                        b.setEnabled(true);
                    }
                }
            });

        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionLab sl = SessionLab.get(view.getContext());

                for(int i=0; i<checkedTimes.size(); i++){
                    Session newSession = new Session(sl.getSessions().size(), mProposal);
                    newSession.setInteractionDate(checkedTimes.get(i));
                    sl.AddSession(newSession);
                }
                //Toast.makeText(view.getContext(), "Session for " + newSession.getSkill() + " requested successfully!", Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Session requested successfully!")
                        .setConfirmText("Okay")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                proposalShowFragment.this.getActivity().finish();
                            }
                        })
                        .show();
            }
        });

        //submit only if at least one session is checked
        return v;
    }


}