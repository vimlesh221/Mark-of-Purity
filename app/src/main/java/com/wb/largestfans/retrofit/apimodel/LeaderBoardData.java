
package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoardData {

    @SerializedName("monthly_leader_board")
    @Expose
    private MonthlyLeaderBoard monthlyLeaderBoard;

    public MonthlyLeaderBoard getMonthlyLeaderBoard() {
        return monthlyLeaderBoard;
    }

    public void setMonthlyLeaderBoard(MonthlyLeaderBoard monthlyLeaderBoard) {
        this.monthlyLeaderBoard = monthlyLeaderBoard;
    }

}
