package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by RANJAN SINGH on 10/4/2017.
 */

public class TeacherAssignmentName {

    private String sTitle;
    private String sUpload_Date;
    private String sSubmit_Date;
    private String sDescription;

    public TeacherAssignmentName(String sTitle, String sDescription, String sUpload_Date, String sSubmit_Date) {
        this.sTitle = sTitle;
        this.sUpload_Date = sUpload_Date;
        this.sSubmit_Date = sSubmit_Date;
        this.sDescription = sDescription;
    }

    public String getTitle() {
        return sTitle;
    }

    public String getUploadDate() {
        return sUpload_Date;
    }

    public String getSubmitDate() {
        return sSubmit_Date;
    }

    public String getDescription() {
        return sDescription;
    }
}