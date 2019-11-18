package com.mobilecomputing.sahayak.JavaClasses;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.mobilecomputing.sahayak.Fragments.proposalShowFragment.TAG;

public class UserClassDBHelper {

    private static UserClass u=new UserClass();
    private static DatabaseReference mDatabase;

    public static UserClass get(String e){
        Query q = FirebaseDatabase.getInstance().getReference("user_class").orderByChild("email").equalTo(e);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Log.d("MUI", "User Found");
                        u = ds.getValue(UserClass.class);
                    }
                }
                else
                {
                    Log.d("MUI_branch","new user added");
                    u = new UserClass(e,3,0);
                    addUser(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return u;
    }

    public static void addUser(UserClass u)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("user_class");
        DatabaseReference dref = mDatabase.push();
        dref.setValue(u);
    }

    public static void updateUser(String em,int r)
    {
        Query q = FirebaseDatabase.getInstance().getReference("user_class").orderByChild("email").equalTo(em);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String key;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        u = ds.getValue(UserClass.class);
                        key = ds.getKey();
                        DatabaseReference dref=FirebaseDatabase.getInstance().getReference("user_class/"+key);
                        u.updateRating(r);
                        Log.d("MUI_branch","After Update:"+r+" "+u.getRating()+" "+u.getTotalSessions());
                        dref.setValue(u);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
