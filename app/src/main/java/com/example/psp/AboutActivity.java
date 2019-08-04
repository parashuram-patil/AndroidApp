package com.example.psp;

import android.os.Bundle;

import com.example.psp.base.BaseActivity;
import com.example.psp.constants.Constants;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(Constants.TITLE_ABOUT);
    }
}
