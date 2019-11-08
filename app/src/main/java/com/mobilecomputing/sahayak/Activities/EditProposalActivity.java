package com.mobilecomputing.sahayak.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobilecomputing.sahayak.Adapters.EditProposalAdapter;
import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.EditProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.SwipeToDelete;
import com.mobilecomputing.sahayak.R;

import java.util.List;

import static com.mobilecomputing.sahayak.Fragments.proposalShowFragment.TAG;

public class EditProposalActivity extends AppCompatActivity {
    public static List<Proposal> proposals;
    EditProposalAdapter mAdapter;
    SearchView searchView;
    RecyclerView mentorProposalView;
    EditProposalLab proposalLab;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proposal);
        view = findViewById(R.id.edit_proposal);
        LinearLayoutManager linearLayoutManager;

        mentorProposalView = (RecyclerView) findViewById(R.id.edit_proposal);
        mentorProposalView.setLayoutManager(new LinearLayoutManager(this));
        linearLayoutManager = new LinearLayoutManager(this);
        proposalLab = EditProposalLab.get(getApplicationContext());
        proposals = proposalLab.getProposals();

        mAdapter = new EditProposalAdapter(this, proposals,proposalLab);
        mAdapter.setClickListener(new EditProposalAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Proposal p = mAdapter.getItem(position);
                Intent intent = new Intent(EditProposalActivity.this, IndividualProposalActivity.class);
                startActivity(intent);
            }
        });
        mentorProposalView.setAdapter(mAdapter);
        enableSwipeToDeleteAndUndo();
    }
    public void enableSwipeToDeleteAndUndo(){
        SwipeToDelete swipeToDeleteCallback = new SwipeToDelete(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Proposal item = mAdapter.getItem(position);
                Log.d(TAG,"Delete Position" + Integer.toString(position));
                mAdapter.removeProposal(position);


                Snackbar snackbar = Snackbar
                        .make(view, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        mentorProposalView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mentorProposalView);
    }


}
