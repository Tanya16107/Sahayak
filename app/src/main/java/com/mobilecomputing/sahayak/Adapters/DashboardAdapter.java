package com.mobilecomputing.sahayak.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilecomputing.sahayak.Activities.MenteeMeetings;
import com.mobilecomputing.sahayak.Activities.MentorMeetings;
import com.mobilecomputing.sahayak.Activities.MenteeOptionsActivity;
import com.mobilecomputing.sahayak.Activities.SessionActivity;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.Activities.MentorOptionsActivity;
import com.mobilecomputing.sahayak.Activities.UserDashboard;
import com.mobilecomputing.sahayak.JavaClasses.DashboardModel;
import com.mobilecomputing.sahayak.JavaClasses.ProposalLab;
import com.mobilecomputing.sahayak.JavaClasses.SessionLab;
import com.mobilecomputing.sahayak.R;
import java.util.Date;

import java.util.List;

public class DashboardAdapter extends PagerAdapter {

    private String url;
    private List<DashboardModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public DashboardAdapter(List<DashboardModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ProposalLab.get(context);
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
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dashboard_card, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.card_img);
        title = view.findViewById(R.id.card_title);
        desc = view.findViewById(R.id.card_description);
        String titleJunk = models.get(position).getTitle();
        imageView.setImageResource(models.get(position).getImage());
        title.setText(titleJunk);
        desc.setText(models.get(position).getDesc());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleJunk.equals("Learn Something")){
                    Intent intent = new Intent(context, MenteeOptionsActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);
                }
                if(titleJunk.equals("Teach Something")){
                    Intent intent = new Intent(context, MentorOptionsActivity.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);

                }
                else if(titleJunk.equals("Mentor Meetings")){
                    Intent intent = new Intent(context, MentorMeetings.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);
                }
                else if(titleJunk.equals("Mentee Meetings")){
                    Intent intent = new Intent(context, MenteeMeetings.class);
                    //intent.putExtra("param", models.get(position).getTitle());
                    context.startActivity(intent);
                }
               else if(titleJunk.equals("Upcoming Meetings")){
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String email = currentUser.getEmail();
                    String studentFlag = "";
                    SessionLab sl = SessionLab.get(view.getContext());
                    List<Session> sessions = sl.getSessions();
                    int flag = 0;
                    String callID = "";
                    Date current = new Date();
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
                        Toast.makeText(context,"You have no upcoming calls",Toast.LENGTH_LONG).show();
                    }
                    else if(url!=null){
                        Intent intent = new Intent(context,SessionActivity.class);
                        intent.putExtra("Meeting ID",callID);
                        intent.putExtra("URL",url);
                        intent.putExtra("Student",studentFlag);
                        //Toast.makeText(getApplicationContext(),"Redirecting to "+callID,Toast.LENGTH_LONG).show();
                        context.startActivity(intent);
                    }
                }


            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
