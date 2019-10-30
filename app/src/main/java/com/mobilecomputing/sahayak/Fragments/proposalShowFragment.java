package com.mobilecomputing.sahayak.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.JavaClasses.SessionLab;
import com.mobilecomputing.sahayak.R;

import java.util.Calendar;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_proposal_show, container, false);
        final Proposal mProposal = (Proposal) getArguments().getSerializable("PROPOSAL_INFO");

        TextView mProposal_skill = (TextView) v.findViewById(R.id.proposal_skill);
        mProposal_skill.setText(mProposal.getSkill());

        TextView mProposal_rating = (TextView) v.findViewById(R.id.proposal_rating);
        mProposal_rating.setText("Instructor Rating: " + mProposal.getRating());

        TextView mProposal_description = (TextView) v.findViewById(R.id.proposal_description);
        mProposal_description.setText("Category: " + mProposal.getCategory());
        //mProposal_description.setText("Description: " + mProposal.getDescription());

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(mProposal.getStartDate());
        int sh = startCalendar.HOUR_OF_DAY;
        int sm = startCalendar.MINUTE;
        if(sm<30){
            sm=0;
        }
        else{
            sm=1;
        }

        String tmp[] = {"00", "30"};
        final CheckBox rb1 = (CheckBox) v.findViewById(R.id.radioButton);
        String xyz = "";

        // TODO: Use proper variable names so that purpose of code is clear
        if (sm == 1) {
            xyz = "";
            if (sh < 10) {
                xyz = "0";
            }
            xyz += sh + "" + tmp[sm] + "-";
            if (sh + 1 < 10) {
                xyz += "0";
            }
            xyz += (sh + 1) + "" + tmp[sm - 1];
            rb1.setText(xyz);
        } else {
            xyz = "";
            if (sh < 10) {
                xyz = "0";
            }
            xyz += sh + "" + tmp[sm] + "-" + sh + "" + tmp[sm + 1];
            rb1.setText(xyz);
        }

        final CheckBox rb2 = (CheckBox) v.findViewById(R.id.radioButton2);
        if (sm == 1) {
            xyz = "";
            if (sh + 1 < 10) {
                xyz += "0" + (sh + 1) + "" + tmp[sm - 1] + "-0" + (sh + 1) + tmp[sm];
            } else {
                xyz += (sh + 1) + "" + tmp[sm - 1] + "-" + (sh + 1) + tmp[sm];
            }

            rb2.setText(xyz);
        } else {
            xyz = "";
            if (sh < 10) {
                xyz = "0";
            }
            xyz += sh + "" + tmp[sm + 1] + "-";
            if (sh + 1 < 10) {
                xyz += "0";
            }
            xyz += (sh + 1) + "" + tmp[sm];
            rb2.setText(xyz);
        }

        final Button b = (Button) v.findViewById(R.id.submitButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionLab sl = SessionLab.get(view.getContext());
                Session newSession = new Session(sl.getSessions().size(), mProposal);
                sl.AddSession(newSession);
                Toast.makeText(view.getContext(), "Session for " + newSession.getSkill() + " requested successfully!", Toast.LENGTH_SHORT).show();
                proposalShowFragment.this.getActivity().finish();
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb1.isChecked() || rb2.isChecked())
                {
                    b.setEnabled(true);
                }
                else
                {
                    b.setEnabled(false);
                }
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb1.isChecked() || rb2.isChecked())
                {
                    b.setEnabled(true);
                }
                else
                {
                    b.setEnabled(false);
                }
            }
        });

        return v;
    }


}