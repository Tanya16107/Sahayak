package com.mobilecomputing.sahayak.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mobilecomputing.sahayak.R;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Button btn_learn_something = (Button) findViewById(R.id.learn_something);
        Button btn_teach_something = (Button) findViewById(R.id.teach_something);


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
    }
}
