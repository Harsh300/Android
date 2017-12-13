package com.parse.starter;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        String activityID = i.getStringExtra("activityID");
        setContentView(R.layout.activity_main);

        //set up the ViewPager with the sections adapter
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (activityID.equals("UserListActivity")){
            tabLayout.getTabAt(2).select();
        }else {
            tabLayout.getTabAt(1).select();
        }

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "TAB1");
        adapter.addFragment(new Tab2Fragment(), "TAB2");
        adapter.addFragment(new MapFragment(), "MAP");
        viewPager.setAdapter(adapter);
    }


}
