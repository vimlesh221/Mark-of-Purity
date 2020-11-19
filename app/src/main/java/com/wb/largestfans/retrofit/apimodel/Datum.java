package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Datum {

    @SerializedName("z_photo_url")
    @Expose
    private String zPhotoUrl;
    @SerializedName("z_photo_date")
    @Expose
    private String zPhotoDate;

    public String getZPhotoUrl() {
        return zPhotoUrl;
    }

    public void setZPhotoUrl(String zPhotoUrl) {
        this.zPhotoUrl = zPhotoUrl;
    }

    public String getZPhotoDate() {
        return zPhotoDate;
    }

    public void setZPhotoDate(String zPhotoDate) {
        this.zPhotoDate = zPhotoDate;
    }

}