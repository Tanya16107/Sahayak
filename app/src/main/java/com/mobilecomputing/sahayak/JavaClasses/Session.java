package com.mobilecomputing.sahayak.JavaClasses;


import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {
    int ID;
    String cloudID;
    String teacher;  // mentor email
    String student;  // mentee email
    Date interactionDate;  // the day on which this session took place
    int duration;  // in minutes
    String skill;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public Date getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(Date interactionDate) {
        this.interactionDate = interactionDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCloudID() {
        return cloudID;
    }

    public void setCloudID(String cloudID) {
        this.cloudID = cloudID;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Session(){
        // required empty constructor
    }

    public Session(int ID, Proposal p){
        // Initialise session from a proposal
        this.setID(ID);
        this.setTeacher(p.getMentorName());
        this.setSkill(p.getSkill());
        this.setDuration(15);
    }

}
