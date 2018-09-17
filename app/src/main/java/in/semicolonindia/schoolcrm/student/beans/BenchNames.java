package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by MPAYAL-PC on 11/29/2017.
 */
@SuppressWarnings("ALL")
public class BenchNames {
    private int image_url ;
    private String sName;
    private String sTitle;
    private String sDate;
    private String sTime;
    private String sDesp;

    public BenchNames(int image_url, String sName, String sTitle,
                      String sDate, String sTime, String sDesp) {
        this.image_url = image_url;
        this.sName = sName;
        this.sTitle = sTitle;
        this.sDate = sDate;
        this.sTime = sTime;
        this.sDesp = sDesp;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public String getsDesp() {
        return sDesp;
    }

    public void setsDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}
