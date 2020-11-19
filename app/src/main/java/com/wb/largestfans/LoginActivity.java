package com.wb.largestfans;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.constant.AppConstants;
import com.wb.largestfans.db.SharedPrefs;
import com.wb.largestfans.manager.WebServiceManager;
import com.wb.largestfans.retrofit.apimodel.LoginResponse;
import com.wb.largestfans.utility.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_email, edit_password;
    private Button mBtnSignIn;
    private TextView mTvForgotPwd;
    private EditText mEditTxtName;
    private Button mBtnOk;
    private TextView mTvCancel;
    private CheckBox mCheckRemember;
    private LinearLayout inputLayoutNumber, inputLayoutPassword;
    String mUserPassword, mUserID;
    String phoneNumber ;
    String password ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);

            }
        }
        // [END handle_data_extras]
        if (SharedPrefs.getBoolean(this, "isLogin", false)) {
            Intent intentMerchantListJoinToday = new Intent(LoginActivity.this, DashboardActivity.class);
            intentMerchantListJoinToday.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //extra flag indicate that we are navigating with drawer
            startActivity(intentMerchantListJoinToday);
            finish();
        }
        TextView forgot_txt = (TextView) findViewById(R.id.forgot_txt);
        forgot_txt.setOnClickListener(this);
        LinearLayout tvJoinToday = (LinearLayout) findViewById(R.id.tvJoinToday);
        tvJoinToday.setOnClickListener(this);
        RelativeLayout activitySignUpRlSignIn = (RelativeLayout) findViewById(R.id.activitySignUpRlSignIn);
        activitySignUpRlSignIn.setOnClickListener(this);

        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_password = (EditText) findViewById(R.id.edit_password);

        inputLayoutNumber = (LinearLayout) findViewById(R.id.inputLayoutNumber);
        inputLayoutPassword = (LinearLayout) findViewById(R.id.inputLayoutPassword);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activitySignUpRlSignIn:
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
                String getCurrentDateTime = sdf.format(c.getTime());
                Utility.getSHA256Hash(getCurrentDateTime);
                 phoneNumber = edit_email.getText().toString().trim();
                 password = edit_password.getText().toString().trim();

                if (phoneNumber.equals("")) {

                    edit_email.setError("This field is required");

                }
                if (password.equals("")) {

                    edit_password.setError("This field is required");
                }
                if (!phoneNumber.equals("") && !password.equals("")) {

                    if (password.length() < 6) {

                        edit_password.setError("Password should be 6 digits");
                    } else {


                        try {
                            if (Utility.isConnectedToInternet(LoginActivity.this)) {
                                try {
                                    // hide keyboard
                                    Utility.hideKeyboard(this);
                                } catch (Exception e) {
                                    Log.e(LoginActivity.class.getName(), "" + e);
                                }

                                // Get token
                                // [START retrieve_current_token]
                                FirebaseInstanceId.getInstance().getInstanceId()
                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.w("TAG", "getInstanceId failed", task.getException());
                                                    return;
                                                }

                                                // Get new Instance ID token
                                                String token = task.getResult().getToken();

                                                // Log and toast
                                                String msg = getString(R.string.msg_token_fmt, token);
                                                Log.d("TAG", msg);
                                                Utility.showProgressDialog("Loading...", LoginActivity.this);
                                                WebServiceManager.Login(phoneNumber, password, token, new ResponseCallBack() {
                                                    @Override
                                                    public void success(Object object) {
                                                        if (object instanceof LoginResponse) {
                                                            LoginResponse loginResponse = (LoginResponse) object;
                                                            String status = loginResponse.getStatus();
                                                            String message = loginResponse.getMessage();
                                                            if (status.equals("OK")) {
                                                                String token = loginResponse.getData().getToken();
                                                                SharedPrefs.save(LoginActivity.this, "isLogin", true);
                                                                SharedPrefs.save(LoginActivity.this, "token", token);
                                                                SharedPrefs.save(LoginActivity.this, "mobile", loginResponse.getData().getMobile());
                                                                Intent activitySignUpRlSubmit = new Intent(LoginActivity.this, DashboardActivity.class);
                                                                activitySignUpRlSubmit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                //extra flag indicate that we are navigating with drawer
                                                                startActivity(activitySignUpRlSubmit);
                                                                finish();
                                                            } else {
                                                                Utility.delertAlert(LoginActivity.this, message);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void failure(String message) {
                                                        Utility.delertAlert(LoginActivity.this, message);
                                                    }
                                                });
                                            }
                                        });
                                // [END retrieve_current_token]

                            } else {
                                Utility.delertAlert(LoginActivity.this, getString(R.string.no_internet_connection));
                            }
                        } catch (Exception ignored) {

                        }
                    }
                }
                break;
            case R.id.tvJoinToday:

                Intent intentMerchantListJoinToday = new Intent(LoginActivity.this, SignUpActivity.class);
                intentMerchantListJoinToday.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(intentMerchantListJoinToday);

                break;
            case R.id.forgot_txt:

                Intent activitySignUpRlSubmit = new Intent(LoginActivity.this, ForgotPaswordActivity.class);
                activitySignUpRlSubmit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //extra flag indicate that we are navigating with drawer
                startActivity(activitySignUpRlSubmit);

                break;
        }
    }
}
