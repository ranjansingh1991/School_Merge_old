package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class SyllabusNames {

    private String sTitle;
    private String sSubject;
    private String sUploader;
    private String sDate;
    private String sFile;
    private String sDesp;
    private String fileName;

    public SyllabusNames(String sTitle, String sSubject, String sUploader, String sDate,
                         String sFile, String sDesp) {
        this.sTitle = sTitle;
        this.sSubject = sSubject;
        this.sUploader = sUploader;
        this.sDate = sDate;
        this.sFile = sFile;
        this.sDesp = sDesp;
        this.fileName = fileName;
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

    public String getUploader() {
        return sUploader;
    }

    public void setUploader(String sClass) {
        this.sUploader = sClass;
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