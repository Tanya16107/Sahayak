package com.mobilecomputing.sahayak.Activities;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.JavaClasses.SessionLab;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDashboard extends AppCompatActivity {
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        CardView btn_learn_something = (CardView) findViewById(R.id.learn_something);
        CardView btn_teach_something = (CardView) findViewById(R.id.teach_something);
        Button btn_view_teacher_meetings = (Button) findViewById(R.id.teacher_meeting_btn);
        Button btn_view_student_meetings = (Button) findViewById(R.id.student_meeting_btn);
        Button btn_view_go_to_current_meeting = (Button) findViewById(R.id.current_meeting_btn);
        ProposalLab.get(this);
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
        btn_learn_something.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student_dashboard_intent = new Intent(UserDashboard.this, MenteeOptionsActivity.class);
                startActivity(student_dashboard_intent);
            }
        });
        btn_teach_something.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student_dashboard_intent = new Intent(UserDashboard.this, MentorOptionsActivity.class);
                startActivity(student_dashboard_intent);
            }
        });

        btn_view_teacher_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student_dashboard_intent = new Intent(UserDashboard.this, MentorMeetings.class);
                startActivity(student_dashboard_intent);
            }
        });

        btn_view_student_meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent student_dashboard_intent = new Intent(UserDashboard.this, MenteeMeetings.class);
                startActivity(student_dashboard_intent);
            }
        });

        btn_view_go_to_current_meeting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String email = currentUser.getEmail();
                SessionLab sl = SessionLab.get(view.getContext());
                List<Session> sessions = sl.getSessions();
                int flag = 0;
                String callID = "";
                Date current = new Date();
                for(int i=0;i<sessions.size();i++){
                    Date scheduled = sessions.get(i).getInteractionDate();
                    //Log.d("69969", "onClick: "+sessions.get(i).getStudent()+sessions.get(i).getTeacher()+scheduled);
                    if(sessions.get(i).getTeacher().equals(email) || sessions.get(i).getStudent().equals(email)){
                        //Log.d("69969", "onClick: "+(scheduled.getTime()-current.getTime()));
                        if(scheduled.getTime()-current.getTime()<=300000 && current.getTime()-scheduled.getTime()<=900000){
                            flag=1;
                            callID=sessions.get(i).getCloudID();
                            break;
                        }
                    }
                }
                if(flag==0){
                    Toast.makeText(getApplicationContext(),"You have no upcoming calls",Toast.LENGTH_LONG).show();
                }
                else if(url!=null){
                    Intent intent = new Intent(view.getContext(),SessionActivity.class);
                    intent.putExtra("Meeting ID",callID);
                    intent.putExtra("URL",url);
                    //Toast.makeText(getApplicationContext(),"Redirecting to "+callID,Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        } );

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
