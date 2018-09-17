package in.semicolonindia.schoolcrm.parent.beans;

/**
 * Created by Rupesh on 14-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentNoticeNames {
    private String sNoticeName;
    private String sDate;
    private String sDesp;

    public ParentNoticeNames(String sNoticeName, String sDate, String sDesp) {
        this.sNoticeName = sNoticeName;
        this.sDate = sDate;
        this.sDesp = sDesp;
    }

    public String getNoticeName() {
        return sNoticeName;
    }

    public void setNoticeName(String sNoticeName) {
        this.sNoticeName = sNoticeName;
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
