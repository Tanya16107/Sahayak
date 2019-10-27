package com.mobilecomputing.sahayak.JavaClasses;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Proposal implements Serializable {
    public int startHour;
    public int startMin;
    public int endHour;
    public int endMin;
    int ID;
    private String mentorName;
    private int mentorRating;
    private String category;
    private String skill;
    private String description;
    private String cloudID;
    private int durationCap;

    public Proposal() {
        // required empty constructor
    }

    public Proposal(int i, String s) {
        ID = i;
        skill = s;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDurationCap() {
        return durationCap;
    }

    public void setDurationCap(int durationCap) {
        this.durationCap = durationCap;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setMentorRating(int r) {
        mentorRating = r;
    }

    public int getRating() {
        return mentorRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    public String getCloudID() {
        return cloudID;
    }

    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    public void setTimeWindow(int sh, int sm, int eh, int em) {
        startHour = sh;
        startMin = sm;
        endHour = eh;
        endMin = em;
    }

    public String getTimeWindow() {

        String S = "";
        String tmp[] = {"00", "30"};
        if(startMin<30){
            startMin=0;
        }
        else {
            startMin=1;
        }

        if (startHour < 10) {
            S += "0" + startHour + "" + tmp[startMin];
        } else {
            S += startHour + "" + tmp[startMin];
        }

        S += "-";
        if (endHour < 10) {
            S += "0" + endHour + "" + endMin;
        } else {
            S += endHour + "" + endMin;
        }
        return S;
    }
}
