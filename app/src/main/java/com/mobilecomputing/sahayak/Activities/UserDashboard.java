package com.mobilecomputing.sahayak.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        CardView btn_learn_something = (CardView) findViewById(R.id.learn_something);
        CardView btn_teach_something = (CardView) findViewById(R.id.teach_something);
        Button btn_view_teacher_meetings = (Button) findViewById(R.id.teacher_meeting_btn);
        Button btn_view_student_meetings = (Button) findViewById(R.id.student_meeting_btn);
        ProposalLab.get(this);

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



    }
}
