package com.mobilecomputing.sahayak.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        mProposal_description.setText("Description: " + mProposal.getDescription());

        int sh = mProposal.startHour;
        int sm = mProposal.startMin;

        String tmp[] = {"00", "30"};
        RadioButton rb1 = (RadioButton) v.findViewById(R.id.radioButton);
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

        RadioButton rb2 = (RadioButton) v.findViewById(R.id.radioButton2);
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

        Button b = (Button) v.findViewById(R.id.submitButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionLab sl = SessionLab.get(view.getContext());
                Session newSession = new Session(sl.getSessions().size(), mProposal);
                sl.AddSession(newSession);
                proposalShowFragment.this.getActivity().finish();
                Toast.makeText(view.getContext(), "Your session was requested successfully!", Toast.LENGTH_SHORT);
            }
        });

        return v;
    }


}