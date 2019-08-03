package com.example.psp.base;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.psp.R;
import com.example.psp.drawer.DrawerNavItem;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);
        //populateDrawerItems();
    }


    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) drawerLayout.findViewById(R.id.activityContent);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(drawerLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        populateDrawerItems();
    }

    private void populateDrawerItems() {
        ArrayList<DrawerNavItem> mNavItems = new ArrayList<DrawerNavItem>();
        mNavItems.add(new DrawerNavItem("Home", "Meetup destination", R.drawable.ic_launcher));
        mNavItems.add(new DrawerNavItem("Preferences", "Change your preferences", R.drawable.ic_launcher));
        mNavItems.add(new DrawerNavItem("About", "Get to know about us", R.drawable.ic_launcher));

        ListView mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        RelativeLayout mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);

        mDrawerList.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getBaseContext(), "Nav " + position + "Selected", Toast.LENGTH_LONG);
            //selectItemFromDrawer(position);
        });
    }
}