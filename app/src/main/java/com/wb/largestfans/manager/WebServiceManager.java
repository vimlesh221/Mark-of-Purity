package com.wb.largestfans.manager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.wb.largestfans.callback.ResponseCallBack;
import com.wb.largestfans.constant.AppConstants;
import com.wb.largestfans.db.SharedPrefs;
import com.wb.largestfans.retrofit.apimodel.ForgotPasswordResponse;
import com.wb.largestfans.retrofit.apimodel.GetLeaderBoardResponse;
import com.wb.largestfans.retrofit.apimodel.GetMemberInfoResponse;
import com.wb.largestfans.retrofit.apimodel.GetNoticeBoardResponse;
import com.wb.largestfans.retrofit.apimodel.GetcampaignlistResponse;
import com.wb.largestfans.retrofit.apimodel.LoginResponse;
import com.wb.largestfans.retrofit.apimodel.SignUpErrorResponse;
import com.wb.largestfans.retrofit.apimodel.SignUpRequest;
import com.wb.largestfans.retrofit.apimodel.SignUpResponse;
import com.wb.largestfans.retrofit.apimodel.UploadDailyPhotoResponse;
import com.wb.largestfans.retrofit.apimodel.UploadProfilePhotoResponse;
import com.wb.largestfans.retrofit.network.APIClient;
import com.wb.largestfans.retrofit.network.APIInterface;
import com.wb.largestfans.utility.Utility;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WebServiceManager {




    public static void signUp(String name, String user_name, String user_shopno, String phone, String email, String address, String passowrd, String confirm_password,String city,String shopcode, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature", Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","register");
        params.put("mem_mobile",phone);
        params.put("mem_shop_name",user_shopno);
        params.put("mem_name",name);
        params.put("mem_adddress",address);
        params.put("mem_pincode",shopcode);
        params.put("mem_city",city);
        params.put("mem_state","Karnataka");
        params.put("mem_email",email);
        params.put("mem_password",passowrd);
        params.put("campaign_name","Karnataka");

        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Gson gson = new GsonBuilder().create();
                Utility.dismissDialog();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpResponse signUpResponse = gson.fromJson(json.toString(), SignUpResponse.class);
                        String status = signUpResponse.getStatus();
                        String message = signUpResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(signUpResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                call.cancel();
                Utility.dismissDialog();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }

    public static void Login(String phoneNumber, String password, String token, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","login");
        params.put("mem_mobile",phoneNumber);
        params.put("mem_password",password);
        params.put("mem_app_regid",token);


        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        LoginResponse loginResponse = gson.fromJson(json.toString(), LoginResponse.class);
                        String status = loginResponse.getStatus();
                        String message = loginResponse.getMessage();
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(loginResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }


                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Utility.dismissDialog();
                call.cancel();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }

    public static void getMemberInfo(Activity activity, String mobile, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","getmemberinfo");
        params.put("mem_mobile",mobile);
        params.put("token",SharedPrefs.getString(activity,"token"));


        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();
                if (response.isSuccessful()) {

                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        GetMemberInfoResponse getMemberInfoResponse = gson.fromJson(json.toString(), GetMemberInfoResponse.class);
                        String status = getMemberInfoResponse.getStatus();
                        String message = getMemberInfoResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(getMemberInfoResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }


                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                call.cancel();
                Utility.dismissDialog();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }
    public static void getLeaderBoardData(Activity activity, String mobile, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","getmonthlyleaderboard");
        params.put("mem_mobile",mobile);
        params.put("token", SharedPrefs.getString(activity,"token"));

        Utility.showProgressDialog("Loading...", activity);
        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();
                if (response.isSuccessful()) {

                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        GetLeaderBoardResponse getLeaderBoardResponse = gson.fromJson(json.toString(), GetLeaderBoardResponse.class);
                        String status = getLeaderBoardResponse.getStatus();
                        String message = getLeaderBoardResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(getLeaderBoardResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                call.cancel();
                Utility.dismissDialog();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }

    public static void uploadDailyPhoto(Activity activity, String mobile, File file, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
       /* params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","getleaderboard");
        params.put("mem_mobile",mobile);
        params.put("token",SharedPrefs.getString(activity,"token"));*/
       // File file = new File("/storage/emulated/0/Download/Corrections 6.jpg");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("dailyphotofile", file.getName(), requestFile);

// add another part within the multipart request
        RequestBody requestBodyRequesttime =
                RequestBody.create(MediaType.parse("multipart/form-data"), getCurrentDateTime);
        RequestBody requestBodySignature =
                RequestBody.create(MediaType.parse("multipart/form-data"), Utility.getSHA256Hash(getCurrentDateTime));
        RequestBody requestBodyTask =
                RequestBody.create(MediaType.parse("multipart/form-data"), "uploaddailyphoto");
        RequestBody requestBodyToken =
                RequestBody.create(MediaType.parse("multipart/form-data"), SharedPrefs.getString(activity,"token"));
        RequestBody requestBodyMemmobile =
                RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        Utility.showProgressDialog("Loading...", activity);
        Call<Object> call1 = apiInterface.updatePhoto(requestBodyRequesttime,requestBodySignature,requestBodyTask,requestBodyToken,requestBodyMemmobile,body);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();

                if (response.isSuccessful()) {
                 //   Toast.makeText(activity,"success",Toast.LENGTH_LONG).show();
                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        UploadDailyPhotoResponse uploadDailyPhotoResponse = gson.fromJson(json.toString(), UploadDailyPhotoResponse.class);
                        String status = uploadDailyPhotoResponse.getStatus();
                        String message = uploadDailyPhotoResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(uploadDailyPhotoResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                call.cancel();
                Utility.dismissDialog();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }

    public static void uploadProfilePhoto(final Activity activity, String mobile, File file, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
       /* params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","getleaderboard");
        params.put("mem_mobile",mobile);
        params.put("token",SharedPrefs.getString(activity,"token"));*/
        // File file = new File("/storage/emulated/0/Download/Corrections 6.jpg");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photofile", file.getName(), requestFile);

// add another part within the multipart request
        RequestBody requestBodyRequesttime =
                RequestBody.create(MediaType.parse("multipart/form-data"), getCurrentDateTime);
        RequestBody requestBodySignature =
                RequestBody.create(MediaType.parse("multipart/form-data"), Utility.getSHA256Hash(getCurrentDateTime));
        RequestBody requestBodyTask =
                RequestBody.create(MediaType.parse("multipart/form-data"), "uploadprofilephoto");
        RequestBody requestBodyToken =
                RequestBody.create(MediaType.parse("multipart/form-data"), SharedPrefs.getString(activity,"token"));
        RequestBody requestBodyMemmobile =
                RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        Utility.showProgressDialog("Loading...", activity);
        Call<Object> call1 = apiInterface.updatePhoto(requestBodyRequesttime,requestBodySignature,requestBodyTask,requestBodyToken,requestBodyMemmobile,body);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();

                if (response.isSuccessful()) {
                    Toast.makeText(activity,"success",Toast.LENGTH_LONG).show();
                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        UploadProfilePhotoResponse uploadProfilePhotoResponse = gson.fromJson(json.toString(), UploadProfilePhotoResponse.class);
                        String status = uploadProfilePhotoResponse.getStatus();
                        String message = uploadProfilePhotoResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(uploadProfilePhotoResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {
                    Toast.makeText(activity,"failed",Toast.LENGTH_LONG).show();

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                call.cancel();
                Utility.dismissDialog();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }
    public static void forgotPassword(String phoneNumber, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","retrievepassword");
        params.put("mem_mobile",phoneNumber);
        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        ForgotPasswordResponse loginResponse = gson.fromJson(json.toString(), ForgotPasswordResponse.class);
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        String status = loginResponse.getStatus();
                        String message = loginResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(loginResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);
                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Utility.dismissDialog();
                call.cancel();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }

    public static void getcampaignlist(final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        SignUpRequest signUpRequest = new SignUpRequest();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","getcampaignlist");
        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        GetcampaignlistResponse loginResponse = gson.fromJson(json.toString(), GetcampaignlistResponse.class);
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        String status = loginResponse.getStatus();
                        String message = loginResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(loginResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);
                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Utility.dismissDialog();
                call.cancel();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }


    public static void getNoticeBoardData(Activity activity, String mobile, final ResponseCallBack listener) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Map<String,String> params = new HashMap<>();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.dateFormat_mmddyyyy);
        String getCurrentDateTime = sdf.format(c.getTime());
        params.put("requesttime",getCurrentDateTime);
        params.put("signature",Utility.getSHA256Hash(getCurrentDateTime));
        params.put("task","getnoticeboard");
        params.put("mem_mobile",mobile);
        params.put("token", SharedPrefs.getString(activity,"token"));

        Utility.showProgressDialog("Loading...", activity);
        Call<Object> call1 = apiInterface.createUser(params);
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Utility.dismissDialog();
                Gson gson = new GsonBuilder().create();
                if (response.isSuccessful()) {

                    try {
                        assert response.body() != null;
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        GetNoticeBoardResponse getNoticeBoardResponse = gson.fromJson(json.toString(), GetNoticeBoardResponse.class);
                        String status = getNoticeBoardResponse.getStatus();
                        String message = getNoticeBoardResponse.getMessage();
                        // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                        if(status.equals("OK")) {
                            // LoginResponse signUpResponse1 = gson.fromJson((response.body().toString()), LoginResponse.class);
                            if (listener != null) {
                                listener.success(getNoticeBoardResponse);
                            }
                        }else {
                            @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap2 = (LinkedTreeMap<String, Object>) response.body();
                            //Convert Linked tree map data into json object.
                            JSONObject json2 = new JSONObject(linkedTreeMap2);
                            SignUpErrorResponse serviceErrorResponse = gson.fromJson(json2.toString(), SignUpErrorResponse.class);

                            listener.failure(serviceErrorResponse.getErrorMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        @SuppressWarnings("unchecked") LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) response.body();
                        //Convert Linked tree map data into json object.
                        JSONObject json = new JSONObject(linkedTreeMap);
                        SignUpErrorResponse serviceErrorResponse = gson.fromJson(json.toString(), SignUpErrorResponse.class);
                        String status = serviceErrorResponse.getStatus();
                        listener.failure(serviceErrorResponse.getErrorMessage());
                    }
                } else {

                }
            }

            /**
             * this method is use to call on sign up failure response
             * @param t instance of Throwable
             * @author vimlesh narayan September 06, 2017
             */
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                call.cancel();
                Utility.dismissDialog();
                listener.failure("Something wrong on server. Please try again after some time");
            }
        });
    }
}