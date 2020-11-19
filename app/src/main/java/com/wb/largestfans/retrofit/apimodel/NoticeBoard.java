
package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeBoard {


    @SerializedName("notice_board_type")
    @Expose
    private String noticeBoardType;
    @SerializedName("notice_board_video")
    @Expose
    private String noticeBoardVideo;
    @SerializedName("notice_board_text")
    @Expose
    private String noticeBoardText;
    @SerializedName("notice_board_id")
    @Expose
    private String noticeBoardId;
    @SerializedName("notice_board_image")
    @Expose
    private String noticeBoardImage;

    public String getNoticeBoardType() {
        return noticeBoardType;
    }

    public void setNoticeBoardType(String noticeBoardType) {
        this.noticeBoardType = noticeBoardType;
    }

    public String getNoticeBoardVideo() {
        return noticeBoardVideo;
    }

    public void setNoticeBoardVideo(String noticeBoardVideo) {
        this.noticeBoardVideo = noticeBoardVideo;
    }

    public String getNoticeBoardText() {
        return noticeBoardText;
    }

    public void setNoticeBoardText(String noticeBoardText) {
        this.noticeBoardText = noticeBoardText;
    }

    public String getNoticeBoardId() {
        return noticeBoardId;
    }

    public void setNoticeBoardId(String noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public String getNoticeBoardImage() {
        return noticeBoardImage;
    }

    public void setNoticeBoardImage(String noticeBoardImage) {
        this.noticeBoardImage = noticeBoardImage;
    }

}