package com.anandniketanbhadaj.skool360.skool360.Models.ImprestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admsandroid on 1/2/2018.
 */

public class GetImprestDataModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("OpeningBalance")
    @Expose
    private String openingBalance;
    @SerializedName("MyBalance")
    @Expose
    private String myBalance;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArrayImprest> finalArray = new ArrayList<FinalArrayImprest>();

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getMyBalance() {
        return myBalance;
    }

    public void setMyBalance(String myBalance) {
        this.myBalance = myBalance;
    }

    public List<FinalArrayImprest> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArrayImprest> finalArray) {
        this.finalArray = finalArray;
    }

}
