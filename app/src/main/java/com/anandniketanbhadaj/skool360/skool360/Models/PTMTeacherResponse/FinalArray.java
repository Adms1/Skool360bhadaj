package com.anandniketanbhadaj.skool360.skool360.Models.PTMTeacherResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 10/30/2017.
 */

public class FinalArray {
    @SerializedName("TeacherName")
    @Expose
    private String teacherName;
    @SerializedName("StaffID")
    @Expose
    private Integer staffID;
    @SerializedName("TeacherID")
    @Expose
    private Integer teacherID;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID) {
        this.teacherID = teacherID;
    }
}
