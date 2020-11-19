package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadProfileData {
    @SerializedName("mem_photo")
    @Expose
    private String memPhoto;

    public String getMemPhoto() {
        return memPhoto;
    }

    public void setMemPhoto(String memPhoto) {
        this.memPhoto = memPhoto;
    }


}
