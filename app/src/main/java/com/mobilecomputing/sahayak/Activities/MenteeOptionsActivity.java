package com.mobilecomputing.sahayak.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilecomputing.sahayak.Adapters.ProposalAdapter;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

import java.util.List;

public class MenteeOptionsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ProposalAdapter mAdapter;
    SearchView searchView;
    public static List<Proposal> proposals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_options);

        RecyclerView recyclerView = findViewById(R.id.rvProposals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProposalLab proposalLab = ProposalLab.get(getApplicationContext());
        proposals = proposalLab.getProposals();

        mAdapter = new ProposalAdapter(this, proposals);
        mAdapter.setClickListener(new ProposalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Proposal p = mAdapter.getItem(position);
                Intent intent = new Intent(MenteeOptionsActivity.this, ProposalInformationActivity.class);
                intent.putExtra("PROPOSAL_INFO", p);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Filter","OK:"+newText);
        mAdapter.filter(newText);
        return false;
    }
}
