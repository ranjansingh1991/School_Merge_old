package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class StudyMatNames {

    private String sTitle;
    private String sSubject;
    private String sClass;
    private String sDate;
    private String fileName;
    private String sDesp;


    public StudyMatNames(String sTitle, String sSubject, String sClass, String sDate, String fileName, String sDesp) {
        this.sTitle = sTitle;
        this.sSubject = sSubject;
        this.sClass = sClass;
        this.sDate = sDate;
        this.fileName = fileName;
        this.sDesp = sDesp;

    }

    public String getTitle() {
        return sTitle;
    }
    public String getSubject() {
        return sSubject;
    }
    public String getClassName() {
        return sClass;
    }
    public String getDate() {
        return sDate;
    }
    public String getFileName() {
        return fileName;
    }
    public String getDesp() {
        return sDesp;
    }
}