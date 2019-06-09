package com.androidaps.dashaps;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.PodFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;
import com.androidaps.dashaps.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements OverviewFragment.OnFragmentInteractionListener, PodFragment.OnFragmentInteractionListener, MainTreatmentFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}