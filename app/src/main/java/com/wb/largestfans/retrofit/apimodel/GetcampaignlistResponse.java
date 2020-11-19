package com.wb.largestfans.retrofit.apimodel;

import java.util.ArrayList;

public class GetcampaignlistResponse {
    private String Status;
    private String Message;
    CompaignListData DataObject;


    // Getter Methods

    public String getStatus() {
        return Status;
    }

    public String getMessage() {
        return Message;
    }

    public CompaignListData getData() {
        return DataObject;
    }

    // Setter Methods

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setData(CompaignListData DataObject) {
        this.DataObject = DataObject;
    }
}

