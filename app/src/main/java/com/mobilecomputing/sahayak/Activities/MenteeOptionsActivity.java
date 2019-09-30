package com.mobilecomputing.sahayak.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobilecomputing.sahayak.Adapters.ProposalAdapter;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

import java.util.List;

public class MenteeOptionsActivity extends AppCompatActivity {

    ProposalAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_options);

        RecyclerView recyclerView = findViewById(R.id.rvProposals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProposalLab proposalLab = ProposalLab.get(getApplicationContext());
        List<Proposal> proposals = proposalLab.getProposals();

        mAdapter = new ProposalAdapter(this, proposals);
        mAdapter.setClickListener(new ProposalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Proposal p=mAdapter.getItem(position);
                Intent intent=new Intent(MenteeOptionsActivity.this,ProposalInformationActivity.class);
                intent.putExtra("PROPOSAL_INFO",p);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
