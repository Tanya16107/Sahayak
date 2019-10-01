package com.mobilecomputing.sahayak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobilecomputing.sahayak.R;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_onboarding);

        Button mcontinueButton = (Button) findViewById(R.id.continueButton);

        mcontinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingActivity.this, MenteeOptionsActivity.class);
                //Intent intent = new Intent(OnboardingActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
