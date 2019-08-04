package com.example.psp.base;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.psp.constants.Constants;
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
        navItems.add(new DrawerNavItem(Constants.TITLE_HOME, "Starting point", R.drawable.ic_launcher));
        navItems.add(new DrawerNavItem(Constants.TITLE_NOTIFICATION, "Your all notifications", R.drawable.ic_launcher));
        navItems.add(new DrawerNavItem(Constants.TITLE_ABOUT, "About this App", R.drawable.ic_launcher));

        listView = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, navItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectItemFromDrawer(position);
        });

    }

    private void selectItemFromDrawer(int position) {

        String title = navItems.get(position).getmTitle();
        Intent intent;
        switch (title) {
            case Constants.TITLE_HOME:
                intent = new Intent(this, MainActivity.class);
                break;

            case Constants.TITLE_NOTIFICATION:
                intent = new Intent(this, NotificationActivity.class);
                break;

            case Constants.TITLE_ABOUT:
                intent = new Intent(this, AboutActivity.class);
                break;

            default:
                intent = new Intent(this, MainActivity.class);
                break;

        }

        startActivity(intent);
        finish();
        listView.setItemChecked(position, true);
        drawerLayout.closeDrawer(mDrawerPane);
    }
}