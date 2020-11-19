package com.wb.largestfans;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.manager.WebServiceManager;
import com.wb.largestfans.retrofit.apimodel.GetcampaignlistResponse;
import com.wb.largestfans.retrofit.apimodel.SignUpResponse;
import com.wb.largestfans.utility.Utility;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox chkTermsAndCondition;
    private EditText et_name, et_user_name, et_user_shopno, et_phone, et_email, et_address, et_passowrd, et_confirm_password,et_city,et_user_shopcode;
    private TextInputLayout til_name, til_user_name, til_user_shopno, til_phone, til_email, til_address, til_password, til_confirm_password,til_city,til_user_shopcode;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext = this;
        Spinner spinner_s = (Spinner) findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(
                this, R.array.india_states, android.R.layout.simple_spinner_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_s.setOnItemSelectedListener(OnCatSpinnerCL);
        spinner_s.setAdapter(category_adapter);

        RelativeLayout activitySignUpRlSubmit = (RelativeLayout) findViewById(R.id.activitySignUpRlSubmit);
        activitySignUpRlSubmit.setOnClickListener(this);
        TextView mTvTermAndCondition = findViewById(R.id.tvTermAndCondition);
        mTvTermAndCondition.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_shopno = (EditText) findViewById(R.id.et_user_shopno);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_address = (EditText) findViewById(R.id.et_address);
        et_passowrd = (EditText) findViewById(R.id.et_passowrd);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);
        et_city = (EditText) findViewById(R.id.et_city);
        et_user_shopcode = (EditText) findViewById(R.id.et_user_shopcode);

        til_name = (TextInputLayout) findViewById(R.id.til_name);
        til_user_name = (TextInputLayout) findViewById(R.id.til_user_name);
        til_user_shopno = (TextInputLayout) findViewById(R.id.til_user_shopno);
        til_phone = (TextInputLayout) findViewById(R.id.til_phone);
        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_address = (TextInputLayout) findViewById(R.id.til_address);
        til_password = (TextInputLayout) findViewById(R.id.til_password);
        til_confirm_password = (TextInputLayout) findViewById(R.id.til_confirm_password);
        til_city = (TextInputLayout) findViewById(R.id.til_city);
        til_user_shopcode = (TextInputLayout) findViewById(R.id.til_user_shopcode);



        // til_name


        chkTermsAndCondition = (CheckBox) findViewById(R.id.chkTermsAndCondition);
        Utility.showProgressDialog("Loading...", SignUpActivity.this);
        WebServiceManager.getcampaignlist(new ResponseCallBack() {
            @Override
            public void success(Object object) {
                Utility.dismissDialog();
                if (object instanceof SignUpResponse) {
                    GetcampaignlistResponse getcampaignlistResponse = (GetcampaignlistResponse) object;
                    String status = getcampaignlistResponse.getStatus();
                    String message = getcampaignlistResponse.getMessage();
                    if (status.equals("OK")) {
                        Utility.signUpSuccessAlert(SignUpActivity.this);
                    } else {
                        Utility.delertAlert(SignUpActivity.this, message);
                    }
                    //   String token = loginResponse.getData().getToken();
                    //  SharedPrefs.save(SignUpActivity.this,"token",token);


                }
            }

            @Override
            public void failure(String message) {
                Utility.dismissDialog();
            }
        });

    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(mContext, R.color.mInputTextColor));

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activitySignUpRlSubmit:
                if (chkTermsAndCondition.isChecked()) {
                    String name = et_name.getText().toString().trim();
                    String user_name = et_user_name.getText().toString().trim();
                    String user_shopno = et_user_shopno.getText().toString().trim();
                    String phone = et_phone.getText().toString().trim();
                    String email = et_email.getText().toString().trim();
                    String address = et_address.getText().toString().trim();
                    String password = et_passowrd.getText().toString().trim();
                    String confirm_password = et_confirm_password.getText().toString().trim();
                    String city = et_city.getText().toString().trim();
                    String shopcode = et_user_shopcode.getText().toString().trim();

                    if (name.equals("")) {
                        til_name.setErrorEnabled(true);
                        til_name.setError("This field is required");

                    }
                    if (shopcode.equals("")) {
                        til_user_shopcode.setErrorEnabled(true);
                        til_user_shopcode.setError("This field is required");

                    }
                    if (user_name.equals("")) {
                        til_user_name.setErrorEnabled(true);
                        til_user_name.setError("This field is required");
                    }
                    if (user_shopno.equals("")) {
                        til_user_shopno.setErrorEnabled(true);
                        til_user_shopno.setError("This field is required");
                    }
                    if (phone.equals("")) {
                        til_phone.setErrorEnabled(true);
                        til_phone.setError("This field is required");
                    }
                    if (email.equals("")) {
                        // til_email.setErrorEnabled(true);
                        // til_email.setError("This field is required");
                    }
                    if (address.equals("")) {
                        //   til_address.setErrorEnabled(true);
                        // til_address.setError("This field is required");
                    }
                    if (password.equals("")) {
                        til_password.setErrorEnabled(true);
                        til_password.setError("This field is required");
                    }
                    if (confirm_password.equals("")) {
                        til_confirm_password.setErrorEnabled(true);
                        til_confirm_password.setError("This field is required");
                    }
                    if (!name.equals("") && !user_name.equals("") && !user_shopno.equals("") && !phone.equals("") && !password.equals("") && !confirm_password.equals("")&& !shopcode.equals("")) {
                        //  inputLayoutNumber.setErrorEnabled(true);
                        til_name.setError(null);
                        // inputLayoutPassword.setErrorEnabled(true);
                        til_user_name.setError(null);
                        til_user_shopno.setError(null);
                        til_user_shopcode.setError(null);
                        til_phone.setError(null);
                        til_email.setError(null);
                        til_address.setError(null);
                        til_password.setError(null);
                        til_confirm_password.setError(null);
                        if (password.length() < 6) {
                            til_confirm_password.setErrorEnabled(true);
                            til_confirm_password.setError("Password should be 6 digits");
                        } else {
                            if (!password.equals(confirm_password)) {
                                // Utility.delertAlert(SignUpActivity.this, "Password and Confirm password should be same");
                                til_confirm_password.setErrorEnabled(true);
                                til_confirm_password.setError("Password and Confirm password should be same");
                            } else {
                                til_confirm_password.setError(null);
                                if (Utility.isConnectedToInternet(SignUpActivity.this)) {
                                    try {
                                        // hide keyboard
                                        Utility.hideKeyboard(this);
                                    } catch (Exception e) {
                                        Log.e(LoginActivity.class.getName(), "" + e);
                                    }
                                    Utility.showProgressDialog("Loading...", SignUpActivity.this);
                                    WebServiceManager.signUp(name, user_name, user_shopno, phone, email, address, password, confirm_password, city, shopcode, new ResponseCallBack() {
                                        @Override
                                        public void success(Object object) {
                                            if (object instanceof SignUpResponse) {
                                                SignUpResponse signUpResponse = (SignUpResponse) object;
                                                String status = signUpResponse.getStatus();
                                                String message = signUpResponse.getMessage();
                                                if (status.equals("OK")) {
                                                    Utility.signUpSuccessAlert(SignUpActivity.this);
                                                } else {
                                                    Utility.delertAlert(SignUpActivity.this, message);
                                                }
                                                //   String token = loginResponse.getData().getToken();
                                                //  SharedPrefs.save(SignUpActivity.this,"token",token);


                                            }
                                        }

                                        @Override
                                        public void failure(String message) {
                                            Utility.delertAlert(SignUpActivity.this, message);
                                        }
                                    });
                                } else {
                                    Utility.delertAlert(SignUpActivity.this, getString(R.string.no_internet_connection));
                                }
                            }
                        }
                    }

                } else {
                    Utility.delertAlert(SignUpActivity.this, "Please select Agreement to the Terms of Use");
                }
                break;
            case R.id.tvTermAndCondition:
                String urlForOpenDoc = "http://rs.dreaminfo.in/term&condition.php";
                Intent intentforOpenDoc = new Intent(Intent.ACTION_VIEW);
                intentforOpenDoc.setData(Uri.parse(urlForOpenDoc));
                startActivity(intentforOpenDoc);
                break;
        }
    }

    /**
     * this method is use to add start mark with the label
     *
     * @param labelText label of textview
     * @param textView  instance of textview
     * @author vimlesh narayan September 06, 2017
     */

    public static void addAsterisk(TextView textView, String labelText) {

        String colored = " *";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(labelText);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);

    }
}
