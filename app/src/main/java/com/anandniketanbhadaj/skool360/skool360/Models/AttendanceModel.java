package com.anandniketanbhadaj.skool360.skool360.Models;

import java.util.ArrayList;

public class AttendanceModel {

    private String TotalAbsent;
    private String TotalPresent;
    private String HolidayCount;
    private ArrayList<Attendance> eventsList;

    public AttendanceModel() {

    }

    public String getTotalAbsent() {
        return TotalAbsent;
    }

    public String getHolidayCount() {
        return HolidayCount;
    }

    public void setHolidayCount(String holidayCount) {
        HolidayCount = holidayCount;
    }

    public void setTotalAbsent(String totalAbsent) {
        TotalAbsent = totalAbsent;
    }

    public String getTotalPresent() {
        return TotalPresent;
    }

    public void setTotalPresent(String totalPresent) {
        TotalPresent = totalPresent;
    }

    public ArrayList<Attendance> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<Attendance> eventsList) {
        this.eventsList = eventsList;
    }

    public class Attendance {

        private String AttendanceDate;
        private String Comment;
        private String AttendenceStatus;

        public String getAttendanceDate() {
            return AttendanceDate;
        }

        public void setAttendanceDate(String attendanceDate) {
            AttendanceDate = attendanceDate;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getAttendenceStatus() {
            return AttendenceStatus;
        }

        public void setAttendenceStatus(String attendenceStatus) {
            AttendenceStatus = attendenceStatus;
        }

        public Attendance() {
        }
    }
}
