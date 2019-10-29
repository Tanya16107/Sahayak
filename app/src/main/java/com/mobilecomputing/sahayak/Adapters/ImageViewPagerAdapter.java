package com.mobilecomputing.sahayak.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mobilecomputing.sahayak.Fragments.FragmentOnboarding1;
import com.mobilecomputing.sahayak.Fragments.FragmentOnboarding2;
import com.mobilecomputing.sahayak.Fragments.FragmentOnboarding3;


public class ImageViewPagerAdapter extends FragmentPagerAdapter {
    public static int totalPage = 3;
    private Context _context;

    public ImageViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch (position) {
            case 0:
                f = new FragmentOnboarding1();
                break;
            case 1:
                f = new FragmentOnboarding2();
                break;
            case 2:
                f = new FragmentOnboarding3();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return totalPage;
    }
}