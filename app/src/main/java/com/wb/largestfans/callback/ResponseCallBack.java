package com.wb.largestfans.callback;

/**
 * Created by vimleshn on 11/9/2017.
 * description this callback interface is use to pass api response
 */

public interface ResponseCallBack {

    // call on success of api response
    void success(Object object);
    // call on failure response of api
    void failure(String message);



}
