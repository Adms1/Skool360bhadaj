package com.anandniketanbhadaj.skool360.skool360.Models.PTMTeacherResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/30/2017.
 */

public class PTMStudentWiseTeacher {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<FinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<FinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<FinalArray> finalArray) {
        this.finalArray = finalArray;
    }
}
