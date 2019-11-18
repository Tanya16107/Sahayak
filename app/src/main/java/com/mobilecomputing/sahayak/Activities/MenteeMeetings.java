package com.mobilecomputing.sahayak.Activities;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.MenuItemCompat;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.JavaClasses.ViewHolder;
import com.mobilecomputing.sahayak.R;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MenteeMeetings extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    SharedPreferences mSharedPref;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    Format formatter = new SimpleDateFormat("EEEE, MMM d");
    SimpleDateFormat formatterDuration = new SimpleDateFormat("h:mm a");
    String date_string;
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        ActionBar actionBar = getSupportActionBar();
        mSharedPref = getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = mSharedPref.getString("Sort", "newest");
        if (mSorting.equals("newest")) {
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        } else if (mSorting.equals("oldest")) {
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("all_sessions");
        Log.d("here:",mRef.getKey());
    }

    private void firebaseSearch(String searchText) {

        String query = searchText.toLowerCase();
               Query firebaseSearchQuery = mRef.orderByChild("student").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerAdapter<Session, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Session, ViewHolder>(
                        Session.class,
                        R.layout.row,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Session session, int position) {
                        Date start = session.getInteractionDate();
                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.setTime(start);
                        startCalendar.add(Calendar.MINUTE, session.getDuration());
                        Date end = startCalendar.getTime();

                        String duration = formatterDuration.format(start)+" - "+formatterDuration.format(end);

                        viewHolder.setDetails(getApplicationContext(), session.getTeacher(), session.getStudent(),
                                formatter.format(start), duration, session.getSkill()
                        );
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });

                        return viewHolder;
                    }

                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        String query = currentUser;
        Query firebaseSearchQuery = mRef.orderByChild("student").startAt(query).endAt(query + "\uf8ff");

        FirebaseRecyclerAdapter<Session, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Session, ViewHolder>(
                        Session.class,
                        R.layout.row,
                        ViewHolder.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Session session, int position) {
                        Date start = session.getInteractionDate();
                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.setTime(start);
                        startCalendar.add(Calendar.MINUTE, session.getDuration());
                        Date end = startCalendar.getTime();

                        String duration = formatterDuration.format(start)+" - "+formatterDuration.format(end);

                        viewHolder.setDetails(getApplicationContext(), session.getTeacher(), session.getStudent(),
                                formatter.format(start), duration, session.getSkill()
                        );
                    }

                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                        ViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);

                        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                            }
                        });

                        return viewHolder;
                    }

                };

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            showSortDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        String[] sortOptions = {" Newest", " Oldest"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort", "newest");
                            editor.apply();
                            recreate();
                        } else if (which == 1) {
                            {
                                SharedPreferences.Editor editor = mSharedPref.edit();
                                editor.putString("Sort", "oldest");
                                editor.apply();
                                recreate();
                            }
                        }
                    }
                });
        builder.show();
    }

}