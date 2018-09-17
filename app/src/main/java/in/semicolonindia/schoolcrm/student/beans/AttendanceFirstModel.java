package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by RANJAN SINGH on 11/16/2017.
 */

public class AttendanceFirstModel {
    private String name;
    private String eMail;
    private String sStudID;
    private String sClass;
    private String sSection;
    private String sImgLink;

    public AttendanceFirstModel(String name, String eMail, String sStudID, String sClass, String sSection,
                                String sImgLink) {
        this.name = name;
        this.eMail = eMail;
        this.sStudID = sStudID;
        this.sClass = sClass;
        this.sSection = sSection;
        this.sImgLink = sImgLink;
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

    public String getsStudID() {
        return sStudID;
    }

    public String getImgLink() {
        return sImgLink;
    }

    public String getsClass() {
        return sClass;
    }

    public String getsSection() {
        return sSection;
    }
}