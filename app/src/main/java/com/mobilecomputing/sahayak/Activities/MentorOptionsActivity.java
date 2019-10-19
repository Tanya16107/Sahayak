package com.mobilecomputing.sahayak.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.R;

import java.util.ArrayList;
import java.util.List;

public class MentorOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_options);
        final Spinner spinner = (Spinner) findViewById(R.id.MentorOptionCategory);
        List<String> categories = new ArrayList<String>();
        categories.add("Language");
        categories.add("Science");
        categories.add("Information Technology");
        categories.add("Sports");
        categories.add("Health");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        Button addButton = (Button) findViewById(R.id.addButton);
        final TextView skillsMentor = (TextView) findViewById(R.id.skillsMentor);
        final TextView timeCapMentor = (TextView) findViewById(R.id.timeCapMentor);
        spinner.setAdapter(dataAdapter);
        final ProposalLab proposalLab = ProposalLab.get(getApplicationContext());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSkillsMentor = skillsMentor.getText().toString();
                String sCategoryMentor = spinner.getSelectedItem().toString();
                Integer ntimeCapMentor = Integer.parseInt(timeCapMentor.getText().toString());
                Proposal proposal = new Proposal(proposalLab.getProposals().size(), sSkillsMentor);
                proposal.setCategory(sCategoryMentor);
                proposal.setDurationCap(ntimeCapMentor);
                proposalLab.AddProposal(proposal);
                skillsMentor.setText("");
                timeCapMentor.setText("");

            }
        });
    }

}
