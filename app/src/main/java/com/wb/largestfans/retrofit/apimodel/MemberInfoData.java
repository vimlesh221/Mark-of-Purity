
package com.wb.largestfans.retrofit.apimodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberInfoData {

    @SerializedName("mem_status")
    @Expose
    private String memStatus;
    @SerializedName("mem_photo")
    @Expose
    private String memPhoto;
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
    @SerializedName("mem_mobile")
    @Expose
    private String memMobile;
    @SerializedName("mem_email")
    @Expose
    private String memEmail;
    @SerializedName("campaign_name")
    @Expose
    private String campaignName;
    @SerializedName("campaign_daily_limit")
    @Expose
    private String campaignDailyLimit;
    @SerializedName("mem_month_rank")
    @Expose
    private String memMonthRank;
    @SerializedName("mem_month_rank_suffix")
    @Expose
    private String memMonthRankSuffix;
    @SerializedName("mem_month_last_rank")
    @Expose
    private String memMonthLastRank;
    @SerializedName("mem_current_month_sale_qty")
    @Expose
    private String memCurrentMonthSaleQty;
    @SerializedName("mem_monthly_target_qty")
    @Expose
    private String memMonthlyTargetQty;
    @SerializedName("mem_month_target_percentage")
    @Expose
    private String memMonthTargetPercentage;
    @SerializedName("month_rank_change")
    @Expose
    private String monthRankChange;
    @SerializedName("photo_album")
    @Expose
    private List<PhotoAlbum> photoAlbum = null;

    public String getMemStatus() {
        return memStatus;
    }

    public void setMemStatus(String memStatus) {
        this.memStatus = memStatus;
    }

    public String getMemPhoto() {
        return memPhoto;
    }

    public void setMemPhoto(String memPhoto) {
        this.memPhoto = memPhoto;
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

    public String getMemMobile() {
        return memMobile;
    }

    public void setMemMobile(String memMobile) {
        this.memMobile = memMobile;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCampaignDailyLimit() {
        return campaignDailyLimit;
    }

    public void setCampaignDailyLimit(String campaignDailyLimit) {
        this.campaignDailyLimit = campaignDailyLimit;
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

    public String getMemMonthLastRank() {
        return memMonthLastRank;
    }

    public void setMemMonthLastRank(String memMonthLastRank) {
        this.memMonthLastRank = memMonthLastRank;
    }

    public String getMemCurrentMonthSaleQty() {
        return memCurrentMonthSaleQty;
    }

    public void setMemCurrentMonthSaleQty(String memCurrentMonthSaleQty) {
        this.memCurrentMonthSaleQty = memCurrentMonthSaleQty;
    }

    public String getMemMonthlyTargetQty() {
        return memMonthlyTargetQty;
    }

    public void setMemMonthlyTargetQty(String memMonthlyTargetQty) {
        this.memMonthlyTargetQty = memMonthlyTargetQty;
    }

    public String getMemMonthTargetPercentage() {
        return memMonthTargetPercentage;
    }

    public void setMemMonthTargetPercentage(String memMonthTargetPercentage) {
        this.memMonthTargetPercentage = memMonthTargetPercentage;
    }

    public String getMonthRankChange() {
        return monthRankChange;
    }

    public void setMonthRankChange(String monthRankChange) {
        this.monthRankChange = monthRankChange;
    }

    public List<PhotoAlbum> getPhotoAlbum() {
        return photoAlbum;
    }

    public void setPhotoAlbum(List<PhotoAlbum> photoAlbum) {
        this.photoAlbum = photoAlbum;
    }

}
