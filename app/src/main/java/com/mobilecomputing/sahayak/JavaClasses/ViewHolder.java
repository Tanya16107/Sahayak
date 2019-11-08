package com.mobilecomputing.sahayak.JavaClasses;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobilecomputing.sahayak.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());
                return true;
            }
        });

    }

    //set details to recycler view row
    public void setDetails(Context ctx, String sTeacher, String sStudent, String sDate,
                           String sDuration, String sSkill){
        //Views
//        Log.d("Date received:", sDate);

        TextView student = mView.findViewById(R.id.student);
        TextView teacher = mView.findViewById(R.id.teacher);
        TextView duration = mView.findViewById(R.id.duration);
        TextView date = mView.findViewById(R.id.interaction_date);
        TextView skill = mView.findViewById(R.id.skill);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        teacher.setText(sTeacher);
        student.setText(sStudent);
        if(email.equals(sTeacher)){
            student.setVisibility(View.VISIBLE);
        }
        else{
            teacher.setVisibility(View.VISIBLE);
        }
        duration.setText(sDuration);
        date.setText(sDate);
        skill.setText(sSkill);
//        Picasso.get().load(image).into(mImageIv);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View  view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}