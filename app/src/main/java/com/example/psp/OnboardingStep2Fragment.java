package com.example.psp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.psp.constants.Constants;
import com.example.psp.listeners.OnSwipeTouchListener;
import com.example.psp.util.Util;


public class OnboardingStep2Fragment extends Fragment {

    public OnboardingStep2Fragment() {
    }

    public static OnboardingStep2Fragment newInstance() {

        return new OnboardingStep2Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_step2, container, false);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.onbaordingFragment);
        frameLayout.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                confirmOnboardingComplete(getContext());
                Util.openActivityFromFragmentActivity(getActivity(), MainActivity.class, true);
            }
        });

        AppCompatImageView viewById = view.findViewById(R.id.arrowImage);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOnboardingComplete(getContext());
                Util.openActivityFromFragmentActivity(getActivity(), MainActivity.class, true);
            }
        });


        return view;
    }

    private void confirmOnboardingComplete(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.SPLASH_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(Constants.IS_BOARDING_COMLETE, true);
        editor.apply();
    }
}