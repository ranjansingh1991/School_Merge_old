package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherNoticeNames {
    private String sNoticeTitle;
    private String sDate;
    private String sDesp;

    public TeacherNoticeNames(String sNoticeName, String sDate, String sDesp) {
        this.sNoticeTitle = sNoticeName;
        this.sDate = sDate;
        this.sDesp = sDesp;
    }

    public String getNoticeName() {
        return sNoticeTitle;
    }

    public void setNoticeName(String sNoticeName) {
        this.sNoticeTitle = sNoticeName;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    public String getDesp() {
        return sDesp;
    }

    public void setDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}