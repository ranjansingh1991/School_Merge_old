package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by MPAYAL-PC on 11/29/2017.
 */
@SuppressWarnings("ALL")
public class TeacherScholarNames {
    private String sDate;
    private String sClass;
    private String sDesp;

    public TeacherScholarNames(String sDate, String sClass, String sDesp) {
        this.sDate = sDate;
        this.sClass = sClass;
        this.sDesp = sDesp;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsDesp() {
        return sDesp;
    }

    public void setsDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}
