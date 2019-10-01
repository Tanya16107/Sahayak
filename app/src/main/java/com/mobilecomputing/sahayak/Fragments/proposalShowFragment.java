package com.mobilecomputing.sahayak.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobilecomputing.sahayak.JavaClasses.Proposal;
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
        TextView T=(TextView)v.findViewById(R.id.skill_name);
        Proposal mProposal=(Proposal) getArguments().getSerializable("PROPOSAL_INFO");
        T.setText(mProposal.getSkill());
        return v;
    }


}