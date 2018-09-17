package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 07-08-2017.
 */

@SuppressWarnings("ALL")
public class TeacherStudyMatNames {

    private String sTitle;
    private String sSubject;
    private String sClass;
    private String sDate;
    private String sFile;
    private String sDesp;

    public TeacherStudyMatNames(String sTitle, String sSubject, String sClass, String sDate, String sFile, String sDesp) {
        this.sTitle = sTitle;
        this.sSubject = sSubject;
        this.sClass = sClass;
        this.sDate = sDate;
        this.sFile = sFile;
        this.sDesp = sDesp;
    }

    public String getsTitle() {
        return sTitle;
    }

    public String getsSubject() {
        return sSubject;
    }

    public String getsClass() {
        return sClass;
    }

    public String getsDate() {
        return sDate;
    }

    public String getsFile() {
        return sFile;
    }

    public String getsDesp() {
        return sDesp;
    }
}