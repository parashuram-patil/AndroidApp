package com.example.psp;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.psp.base.BaseActivity;
import com.example.psp.constants.Constants;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(Constants.TITLE_ABOUT);

        TextView versionInfo =  findViewById(R.id.versionInfo);
        versionInfo.setText("Version " + BuildConfig.VERSION_NAME);

        TextView textView =(TextView)findViewById(R.id.licence);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String url = "https://github.com/parshuram-patil/AndroidApp/blob/master/README.md";
        String text = "<a href='" + url + "'>" + getString(R.string.more_information) + "</a>";
        textView.setText(Html.fromHtml(text, 0));
    }
}
