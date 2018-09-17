package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by MPAYAL-PC on 11/28/2017.
 */
@SuppressWarnings("All")

public class LeaderBoardNames {
    private int image_url ;
    private String sName;
    private String sClass;
    private String sSection;
    private String sDate;
    private String sTime;
    private String sAchivments;
    private String sDesp;


    public LeaderBoardNames(int image_url, String sName, String sClass, String sSection,
                            String sDate, String sTime, String sAchivments, String sDesp) {
        this.image_url = image_url;
        this.sName = sName;
        this.sClass = sClass;
        this.sSection = sSection;
        this.sDate = sDate;
        this.sTime = sTime;
        this.sAchivments = sAchivments;
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

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsSection() {
        return sSection;
    }

    public void setsSection(String sSection) {
        this.sSection = sSection;
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

    public String getsAchivments() {
        return sAchivments;
    }

    public void setsAchivments(String sAchivments) {
        this.sAchivments = sAchivments;
    }

    public String getsDesp() {
        return sDesp;
    }

    public void setsDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}
