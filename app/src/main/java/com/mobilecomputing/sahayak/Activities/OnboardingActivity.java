package com.mobilecomputing.sahayak.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.mobilecomputing.sahayak.R;

public class OnboardingActivity extends AppCompatActivity {

    public static final int RC_ONBOARDING_BACK = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_onboarding);

        Button mcontinueButton = (Button) findViewById(R.id.continueButton);

        mcontinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnboardingActivity.this, SignInActivity.class);
                //Intent intent = new Intent(OnboardingActivity.this, SignInActivity.class);
                startActivityForResult(intent, RC_ONBOARDING_BACK);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_ONBOARDING_BACK && resultCode==RESULT_OK) {
            finish();
        }
    }
}
