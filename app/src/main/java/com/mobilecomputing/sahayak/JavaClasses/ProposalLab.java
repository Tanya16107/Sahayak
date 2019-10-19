package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;
import android.util.Log;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;


public class ProposalLab {
    private static ProposalLab sproposalLab;
    private List<Proposal> mProposals;
    private DatabaseReference mDatabase;

    public static ProposalLab get(Context context)
    {
        if(sproposalLab==null)
        {
            sproposalLab=new ProposalLab(context);
        }
        return sproposalLab;
    }

    private ProposalLab(Context context)
    {
        mProposals = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("active_proposals");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
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

    public void AddProposal(Proposal proposal){
        mProposals.add(proposal);
        mDatabase = FirebaseDatabase.getInstance().getReference("active_proposals");
        // TODO: Handle Duplicates
        mDatabase.push().setValue(proposal);
    }

    public List<Proposal> getProposals()
    {
        Log.d("ProposalLab", "Getting "+ mProposals.size() +" Proposals "+mProposals.getClass().getSimpleName());
        for (Object p: mProposals) {
            Log.d("ProposalLab", "Proposal for skill " + p.getClass().getSimpleName());
        }
        return mProposals;
    }

    public Proposal getProposal(int id) {
        for (Proposal p : mProposals) {
            if (p.getID()==id) {
                return p;
            }
        }
        return null;
    }
}
