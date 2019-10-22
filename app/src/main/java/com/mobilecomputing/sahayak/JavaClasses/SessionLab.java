package com.mobilecomputing.sahayak.JavaClasses;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SessionLab {
    private static SessionLab ssessionLab;
    private List<Session> mSessions = new ArrayList<>();
    private DatabaseReference mDatabase;

    private SessionLab(Context context) {
        mDatabase = FirebaseDatabase.getInstance().getReference("all_sessions");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSessions.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Session p = ds.getValue(Session.class);
                        mSessions.add(p);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static SessionLab get(Context context) {
        if (ssessionLab == null) {
            ssessionLab = new SessionLab(context);
        }
        return ssessionLab;
    }

    public void AddSession(Session nsession) {
        mDatabase = FirebaseDatabase.getInstance().getReference("all_sessions");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        nsession.setStudent(email);
        // TODO: Handle Duplicates
        mDatabase.push().setValue(nsession);
    }

    public List<Session> getSessions() {
        Log.d("SessionLab", "Getting " + mSessions.size() + " Sessions " + mSessions.getClass().getSimpleName());
        return mSessions;
    }

    public Session getSession(int id) {
        for (Session p : mSessions) {
            if (p.getID() == id) {
                return p;
            }
        }
        return null;
    }
}
