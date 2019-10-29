package com.mobilecomputing.sahayak.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.mobilecomputing.sahayak.R;

public class FragmentOnboarding2 extends Fragment {


    public FragmentOnboarding2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding2, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ImageView photo = view.findViewById(R.id.image_onboarding2);

        int width = (Resources.getSystem().getDisplayMetrics().widthPixels) / 3;
        int height = width;

        photo.getLayoutParams().height = height;
        photo.getLayoutParams().width = width;


        photo.requestLayout();
    }

}
