package com.mobilecomputing.sahayak.JavaClasses;

public class Proposal
{
    int ID;
    private String mentorName;
    private int mentorRank;
    private String category;
    private String skill;
    private int timeWindow;
    private int durationCap;

    public Proposal(int i,String s)
    {
        ID=i;
        skill=s;
    }

    public int getID()
    {
        return ID;
    }

    public String getSkill()
    {
        return skill;
    }
}
