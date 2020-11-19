
package com.wb.largestfans.retrofit.apimodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeBoardData {

    @SerializedName("notice_board")
    @Expose
    private List<NoticeBoard> noticeBoard = null;

    public List<NoticeBoard> getNoticeBoard() {
        return noticeBoard;
    }

    public void setNoticeBoard(List<NoticeBoard> noticeBoard) {
        this.noticeBoard = noticeBoard;
    }

}
