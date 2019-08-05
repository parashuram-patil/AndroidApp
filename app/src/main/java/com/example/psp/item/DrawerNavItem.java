package com.example.psp.item;

public class DrawerNavItem {
    String mTitle;
    String mSubtitle;
    int mIcon;

    public DrawerNavItem(String title, String subtitle, int icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public int getmIcon() {
        return mIcon;
    }
}
