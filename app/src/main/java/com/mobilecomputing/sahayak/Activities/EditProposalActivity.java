package com.mobilecomputing.sahayak.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.mobilecomputing.sahayak.R;
public class EditProposalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proposal);

        RecyclerView mentorProposalView = (RecyclerView) findViewById(R.id.edit_proposal);
        mentorProposalView.setLayoutManager(new LinearLayoutManager(this));


    }
}
