package com.example.psp.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Menu;
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
import com.example.psp.adapter.DrawerListAdapter;
import com.example.psp.constants.Constants;
import com.example.psp.item.DrawerNavItem;
import com.example.psp.util.Util;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    public static Integer notificationCnt = 0;
    public static Menu menu;
    private static Context context;

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
        context = this;
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
        navItems.add(new DrawerNavItem(Constants.TITLE_HOME, "Starting point", R.drawable.home_2x));
        navItems.add(new DrawerNavItem(Constants.TITLE_NOTIFICATION, "Your all notifications", R.drawable.notiifcation_2x));
        navItems.add(new DrawerNavItem(Constants.TITLE_ABOUT, "About this App", R.drawable.about_2x));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navitgation_menu, menu);
        this.menu = menu;
        context = this;
        setNotificationCount();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.notificationIcon) {
            final Intent configurationIntent = new Intent(this, NotificationActivity.class);
            startActivity(configurationIntent);
        }

        return true;
    }

    public static void setNotificationCount() {
        MenuItem itemCart = menu.findItem(R.id.notificationIcon);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        Util.setBadgeCount(context, icon, notificationCnt);
    }
}