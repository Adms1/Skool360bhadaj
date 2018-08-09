package com.anandniketanbhadaj.skool360.skool360.Models.Suggestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuggestionInboxModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<InboxFinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<InboxFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<InboxFinalArray> finalArray) {
        this.finalArray = finalArray;
    }
}
