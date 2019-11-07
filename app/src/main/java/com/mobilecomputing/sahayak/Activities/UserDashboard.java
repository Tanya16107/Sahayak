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

import com.codemybrainsout.ratingdialog.RatingDialog;
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
import com.mobilecomputing.sahayak.JavaClasses.UserClass;
import com.mobilecomputing.sahayak.JavaClasses.UserClassDBHelper;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UserDashboard extends AppCompatActivity {
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Bundle extras = getIntent().getExtras();
        String mentorEmail="";
        if(extras == null) {
            mentorEmail = "";
        } else {
            mentorEmail = extras.getString("Mentor Email");
        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = currentUser.getEmail();
        CardView btn_learn_something = (CardView) findViewById(R.id.learn_something);
        CardView btn_teach_something = (CardView) findViewById(R.id.teach_something);
        Button btn_view_teacher_meetings = (Button) findViewById(R.id.teacher_meeting_btn);
        Button btn_view_student_meetings = (Button) findViewById(R.id.student_meeting_btn);
        Button btn_view_go_to_current_meeting = (Button) findViewById(R.id.current_meeting_btn);

        ProposalLab.get(this);
        String em=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        UserClass u=UserClassDBHelper.get(em);

//        Random random= new Random();
//        int r=random.nextInt(6);
//        u.updateRating(r);
//        Log.d("MUI_branch","Before Update:"+r+" "+u.getRating()+" "+u.getTotalSessions());
//        UserClassDBHelper.updateUser(em,r);

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
        if(!email.equals(mentorEmail) && !mentorEmail.equals("")) {
            final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                    .title("How would you rate the mentor?")
                    .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                        @Override
                        public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                            //do something
                            ratingDialog.dismiss();
                        }
                    })
                    .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                        @Override
                        public void onFormSubmitted(String feedback) {
                        }
                    })
                    .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                        @Override
                        public void onRatingSelected(float rating, boolean thresholdCleared) {
                            Toast.makeText(getApplicationContext(), Float.toString(rating), Toast.LENGTH_LONG).show();
                        }
                    }).build();

            ratingDialog.show();
        }
        btn_view_go_to_current_meeting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SessionLab sl = SessionLab.get(view.getContext());
                List<Session> sessions = sl.getSessions();
                int flag = 0;
                String callID = "";
                Date current = new Date();
                String studentFlag = "";
                for(int i=0;i<sessions.size();i++){
                    Date scheduled = sessions.get(i).getInteractionDate();
                    //Log.d("69969", "onClick: "+sessions.get(i).getStudent()+sessions.get(i).getTeacher()+scheduled);
                    if(sessions.get(i).getTeacher().equals(email) || sessions.get(i).getStudent().equals(email)){
                        //Log.d("69969", "onClick: "+(scheduled.getTime()-current.getTime()));
                        if(scheduled.getTime()-current.getTime()<=600000 && current.getTime()-scheduled.getTime()<=sessions.get(i).getDuration()*60000){
                            flag=1;
                            callID=sessions.get(i).getCloudID();
                            studentFlag = sessions.get(i).getTeacher();
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
                    intent.putExtra("Student",studentFlag);
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
        return super.onOptionsItemSelected(item);
    }
}
