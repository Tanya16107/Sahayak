package com.mobilecomputing.sahayak.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mobilecomputing.sahayak.JavaClasses.Proposal;
import com.mobilecomputing.sahayak.JavaClasses.Session;
import com.mobilecomputing.sahayak.JavaClasses.SessionLab;
import com.mobilecomputing.sahayak.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class proposalShowFragment extends Fragment {

    public static final String TAG = "PROPOSAL-FRAGMENT";

    public proposalShowFragment() {
        // Required empty public constructor
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final int[] numberOfChecks = {0};
        final int[] durationTracker = {0};
        final ArrayList<Date> checkedTimes = new ArrayList<Date>();
        final ArrayList<Boolean> taken = new ArrayList<Boolean>();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_proposal_show, container, false);
        final Button b = (Button) v.findViewById(R.id.submitButton);
        final Proposal mProposal = (Proposal) getArguments().getSerializable("PROPOSAL_INFO");

        final int durationCap = mProposal.getDurationCap();

        final LinearLayout sessionsLayout = (LinearLayout) v.findViewById(R.id.sessionsList);

        TextView mProposal_skill = (TextView) v.findViewById(R.id.proposal_skill);
        String mSkill = mProposal.getSkill();
        mProposal_skill.setText(mSkill);

        TextView mProposal_rating = (TextView) v.findViewById(R.id.proposal_rating);
        mProposal_rating.setText("Instructor Rating: " + mProposal.getRating());

        TextView mProposal_description = (TextView) v.findViewById(R.id.proposal_description);
        mProposal_description.setText("Category: " + mProposal.getCategory());
        //mProposal_description.setText("Description: " + mProposal.getDescription());

        final SimpleDateFormat dateFormat = new SimpleDateFormat("E, HH:mm");
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(mProposal.getStartDate());

        int quanta = 15;
        int running_sum = 0;
        SessionLab sl = SessionLab.get(getContext());
        List<Session> existingSessions = sl.getSessions();
        ArrayList<Session> eSessions_mProposal = new ArrayList<Session>();
        for (int i = 0; i < existingSessions.size(); i++) {
            Session e = existingSessions.get(i);
            if (e.getTeacher().equals(mProposal.getMentorName()) & e.getSkill().equals(mSkill)) {
                eSessions_mProposal.add(e);
            }
        }

        while (running_sum < mProposal.getDuration()) {
            final Date start = startCalendar.getTime();
            startCalendar.add(Calendar.MINUTE, quanta);
            running_sum += quanta;
            Date end = startCalendar.getTime();
            final CheckBox sessionCheckBox = new CheckBox(getContext());
            sessionCheckBox.setText(dateFormat.format(start) + " - " + dateFormat.format(end));
            sessionsLayout.addView(sessionCheckBox);
            //if taken, then set enabled to false
            taken.add(false);
            for (int i = 0; i < eSessions_mProposal.size(); i++) {
                Session e = eSessions_mProposal.get(i);
                if (start.equals(e.getInteractionDate())) {
                    sessionCheckBox.setEnabled(false);
                    taken.set((taken.size() - 1), true);
                    durationTracker[0] += quanta;
                }
            }

            if (durationTracker[0] >= durationCap) {
                sessionCheckBox.setEnabled(false);
            }

            sessionCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        numberOfChecks[0] -= 1;
                        durationTracker[0] -= 15;
                        checkedTimes.remove(start);

                        if (durationTracker[0] <= durationCap) {
                            //enable all others
                            for (int i = 0; i < sessionsLayout.getChildCount(); i++) {
                                View cbView = sessionsLayout.getChildAt(i);
                                if (cbView instanceof CheckBox) {
                                    if (!(((CheckBox) cbView).isEnabled())) {
                                        if (taken.get(i) == false) {
                                            cbView.setEnabled(true);
                                        }
                                    }
                                }
                            }

                        }
                    } else if (isChecked) {
                        numberOfChecks[0] += 1;
                        durationTracker[0] += 15;

                        if (durationTracker[0] <= durationCap) checkedTimes.add(start);

                        if (durationTracker[0] >= durationCap) {
                            //if strictly greater, then uncheck this also and show dialogue
                            if (durationTracker[0] > durationCap) {
                                numberOfChecks[0] -= 1;
                                durationTracker[0] -= 15;
                                sessionCheckBox.toggle();
                                new SweetAlertDialog(getContext())
                                        .setTitleText("Oops :(")
                                        .setContentText("The mentor is not available for more than " + durationCap + " minutes")
                                        .show();

                            }
                            //disable all others
                            for (int i = 0; i < sessionsLayout.getChildCount(); i++) {
                                View cbView = sessionsLayout.getChildAt(i);
                                if (cbView instanceof CheckBox) {
                                    if (!(((CheckBox) cbView).isChecked())) {
                                        cbView.setEnabled(false);
                                    }
                                }
                            }


                        }

                    }
                    if (numberOfChecks[0] <= 0) {
                        b.setEnabled(false);
                    } else if (numberOfChecks[0] > 0) {
                        b.setEnabled(true);
                    }

                }
            });

        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionLab sl = SessionLab.get(view.getContext());

                for (int i = 0; i < checkedTimes.size(); i++) {
                    Session newSession = new Session(sl.getSessions().size(), mProposal);
                    newSession.setInteractionDate(checkedTimes.get(i));
                    sl.AddSession(newSession);
                }
                //Toast.makeText(view.getContext(), "Session for " + newSession.getSkill() + " requested successfully!", Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Session requested successfully!")
                        .setConfirmText("Okay")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                proposalShowFragment.this.getActivity().finish();
                            }
                        })
                        .show();
            }
        });

        //submit only if at least one session is checked
        return v;
    }


}