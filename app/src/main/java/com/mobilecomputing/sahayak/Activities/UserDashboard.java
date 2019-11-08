package com.mobilecomputing.sahayak.Activities;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilecomputing.sahayak.Adapters.DashboardAdapter;
import com.mobilecomputing.sahayak.JavaClasses.DashboardModel;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.JavaClasses.SessionLab;
import com.mobilecomputing.sahayak.JavaClasses.UserClass;
import com.mobilecomputing.sahayak.JavaClasses.UserClassDBHelper;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserDashboard extends AppCompatActivity {

    ViewPager viewPager;
    DashboardAdapter adapter;
    List<DashboardModel> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private String url;
    String mentorEmail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            mentorEmail = "";
        } else {
            mentorEmail = extras.getString("Mentor Email");
        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        UserClassDBHelper.get(email);
        models = new ArrayList<>();
        models.add(new DashboardModel(R.drawable.card_learn, "Learn Something", "Tap to learn new skills"));
        models.add(new DashboardModel(R.drawable.card_teach, "Teach Something", "Tap to teach skills"));
        models.add(new DashboardModel(R.drawable.card_mentor_meeting, "Mentor Meetings", "Tap to see scheduled teaching sessions"));
        models.add(new DashboardModel(R.drawable.card_mentee_meeting, "Mentee Meetings", "Tap to see scheduled learning sessions"));
        models.add(new DashboardModel(R.drawable.card_upcoming_meeting, "Upcoming Meetings", "Tap to join a session"));

        adapter = new DashboardAdapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(96, 0, 96, 0);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("URL");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                url = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent signOutIntent = new Intent(this, SignInActivity.class);
            startActivity(signOutIntent);
            finish();
        }
        else if(id == R.id.action_edit_proposal){
            Intent editProposalIntent = new Intent(this, EditProposalActivity.class);
            startActivity(editProposalIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
