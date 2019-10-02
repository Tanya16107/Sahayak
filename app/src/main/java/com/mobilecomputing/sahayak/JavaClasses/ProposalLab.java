package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


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
        for (int i=0; i<100; i++)
        {
            Proposal p=new Proposal(i,"SKILL #"+i);
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
