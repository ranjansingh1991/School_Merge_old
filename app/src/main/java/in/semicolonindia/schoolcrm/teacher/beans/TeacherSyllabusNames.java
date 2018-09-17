package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class TeacherSyllabusNames {

    private String sTitle;
    private String sSubject;
    private String sDate;
    private String sFile;
    private String sDesp;

    public TeacherSyllabusNames(String sTitle, String sSubject, String sDate, String sFile, String sDesp) {
        this.sTitle = sTitle;
        this.sSubject = sSubject;
        this.sDate = sDate;
        this.sFile = sFile;
        this.sDesp = sDesp;
    }

    public String getTitle() {
        return sTitle;
    }

    public void setTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getSubject() {
        return sSubject;
    }

    public void setSubject(String sSubject) {
        this.sSubject = sSubject;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    public String getFile() {
        return sFile;
    }

    public void setFile(String sFile) {
        this.sFile = sFile;
    }

    public String getDesp() {
        return sDesp;
    }

    public void setDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}