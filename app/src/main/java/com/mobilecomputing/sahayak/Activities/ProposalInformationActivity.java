package com.mobilecomputing.sahayak.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mobilecomputing.sahayak.Fragments.proposalShowFragment;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.R;

public class ProposalInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_information);

        FragmentManager fm = getSupportFragmentManager();

        Proposal mProposal = (Proposal) getIntent().getSerializableExtra("PROPOSAL_INFO");
        Log.d("MYCODE", mProposal.getSkill() + "uno");
        Bundle bundle = new Bundle();
        bundle.putSerializable("PROPOSAL_INFO", mProposal);

        Fragment fragObj = new proposalShowFragment();
        fragObj.setArguments(bundle);
        fm.beginTransaction().replace(R.id.fragment, fragObj).commit();
    }
}
