package com.anandniketanbhadaj.skool360.skool360.Models.ExamSyllabus;

import com.anandniketanbhadaj.skool360.skool360.Models.ImprestResponse.Datum;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamFinalArray {
    @SerializedName("TestName")
    @Expose
    private String testName;
    @SerializedName("TestDate")
    @Expose
    private String testDate;
    @SerializedName("Data")
    @Expose
    private List<ExamDatum> data = null;
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("Photos")
    @Expose
    private List<PhotoModel> photos = null;
    //====Leave Data===========
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("StudentName")
    @Expose
    private String studentName;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    =================


    //================holiday==================
    @SerializedName("MonthName")
    @Expose
    private String monthName;
    @SerializedName("MonthImage")
    @Expose
    private String monthImage;
    @SerializedName("Year")
    @Expose
    private String year;
    public String getMonthName() {
        return monthName;
    }
    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getMonthImage() {
        return monthImage;
    }

    public void setMonthImage(String monthImage) {
        this.monthImage = monthImage;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    //=========================================
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public List<ExamDatum> getData() {
        return data;
    }

    public void setData(List<ExamDatum> data) {
        this.data = data;
    }
}
