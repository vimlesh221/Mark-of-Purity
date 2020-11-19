
package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Monthly {

    @SerializedName("mem_shop_name")
    @Expose
    private String memShopName;
    @SerializedName("mem_month_rank")
    @Expose
    private String memMonthRank;
    @SerializedName("mem_month_rank_suffix")
    @Expose
    private String memMonthRankSuffix;
    @SerializedName("mem_month_target_percentage")
    @Expose
    private String memMonthTargetPercentage;
    @SerializedName("rank_change")
    @Expose
    private String rankChange;

    public String getMemShopName() {
        return memShopName;
    }

    public void setMemShopName(String memShopName) {
        this.memShopName = memShopName;
    }

    public String getMemMonthRank() {
        return memMonthRank;
    }

    public void setMemMonthRank(String memMonthRank) {
        this.memMonthRank = memMonthRank;
    }

    public String getMemMonthRankSuffix() {
        return memMonthRankSuffix;
    }

    public void setMemMonthRankSuffix(String memMonthRankSuffix) {
        this.memMonthRankSuffix = memMonthRankSuffix;
    }

    public String getMemMonthTargetPercentage() {
        return memMonthTargetPercentage;
    }

    public void setMemMonthTargetPercentage(String memMonthTargetPercentage) {
        this.memMonthTargetPercentage = memMonthTargetPercentage;
    }

    public String getRankChange() {
        return rankChange;
    }

    public void setRankChange(String rankChange) {
        this.rankChange = rankChange;
    }

}
