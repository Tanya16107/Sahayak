package com.mobilecomputing.sahayak.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobilecomputing.sahayak.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        TextView btn_signIn = (TextView) findViewById(R.id.textView_googleIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userDashboardIntent = new Intent(SignInActivity.this, UserDashboard.class);
                startActivity(userDashboardIntent);
            }
        });
    }
}
