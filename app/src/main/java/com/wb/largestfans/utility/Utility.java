package com.wb.largestfans.utility;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.util.Log;
import android.view.inputmethod.InputMethodManager;


import com.wb.largestfans.LoginActivity;
import com.wb.largestfans.R;
import com.wb.largestfans.application.RoyalStagApplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




/**
 * @author vimleshn September 06, 2017
 * Utility.java
 * @description this class is contains all common methods
 */

public class Utility {

    //captured picture uri

    @SuppressWarnings("deprecation")
    private static ProgressDialog mProgressDialog;

    /**
     * @author vimleshn September 06, 2017
     * @description use to check internet connection availability
     */
    public static boolean isConnectedToInternet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                @SuppressWarnings("deprecation") NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @author vimleshn September 06, 2017
     * @description below method use to show progress dialog
     */
    public static void showProgressDialog(String msg, Context ctx) {
        try {
            if (mProgressDialog == null) {
                //noinspection deprecation
                mProgressDialog = new ProgressDialog(ctx,R.style.AppCompatAlertDialogStyle);
                mProgressDialog.setMessage(msg);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
              //  mProgressDialog.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
                mProgressDialog.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author vimleshn September 06, 2017
     * @description below method use to dismiss progress dialog
     */
    public static void dismissDialog() {

        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param activity reference
     * @author vimlesh
     * @description This method is used to close keyboard programmatically.
     */
    public static void hideKeyboard(Activity activity) {
        try {
            // getting application context
            Context context = RoyalStagApplication.getAppContext();
            //Fetching InputMethodManager serivce
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            //Requesting InputMethodManager to close the keyboard
            if (activity.getCurrentFocus() != null) {
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param email current email address
     * @return return true if valid
     * @author vimleshn
     * @description this method is used to check validity of email address
     */

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidMail(CharSequence email) {
        //check email address via matching with regex for valid email

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }







    /**
     * @param activity params for Context
     * @author vimleshn July 06, 2015 2015
     * @description get color code
     */
    // delete confirmation alert
    public static void delertAlert(Activity activity, String message) {
        try {
            //noinspection Convert2Lambda
            new AlertDialog.Builder(activity)
                    .setTitle("")
                    .setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }











    /**
     * this method is use to validate the password
     *
     * @param activity param for password
     * @author vimlesh narayan September 06, 2017
     */

    // delete confirmation alert
    public static void forgotPasswordAlert(final Activity activity, String message) {
        try {
            //noinspection Convert2Lambda
            new AlertDialog.Builder(activity)
                    .setTitle("")
                    .setCancelable(false)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intentDashboard = new Intent(activity, LoginActivity.class);
                            intentDashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intentDashboard);
                            activity.finish();
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is use to validate the password
     *
     * @param activity param for password
     * @author vimlesh narayan September 06, 2017
     */
    // delete confirmation alert
    public static void signUpSuccessAlert(final Activity activity) {
        try {
            //noinspection Convert2Lambda
            new AlertDialog.Builder(activity)
                    .setTitle("")
                    .setCancelable(false)
                    .setMessage("Account created successfully")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            activity.finish();
                            Intent activitySignUpRlSubmit = new Intent(activity, LoginActivity.class);
                            activitySignUpRlSubmit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //extra flag indicate that we are navigating with drawer
                            activity.startActivity(activitySignUpRlSubmit);
                            activity.finish();
                        }
                    })
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1, data));
    }
    public static String getSHA256Hash(String getCurrentDateTime ) {
        MessageDigest digest=null;
        String result = null;
      //  Calendar c = Calendar.getInstance();
     //   SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
      //  String getCurrentDateTime = sdf.format(c.getTime());
        Log.e("currenttime",getCurrentDateTime);
        String signaturedata = getCurrentDateTime+"sugmg803hs756vjf83ak";
       // String signaturedata = "j2vwt5a12qg87daqtazd";

       try {
           // MessageDigest digest = MessageDigest.getInstance("SHA-256");
           try {
               digest = MessageDigest.getInstance("SHA-256");
           } catch (NoSuchAlgorithmException e1) {
               e1.printStackTrace();
           }
           digest.reset();
            byte[] hash = digest.digest(signaturedata.getBytes());
           Log.e("signature",bin2hex(hash));
            return bin2hex(hash); // make it printable
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return  result;
    }


}