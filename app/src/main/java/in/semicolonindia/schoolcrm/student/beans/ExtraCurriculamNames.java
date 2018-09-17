package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by MPAYAL-PC on 11/30/2017.
 */
@SuppressWarnings("ALL")
public class ExtraCurriculamNames {
    private int image_url ;
    private String sDate;
    private String sTime;
    private String sDesp;

    public ExtraCurriculamNames(int image_url, String sDate, String sTime, String sDesp) {
        this.image_url = image_url;
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
