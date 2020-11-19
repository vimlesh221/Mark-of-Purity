package com.wb.largestfans;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.manager.WebServiceManager;
import com.wb.largestfans.retrofit.apimodel.ForgotPasswordResponse;
import com.wb.largestfans.utility.Utility;



public class ForgotPaswordActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout activityForgotPasswordTextintputPhone;
    private EditText activityForgotPasswordEtPhone;
    private RelativeLayout activityForgotPasswordRlSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        activityForgotPasswordTextintputPhone = (LinearLayout) findViewById(R.id.activityForgotPasswordTextintputPhone);
        activityForgotPasswordEtPhone = (EditText) findViewById(R.id.activityForgotPasswordEtPhone);
        activityForgotPasswordRlSubmit = (RelativeLayout) findViewById(R.id.activityForgotPasswordRlSubmit);
        activityForgotPasswordRlSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String phoneNumber = activityForgotPasswordEtPhone.getText().toString().trim();


        if (phoneNumber.equals("")) {

            activityForgotPasswordEtPhone.setError("This field is required");

        }

        if (!phoneNumber.equals("")) {
            if (Utility.isConnectedToInternet(ForgotPaswordActivity.this)) {
                try {
                    // hide keyboard
                    Utility.hideKeyboard(this);
                } catch (Exception e) {
                    Log.e(LoginActivity.class.getName(), "" + e);
                }
                Utility.showProgressDialog("Loading...", ForgotPaswordActivity.this);
                WebServiceManager.forgotPassword(phoneNumber, new ResponseCallBack() {
                    @Override
                    public void success(Object object) {
                        if (object instanceof ForgotPasswordResponse) {
                            ForgotPasswordResponse forgotPasswordResponse = (ForgotPasswordResponse) object;

                            String status = forgotPasswordResponse.getStatus();
                            String message = forgotPasswordResponse.getMessage();
                            if(status.equals("OK")){
                                Utility.forgotPasswordAlert(ForgotPaswordActivity.this,message);
                            }else {
                                Utility.delertAlert(ForgotPaswordActivity.this,message);
                            }
                        }
                    }

                    @Override
                    public void failure(String message) {
                        Utility.delertAlert(ForgotPaswordActivity.this,message);
                    }
                });
            }else {
                Utility.delertAlert(ForgotPaswordActivity.this, getString(R.string.no_internet_connection));
            }

        }
    }
}
