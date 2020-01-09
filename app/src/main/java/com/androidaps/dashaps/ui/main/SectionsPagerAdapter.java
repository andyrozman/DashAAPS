package com.androidaps.dashaps.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.androidaps.dashaps.R;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.PodFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private Fragment[] fragments = new Fragment[3];
    private String TAG = "SectionsPagerAdapter";

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (fragments[position]==null) {
            Log.d(TAG, "fragemnt null");
            if (position==0) {
                fragments[position] = OverviewFragment.newInstance();
            } else if (position==1) {
                fragments[position] =  MainTreatmentFragment.newInstance();
            } else { //if (position==3) {
                fragments[position] =  PodFragment.newInstance("1", "1");
            }
        }

        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}