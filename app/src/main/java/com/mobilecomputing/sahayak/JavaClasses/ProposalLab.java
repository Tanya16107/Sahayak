package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mProposals=new ArrayList<>();
        Random rand=new Random();



        for (int i=0; i<20; i++)
        {
            Proposal p=new Proposal(i,"Skill "+UUID.randomUUID().toString().split("-")[0]);
            p.setMentorRating(rand.nextInt(6));
            p.setDescription(UUID.randomUUID().toString().replace("-"," ")+" "+UUID.randomUUID().toString().replace("-"," ")+" "+
                    UUID.randomUUID().toString().replace("-"," "));

            int hour=rand.nextInt(22);
            int min=rand.nextInt(2);
            p.setTimeWindow(hour,min,hour+1,min);
            mProposals.add(p);
        }
    }
    public void AddProposal(Proposal proposal){
        mProposals.add(proposal);
    }

    public List<Proposal> getProposals()
    {
        return mProposals;
    }

    public Proposal getCrime(int id) {
        for (Proposal p : mProposals) {
            if (p.getID()==id) {
                return p;
            }
        }
        return null;
    }
}
