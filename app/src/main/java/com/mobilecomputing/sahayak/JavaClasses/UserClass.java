package com.mobilecomputing.sahayak.JavaClasses;

public class UserClass {
    String email;
    float rating;
    int totalSessions;

    public UserClass(){
        //Required empty constructor
    }
    public UserClass(String e, float r, int t)
    {
        email=e;
        rating=r;
        totalSessions=t;
    }

    public String getEmail() { return email; }

    public void setEmail(String e) { this.email=e; }

    public float getRating() { return rating; }

    public void setRating(float r) { this.rating=r; }

    public int getTotalSessions() { return totalSessions; }

    public void setTotalSessions(int t) { this.totalSessions=t; }

    public void updateRating(int r)
    {
        this.rating=(this.rating*this.totalSessions+r)/(this.totalSessions+1);
        this.totalSessions=this.totalSessions+1;
    }
}
