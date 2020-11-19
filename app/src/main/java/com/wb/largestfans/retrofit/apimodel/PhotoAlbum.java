
package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoAlbum {

    @SerializedName("z_photo_twitter_share")
    @Expose
    private String zPhotoTwitterShare;
    @SerializedName("z_photo_facebook_share")
    @Expose
    private String zPhotoFacebookShare;
    @SerializedName("z_photo_url")
    @Expose
    private String zPhotoUrl;
    @SerializedName("z_photo_date")
    @Expose
    private String zPhotoDate;
    @SerializedName("allow_delete")
    @Expose
    private String allowDelete;

    public String getZPhotoTwitterShare() {
        return zPhotoTwitterShare;
    }

    public void setZPhotoTwitterShare(String zPhotoTwitterShare) {
        this.zPhotoTwitterShare = zPhotoTwitterShare;
    }

    public String getZPhotoFacebookShare() {
        return zPhotoFacebookShare;
    }

    public void setZPhotoFacebookShare(String zPhotoFacebookShare) {
        this.zPhotoFacebookShare = zPhotoFacebookShare;
    }

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

    public String getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(String allowDelete) {
        this.allowDelete = allowDelete;
    }

}
