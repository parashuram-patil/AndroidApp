package com.example.psp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.psp.base.BaseActivity;
import com.example.psp.constants.Constants;
import com.example.psp.util.Util;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //       WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setTitle(Constants.TITLE_HOME);

        Util.startNotificationsCleanupWorker();

        /*TestClass testClass = new TestClass();
        testClass.test();*/

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }
}
