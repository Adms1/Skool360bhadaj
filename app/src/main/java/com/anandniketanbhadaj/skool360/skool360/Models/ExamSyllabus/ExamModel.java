package com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus;

import com.anandniketanbhadaj.skool360.skool360.Models.PTMTeacherResponse.FinalArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<ExamFinalArray> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ExamFinalArray> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<ExamFinalArray> finalArray) {
        this.finalArray = finalArray;
    }

}
