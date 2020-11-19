package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("requesttime")
    @Expose
    private String requesttime;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("mem_mobile")
    @Expose
    private String memMobile;
    @SerializedName("mem_shop_name")
    @Expose
    private String memShopName;
    @SerializedName("mem_name")
    @Expose
    private String memName;
    @SerializedName("mem_adddress")
    @Expose
    private String memAdddress;
    @SerializedName("mem_pincode")
    @Expose
    private String memPincode;
    @SerializedName("mem_city")
    @Expose
    private String memCity;
    @SerializedName("mem_state")
    @Expose
    private String memState;
    @SerializedName("mem_email")
    @Expose
    private String memEmail;
    @SerializedName("mem_password")
    @Expose
    private String memPassword;


    public String getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(String requesttime) {
        this.requesttime = requesttime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getMemMobile() {
        return memMobile;
    }

    public void setMemMobile(String memMobile) {
        this.memMobile = memMobile;
    }

    public String getMemShopName() {
        return memShopName;
    }

    public void setMemShopName(String memShopName) {
        this.memShopName = memShopName;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemAdddress() {
        return memAdddress;
    }

    public void setMemAdddress(String memAdddress) {
        this.memAdddress = memAdddress;
    }

    public String getMemPincode() {
        return memPincode;
    }

    public void setMemPincode(String memPincode) {
        this.memPincode = memPincode;
    }

    public String getMemCity() {
        return memCity;
    }

    public void setMemCity(String memCity) {
        this.memCity = memCity;
    }

    public String getMemState() {
        return memState;
    }

    public void setMemState(String memState) {
        this.memState = memState;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String memPassword) {
        this.memPassword = memPassword;
    }

}