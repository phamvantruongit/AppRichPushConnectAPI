package vn.richpush.bravesoft.apprichpushconnectapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import biz.appvisor.push.android.sdk.AppVisorPush;

public class MainActivity extends AppCompatActivity {

    private AppVisorPush appVisorPush;
    private static final String APP_ID = "iKLV6YLRLc";
    private static final String SENDER_ID = "708094939077";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.appVisorPush = AppVisorPush.sharedInstance();
        this.appVisorPush.setAppInfor(getApplicationContext(), APP_ID);
        this.appVisorPush.setNotificationChannel("my_channel_name", "my_channel_description");
        this.appVisorPush.startPush(SENDER_ID,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                MainActivity.class,
                getString(R.string.app_name));
        this.appVisorPush.trackPushWithActivity(this);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
