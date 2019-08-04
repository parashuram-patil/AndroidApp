package com.example.psp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.psp.AboutActivity;
import com.example.psp.MainActivity;
import com.example.psp.NotificationActivity;
import com.example.psp.R;
import com.example.psp.drawer.DrawerNavItem;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    ListView listView;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    ArrayList<DrawerNavItem> navItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);
        //populateDrawerItems();
    }


    @Override
    public void setContentView(int layoutResID) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) drawerLayout.findViewById(R.id.activityContent);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(drawerLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        populateDrawerItems();
    }

    private void populateDrawerItems() {
        navItems = new ArrayList<DrawerNavItem>();
        navItems.add(new DrawerNavItem("Home", "Starting point", R.drawable.ic_launcher));
        navItems.add(new DrawerNavItem("Notification", "Your all notifications", R.drawable.ic_launcher));
        navItems.add(new DrawerNavItem("About", "About this App", R.drawable.ic_launcher));

        listView = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, navItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectItemFromDrawer(position);
        });

    }

    private void selectItemFromDrawer(int position) {

        switch (position) {
            case 1 :
                final Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case 2:
                final Intent notificationIntent = new Intent(this, NotificationActivity.class);
                startActivity(notificationIntent);
                break;
            case 3:
                final Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }

        listView.setItemChecked(position, true);
        setTitle(navItems.get(position).getmTitle());
        drawerLayout.closeDrawer(mDrawerPane);

    }
}