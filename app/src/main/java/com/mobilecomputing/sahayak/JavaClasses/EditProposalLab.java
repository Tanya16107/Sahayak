package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class EditProposalLab {
    private static EditProposalLab sproposalLab;
    private List<Proposal> mProposals = new ArrayList<>();
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    DatabaseReference mRef;
    FirebaseDatabase mFirebaseDatabase;
    public static String TAG = "EditProposalLab";

    private EditProposalLab(final Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference("active_proposals");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mProposals.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        Proposal p = ds.getValue(Proposal.class);
                        if(p.getMentorName().equals(currentUser.getEmail())){
                            mProposals.add(p);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static EditProposalLab get(Context context) {
        if (sproposalLab == null) {
            sproposalLab = new EditProposalLab(context);
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

    public void deleteProposalsIndex(Proposal proposalDelete){
        mDatabase = FirebaseDatabase.getInstance().getReference("active_proposals");
        mDatabase.orderByChild("startDate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot proposalSnapshot: dataSnapshot.getChildren()) {
                    Proposal proposal=proposalSnapshot.getValue(Proposal.class);
                    Log.d(TAG,proposal.getCloudID()+"-------"+ proposalDelete.getCloudID());
                    if(proposalDelete.getCloudID().equals(proposal.getCloudID())){
                        Log.d(TAG,"delete");
                        proposalSnapshot.getRef().removeValue();
                    }
                }
            } @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public List<Proposal> getProposals() {
        Log.d("EditProposalLab", "Getting " + mProposals.size() + " Proposals " + mProposals.getClass().getSimpleName());
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
