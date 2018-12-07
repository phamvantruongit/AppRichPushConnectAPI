package vn.richpush.bravesoft.apprichpushconnectapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import biz.appvisor.push.android.sdk.AppVisorPush;
import biz.appvisor.push.android.sdk.ChangePushStatusListener;

public class MainActivity extends AppCompatActivity {

    private AppVisorPush appVisorPush;
    //private static final String APP_ID = "iIQCDw7qRb";
    private static final String APP_ID = "iKLV6YLRLc";
    private static final String SENDER_ID = "708094939077";
    // API KEY : AAAApN2_T8U:APA91bHOE2qUiXMpR0kYntSNSuQLVXE4IAfw-a7a814WdOYEF-GU3qsDpRn5weWnSIaE-HF9P3bxAGuxmPdkL4CGnCm_BqVUdtVpP72XI3iKtBH8xiCQkmtNTUgVCTKVUrqtYP6aVwd8
    // To do setting on page Admin
    TextView tvMessage;
    CheckBox checkBox;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("Save",0);
        boolean check=preferences.getBoolean("IsCheck",false);

        tvMessage=findViewById(R.id.tvMessage);
        checkBox=findViewById(R.id.check_box);

        this.appVisorPush = AppVisorPush.sharedInstance();
        this.appVisorPush.setAppInfor(getApplicationContext(), APP_ID);
        this.appVisorPush.setNotificationChannel("my_channel_name", "my_channel_description");
        this.appVisorPush.startPush(SENDER_ID,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                MainActivity.class,
                getString(R.string.app_name));
        this.appVisorPush.trackPushWithActivity(this);

        if (this.appVisorPush.checkIfStartByAppVisorPush(this))
        {
            Bundle bundle = this.appVisorPush.getBundleFromAppVisorPush(this);
            String message = bundle.getString("message");

            String xString = bundle.getString("x");
            String yString = bundle.getString("y");
            String zString = bundle.getString("z");
            String wString = bundle.getString("w");

            tvMessage.setText( message +  " W= " + wString +";X= " + xString + "\n" + "Y= "+ yString + ";Z= " + zString);
        }
        if(!check) {
            changePushStatus(false);
        }else {
            changePushStatus(true);
            checkBox.setChecked(true);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    changePushStatus(true);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("IsCheck",true);
                    editor.commit();
                }else {
                    changePushStatus(false);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("IsCheck",false);
                    editor.commit();
                }
            }
        });

    }

    private void changePushStatus(boolean on)
    {
        ChangePushStatusListener listener = new ChangePushStatusListener()
        {
            @Override
            public void changeStatusSucceeded(boolean pushStatus)
            {

            }
            @Override
            public void changeStatusFailed(boolean pushStatus)
            {

            }
        };


        this.appVisorPush.addChangePushStatusListener(listener);

        this.appVisorPush.changePushReceiveStatus(on);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
