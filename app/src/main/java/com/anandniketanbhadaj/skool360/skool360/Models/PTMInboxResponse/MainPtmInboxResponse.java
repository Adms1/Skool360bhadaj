package com.anandniketanbhadaj.skool360.skool360.Models.PTMInboxResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/25/2017.
 */

public class MainPtmInboxResponse {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayInbox> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArrayInbox> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayInbox> finalArray) {
        this.finalArray = finalArray;
    }

}
