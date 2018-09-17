package in.semicolonindia.schoolcrm.rest;

/**
 * Created by Rupesh on 20-08-2017.
 */

@SuppressWarnings("ALL")
public interface BaseUrl {

    String sBaseURL = "http://semicolonindia.in/school-crm-demo/index.php?mobile/";
    // 1. Student All Base Url.......
    public String sLoginURL = sBaseURL + "login";
    public String sGetChangePasswordURL = sBaseURL + "update_user_password";
    public String sGetAUploadImageURL = sBaseURL + "update_user_image";
    public String sForgotPasswordURL = sBaseURL + "reset_password";
    public String sGetAssignmentURL = sBaseURL + "get_assignment";
    public String sHolidayURL = sBaseURL + "get_holiday";
    public String sGetTeacherURL = sBaseURL + "get_teachers";
    public String sGetTeacherMsg = sBaseURL + "get_teacher_message";
    public String sGetSubjectURL = sBaseURL + "get_subjects";
    public String sGetRoutineURL = sBaseURL + "get_class_routine";
    public String sGetSyllabusURL = sBaseURL + "get_syllabus";
    public String sGetMarksURL = sBaseURL + "get_student_mark_information";
    public String sGetProfile = sBaseURL + "get_loggedin_user_profile";
    public String sGetNoticeboardURL = sBaseURL + "get_event_calendar";
    public String sGetStudyMaterialURL = sBaseURL + "get_study_material";
    public String sGetPaymentURL = sBaseURL + "get_single_student_accounting";
    public String sGetBookListURL = sBaseURL + "get_book_list";
    public String sGetAttendanceListURL = sBaseURL + "get_student_attendance";
    public String sMarksListURL = sBaseURL + "get_exam_list";
    public String sGetBookRequestURL = sBaseURL + "get_book_issued_list";
    public String sGetSubmitBookRequestURL = sBaseURL + "submit_book_request";
    public String sGetTransportURL = sBaseURL + "get_transports";
    public String sGetTransportRequestURL  = sBaseURL + "submit_transport_request";
    public String sSendUpdateURL = sBaseURL + "update_user_image";
    public String sUploadUserInfoURL = sBaseURL + "update_user_info";
    public String sGetContactsTeacher = sBaseURL + "get_contacts_teacher";
    public String sSendMsg = sBaseURL + "send_message";
    public String sGetMsg = sBaseURL + "get_message";
    public String sGetNewMsg = sBaseURL + "get_new_message";

    public String sSyllabusDocDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/syllabus/";
    public String sAssignmentDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/assignment/";
    public String sStudyMaterialDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/document/";


    // 2. Techer all base url.......
    public String tLoginURL = sBaseURL + "login";
    public String tForgotPasswordURL = sBaseURL + "reset_password";
    public String tGetChangePasswordURL = sBaseURL + "update_user_password";
    public String tHolidayURL = sBaseURL + "get_holiday";
    public String tGetTeacherURL = sBaseURL + "get_teachers";
    public String tGetAssignmentURL = sBaseURL + "get_assignment";
    public String tGetTeacherReportURL = sBaseURL + "get_teachers_of_class";
    public String tGetProfile = sBaseURL + "get_loggedin_user_profile";
    public String tGetSubjectTeacherURL = sBaseURL + "get_teacher_subjects";
    // public String sGetRoutineURL = sBaseURL + "";
    public String tGetSubjectURL = sBaseURL + "get_subjects";
    public String tGetRoutineURL = sBaseURL + "get_class_routine";
    public String tGetSyllabusURL = sBaseURL + "get_teacher_syllabus";
    public String tUpLoadMarksURL = sBaseURL + "upload_mark";
    public String tGetStudentURL = sBaseURL + "get_students_of_section";
    public String tMarksListURL = sBaseURL + "get_exam_list";
    public String tGetNoticeboardURL = sBaseURL + "get_event_calendar";
    public String tGetStudyMaterialURL = sBaseURL + "get_teacher_study_material";
    public String tGetPaymentURL = sBaseURL + "";
    public String tGetClassURL = sBaseURL + "get_class";
    public String tGetSubject = sBaseURL + "get_subjects";
    public String tGetAUploadImageURL = sBaseURL + "update_user_image";
    public String tGetBookListURL = sBaseURL + "get_book_list";
    public String tGetBookRequestURL = sBaseURL + "get_book_issued_list";
    public String tGetSubmitBookRequestURL = sBaseURL + "submit_book_request";
    public String tUploadUserInfoURL = sBaseURL + "update_user_info";
    public String tGetContactsTeacher = sBaseURL + "get_contacts_teacher";
    public String tSendMsg = sBaseURL + "send_message";
    public String tGetMsg = sBaseURL + "get_message";
    public String tGetNewMsg = sBaseURL + "get_new_message";
    public String tUploadAttendance = sBaseURL + "upload_attendance";
    public String tUploadAssignment = sBaseURL + "upload_assignment";

    public String tSyllabusDocDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/syllabus/";
    public String tAssignmentDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/assignment/";
    public String tStudyMaterialDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/document/";

    // 3. Parent all base url.......
    public String pLoginURL = sBaseURL + "login";
    public String pForgotPasswordURL = sBaseURL + "reset_password";
    public String pGetChangePasswordURL = sBaseURL + "update_user_password";
    public String pGetExamList = sBaseURL + "get_exam_list";
    public String pHolidayURL = sBaseURL + "get_holiday";
    public String pGetAssignmentURL = sBaseURL + "get_assignment";
    public String pGetTeacherURL = sBaseURL + "get_teachers";
    public String pGetTeacherMsg = sBaseURL + "get_teacher_message";
    public String pGetTeacherOfClass = sBaseURL + "get_teachers_of_class";
    public String pGetSubjectURL = sBaseURL + "get_subjects";
    public String pGetRoutineURL = sBaseURL + "get_class_routine";
    public String pGetSyllabusURL = sBaseURL + "get_syllabus";
    public String pGetProfile = sBaseURL + "get_loggedin_user_profile";
    public String pGetMarksURL = sBaseURL + "get_student_mark_information";
    public String pGetNoticeboardURL = sBaseURL + "get_event_calendar";
    public String pGetStudyMaterialURL = sBaseURL + "get_study_material";
    public String pGetPaymentURL = sBaseURL + "get_single_student_accounting";
    public String pGetBookListURL = sBaseURL + "get_book_list";
    public String pGetAUploadImageURL = sBaseURL + "update_user_image";

    public String pGetBookRequestURL = sBaseURL + "get_book_issued_list";
    public String pGetSubmitBookRequestURL = sBaseURL + "submit_book_request";
    public String pGetTransportURL = sBaseURL + "get_transports";
    public String pGetTransportRequestURL  = sBaseURL + "submit_transport_request";
    public String pUploadUserInfoURL = sBaseURL + "update_user_info";
    public String pGetMapLocation = sBaseURL + "get_kid_location";
    public String pGetAttendanceListURL = sBaseURL + "get_student_attendance";
    public String pSendMsg = sBaseURL + "send_message";
    public String pGetMsg = sBaseURL + "get_message";
    public String pGetNewMsg = sBaseURL + "get_new_message";

    public String pSyllabusDocDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/syllabus/";
    public String pAssignmentDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/assignment/";
    public String pStudyMaterialDownloadURL = "http://semicolonindia.in/school-crm-demo/uploads/document/";
}
