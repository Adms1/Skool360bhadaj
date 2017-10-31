package com.anandniketanbhadaj.skool360.skool360.Models.PTMCreateResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 10/25/2017.
 */

public class FinalArrayStudentForCreate {
    @SerializedName("Standard")
    @Expose
    private String standard;
    @SerializedName("StandardID")
    @Expose
    private String standardID;
    @SerializedName("classname")
    @Expose
    private String classname;
    @SerializedName("ClassID")
    @Expose
    private Integer classID;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("SubjectID")
    @Expose
    private Integer subjectID;
    @SerializedName("StudentData")
    @Expose
    private List<StudentDatum> studentData = null;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandardID() {
        return standardID;
    }

    public void setStandardID(String standardID) {
        this.standardID = standardID;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getClassID() {
        return classID;
    }

    public void setClassID(Integer classID) {
        this.classID = classID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public List<StudentDatum> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentDatum> studentData) {
        this.studentData = studentData;
    }

}
