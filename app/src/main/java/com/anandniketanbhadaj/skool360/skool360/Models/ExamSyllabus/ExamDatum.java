package com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamDatum {
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Detail")
    @Expose
    private String detail;
    private Boolean isVisible = false;

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    //==========holiday==========
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("Holiday")
    @Expose
    private String holiday;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    //===========================
}
