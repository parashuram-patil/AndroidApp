package com.example.psp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.viewpagerindicator.CirclePageIndicator;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter a = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(a);

        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
    }

    private class PageAdapter extends FragmentPagerAdapter {

        PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;

            switch (pos) {
                case 0:
                    f = OnboardingStep1Fragment.newInstance();
                    break;
                case 1:
                    f = OnboardingStep2Fragment.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
