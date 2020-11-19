package com.wb.largestfans.retrofit.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoard {

    @SerializedName("mem_shop_name")
    @Expose
    private String memShopName;
    @SerializedName("mem_rank")
    @Expose
    private String memRank;
    @SerializedName("mem_rank_suffix")
    @Expose
    private String memRankSuffix;
    @SerializedName("mem_point")
    @Expose
    private String memPoint;

    public String getMemShopName() {
        return memShopName;
    }

    public void setMemShopName(String memShopName) {
        this.memShopName = memShopName;
    }

    public String getMemRank() {
        return memRank;
    }

    public void setMemRank(String memRank) {
        this.memRank = memRank;
    }

    public String getMemRankSuffix() {
        return memRankSuffix;
    }

    public void setMemRankSuffix(String memRankSuffix) {
        this.memRankSuffix = memRankSuffix;
    }

    public String getMemPoint() {
        return memPoint;
    }

    public void setMemPoint(String memPoint) {
        this.memPoint = memPoint;
    }

}