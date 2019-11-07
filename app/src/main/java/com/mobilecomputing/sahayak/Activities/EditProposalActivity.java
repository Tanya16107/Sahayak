package com.mobilecomputing.sahayak.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobilecomputing.sahayak.Adapters.ProposalAdapter;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

import java.util.List;

public class EditProposalActivity extends AppCompatActivity {
    public static List<Proposal> proposals;
    ProposalAdapter mAdapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proposal);

        RecyclerView mentorProposalView = (RecyclerView) findViewById(R.id.edit_proposal);
        mentorProposalView.setLayoutManager(new LinearLayoutManager(this));
        ProposalLab proposalLab = ProposalLab.get(getApplicationContext());
        proposals = proposalLab.getProposals();

        mAdapter = new ProposalAdapter(this, proposals);
        mAdapter.setClickListener(new ProposalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Proposal p = mAdapter.getItem(position);
                Intent intent = new Intent(EditProposalActivity.this, IndividualProposalActivity.class);
                startActivity(intent);
            }
        });
        mentorProposalView.setAdapter(mAdapter);
    }


}
