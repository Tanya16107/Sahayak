package com.mobilecomputing.sahayak.JavaClasses;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Proposal implements Serializable {

    private String cloudID;
    private String category;
    private String skill;
    private Date startDate;
    private int duration;
    private int durationCap;
    private String mentorName;
    private float rating;

    public String getCloudID() {
        return cloudID;
    }

    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDurationCap() {
        return durationCap;
    }

    public void setDurationCap(int durationCap) {
        this.durationCap = durationCap;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Proposal() {
        // required empty constructor
    }

    public Proposal(String category, String skill, Date startDate, int duration, int durationCap) {
        this.category = category;
        this.skill = skill;
        this.startDate = startDate;
        this.duration = duration;
        this.durationCap = durationCap;

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Log.d("Calendar", Integer.toString(startCalendar.get(Calendar.YEAR)));
    }

    public String getTimeWindow() {

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(startDate);
        endCalendar.add(Calendar.MINUTE, duration);

        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startMin = startCalendar.get(Calendar.MINUTE);
        int endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        int endMin = endCalendar.get(Calendar.MINUTE);

        String S = "";
        if(startHour < 10)
            S += "0";
        S += startHour + ":";
        if(startMin < 10)
            S += "0";
        S += startMin + "-";
        if(endHour < 10)
            S += "0";
        S += endHour + ":";
        if(endMin < 10)
            S += "0";
        S += endMin;
        return S;
    }
    public boolean equals(Object obj)
    {

        // checking if both the object references are
        // referring to the same object.
        if(this == obj)
            return true;

        // it checks if the argument is of the
        // type Geek by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Geek)) return false; ---> avoid.
        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        // type casting of the argument.
        Proposal geek = (Proposal) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        return (geek.cloudID == this.cloudID);
    }
}
