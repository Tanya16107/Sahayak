package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProposalLab {
    private static ProposalLab sproposalLab;
    private List<Proposal> mProposals = new ArrayList<>();
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;

    private ProposalLab(final Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference("active_proposals");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProposals.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Proposal p = ds.getValue(Proposal.class);
                        mProposals.add(p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static ProposalLab get(Context context) {
        if (sproposalLab == null) {
            sproposalLab = new ProposalLab(context);
        }
        return sproposalLab;
    }

    public void AddProposal(Proposal proposal) {
        mDatabase = FirebaseDatabase.getInstance().getReference("active_proposals");
        DatabaseReference dref = mDatabase.push();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        proposal.setMentorName(email);
        proposal.setCloudID(dref.getKey());
        // TODO: Handle Duplicates
        dref.setValue(proposal);
    }

    public List<Proposal> getProposals() {
        Log.d("ProposalLab", "Getting " + mProposals.size() + " Proposals " + mProposals.getClass().getSimpleName());
        return mProposals;
    }

    public Proposal getProposal(String cloudID) {
        for (Proposal p : mProposals) {
            if (p.getCloudID().equals(cloudID)) {
                return p;
            }
        }
        return null;
    }
}
