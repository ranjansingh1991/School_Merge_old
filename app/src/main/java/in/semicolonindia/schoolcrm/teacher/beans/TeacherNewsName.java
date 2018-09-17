package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherNewsName {
    private String sTitle;
    private int sImages;
    private String sDate;
    private String sTime;
    private String sAdvertisement;
    private String sDes;

    public TeacherNewsName(String sTitle, int sImages, String sDate, String sTime, String sAdvertisement, String sDes) {
        this.sTitle = sTitle;
        this.sImages = sImages;
        this.sDate = sDate;
        this.sTime = sTime;
        this.sAdvertisement = sAdvertisement;
        this.sDes = sDes;
    }

    public String getsTitle() {
        return sTitle;
    }

    public int getsImages() {
        return sImages;
    }

    public String getsDate() {
        return sDate;
    }

    public String getsTime() {
        return sTime;
    }

    public String getsAdvertisement() {
        return sAdvertisement;
    }

    public String getsDes() {
        return sDes;
    }

}
