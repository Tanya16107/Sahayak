package com.mobilecomputing.sahayak.JavaClasses;

import java.io.Serializable;

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
    private int durationCap;

    public Proposal(int i, String s) {
        ID = i;
        skill = s;
    }

    public int getID() {
        return ID;
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

    public void setTimeWindow(int sh, int sm, int eh, int em) {
        startHour = sh;
        startMin = sm;
        endHour = eh;
        endMin = em;
    }

    public String getTimeWindow() {

        String S = "";
        String tmp[] = {"00", "30"};
        if (startHour < 10) {
            S += "0" + startHour + "" + tmp[startMin];
        } else {
            S += startHour + "" + tmp[startMin];
        }

        S += "-";
        if (endHour < 10) {
            S += "0" + endHour + "" + tmp[endMin];
        } else {
            S += endHour + "" + tmp[endMin];
        }
        return S;
    }
}
