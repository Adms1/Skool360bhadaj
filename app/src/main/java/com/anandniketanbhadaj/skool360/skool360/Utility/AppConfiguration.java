package com.anandniketanbhadaj.skool360.skool360.Utility;

/**
 * Created by Harsh on 04-Aug-16.
 */
public class AppConfiguration {

    public enum Domain {
        LIVE, LOCAL
    }

    static Domain domain = Domain.LIVE;//only Change this for changing environment

    public static String getUrl(String methodName) {
        String url = "";
        switch (domain) {
            case LIVE:
                url = DOMAIN_LIVE + methodName;
                break;
            case LOCAL:
                url = DOMAIN_LOCAL + methodName;
                break;
            default:
                break;
        }
        return url;
    }


    //Local
    public static String DOMAIN_LOCAL = "";
    public static String DOMAIN_LIVE = "http://192.168.1.8:8086/MobileApp_Service.asmx/";//use for office only
//    public static String DOMAIN_LIVE = "http://103.24.183.28:8085/MobileApp_Service.asmx/";//use for client


    public static String IMAGE_LIVE="http://192.168.1.8:8086/SKOOL360-Category-Images-Android/Student/";
//    public static String IMAGE_LIVE="http://103.24.183.28:8085/SKOOL360-Category-Images-Android/Student/";

public static String GALLARY_LIVE="http://192.168.1.8:8086/";
//    public static String IMAGE_LIVE="http://103.24.183.28:8085/";

    public static String StudentLogin = "StudentLogin";
    public static String GetUserProfile = "GetUserProfile";
    public static String ChangePassword = "ChangePassword";
    public static String GetClasswork = "GetClasswork";
    public static String GetHomework = "GetHomework";
    public static String GetAttendence = "GetAttendence";
    public static String GetTimetable = "GetTimetable";
    public static String AddAppointmentRequest = "AddAppointmentRequest";
    public static String GetTerm = "GetTerm";
    public static String GetImprest = "GetImprest";
    public static String GetEvent = "GetEvent";
    public static String GetAnnouncement = "GetAnnouncement";
    public static String GetCanteenMenu = "GetCanteenMenu";
    public static String GetCircular = "GetCircular";
    public static String GetTestDetail = "GetTestDetail";
    public static String AddDeviceDetail = "AddDeviceDetail";
    public static String GetStudentResult = "GetStudentResult";
    public static String GetReportcard = "GetReportCard";
    public static String GetFeesStatus = "GetFeesStatus";
    public static String GetPaymentLedger = "PaymentLedger";
    public static String GetPrincipalMessage = "GetPrincipalMessage";
    public static String PTMTeacherStudentGetDetail = "PTMTeacherStudentGetDetail";
    public static String PTMTeacherStudentInsertDetail = "PTMTeacherStudentInsertDetail";
    public static String PTMDeleteMeeting = "PTMDeleteMeeting";
    public static String PTMStudentWiseTeacher = "PTMStudentWiseTeacher";
    public static String GetCircularDetail = "GetCircularDetail";
    public static String DeviceVersion = "DeviceVersion";
    public static String GetGallery="GetGallery";
    public static String InsertStudentLeaveRequest="InsertStudentLeaveRequest";
    public static String GetStudentLeaveRequest="GetStudentLeaveRequest";
    public static String CreateParentsSuggestion="CreateParentsSuggestion";
    public static String GetHoliday="GetHoliday";

}
