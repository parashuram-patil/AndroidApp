package com.example.psp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.psp.listeners.OnSwipeTouchListener;
import com.example.psp.util.Util;


public class OnboardingStep1Fragment extends Fragment {

    public OnboardingStep1Fragment() {
    }

    public static OnboardingStep1Fragment newInstance() {

        return new OnboardingStep1Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_step1, container, false);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.onbaordingFragment);
        frameLayout.setOnTouchListener(new OnSwipeTouchListener(view.getContext()) {
            @Override
            public void onSwipeLeft() {
                Util.openNextFragment((ViewPager) getActivity().findViewById(R.id.pager));
            }
        });

        AppCompatImageView viewById = view.findViewById(R.id.arrowImage);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openNextFragment((ViewPager) getActivity().findViewById(R.id.pager));
            }
        });


        return view;
    }
}
