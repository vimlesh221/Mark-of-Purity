
package com.wb.largestfans.retrofit.apimodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthlyLeaderBoard {

    @SerializedName("Monthly")
    @Expose
    private List<Monthly> monthly = null;

    public List<Monthly> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<Monthly> monthly) {
        this.monthly = monthly;
    }

}
