package com.example.psp;

import android.os.Bundle;

import com.example.psp.base.BaseActivity;
import com.example.psp.constants.Constants;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setTitle(Constants.TITLE_NOTIFICATION);
    }
}
