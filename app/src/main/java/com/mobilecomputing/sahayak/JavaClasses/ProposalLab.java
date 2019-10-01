package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class ProposalLab {
    private static ProposalLab sproposalLab;
    private List<Proposal> mProposals;

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
