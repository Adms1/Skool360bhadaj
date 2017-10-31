package com.anandniketanbhadaj.skool360.skool360.Models.PTMInboxResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admsandroid on 10/25/2017.
 */

public class FinalArrayInbox {
    @SerializedName("MessageID")
    @Expose
    private String messageID;
    @SerializedName("FromID")
    @Expose
    private String fromID;
    @SerializedName("ToID")
    @Expose
    private String toID;
    @SerializedName("MeetingDate")
    @Expose
    private String meetingDate;
    @SerializedName("SubjectLine")
    @Expose
    private String subjectLine;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("ReadStatus")
    @Expose
    private String readStatus;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getSubjectLine() {
        return subjectLine;
    }

    public void setSubjectLine(String subjectLine) {
        this.subjectLine = subjectLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

}
