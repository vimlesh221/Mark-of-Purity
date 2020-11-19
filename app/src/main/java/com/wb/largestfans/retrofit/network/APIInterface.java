package com.wb.largestfans.retrofit.network;





import com.wb.largestfans.constant.AppConstants;
import com.wb.largestfans.retrofit.apimodel.GetMemberInfoResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by vimleshn on 09/01/17.
 * description this class is use to create all api endpoint method
 */

public interface APIInterface {

    @POST(AppConstants.userSignUp)
    @FormUrlEncoded
    Call<Object> createUser(@FieldMap Map<String, String> params);
    @Multipart
    @POST(AppConstants.userSignUp)
    Call<Object> updatePhoto(@Part("requesttime") RequestBody requesttime,
                             @Part("signature") RequestBody signature, @Part("task") RequestBody task, @Part("token") RequestBody token, @Part("mem_mobile") RequestBody mem_mobile,
                             @Part MultipartBody.Part image
    );
    @POST(AppConstants.userSignUp)
    @FormUrlEncoded
    Call<GetMemberInfoResponse> getMemberInfo(@FieldMap Map<String, String> params);
}
