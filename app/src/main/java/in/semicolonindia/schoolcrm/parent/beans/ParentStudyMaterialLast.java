package in.semicolonindia.schoolcrm.parent.beans;

/**
 * Created by Rupesh on 21-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentStudyMaterialLast {
    private String sTitle;
    private String sSubject;
    private String sClass;
    private String sDate;
    private String sFile;
    private String sDesp;

    public ParentStudyMaterialLast(String sTitle, String sSubject, String sClass,
                                   String sDate, String sFile, String sDesp) {
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

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsSubject() {
        return sSubject;
    }

    public void setsSubject(String sSubject) {
        this.sSubject = sSubject;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsFile() {
        return sFile;
    }

    public void setsFile(String sFile) {
        this.sFile = sFile;
    }

    public String getsDesp() {
        return sDesp;
    }

    public void setsDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}
