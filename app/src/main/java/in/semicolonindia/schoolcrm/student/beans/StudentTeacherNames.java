package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by RANJAN SINGH on 11/18/2017.
 */
@SuppressWarnings("ALL")
public class StudentTeacherNames {
    private String name;
    private String speciality;
    private String email;
    private String ID;
    private String sImgURL;
    private int nMsgCount;
    private int nReceiverType;

    public StudentTeacherNames(String name, String email, String ID, String sImgURL) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
    }
    public StudentTeacherNames(String name, String email, String ID, String sImgURL, int nMsgCount) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
        this.nMsgCount = nMsgCount;
    }
    public StudentTeacherNames(String name, String email, String ID, String sImgURL, int nMsgCount, int nReceiverType) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
        this.nMsgCount = nMsgCount;
        this.nReceiverType = nReceiverType;
    }

    public String getName() {
        return name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public String getImgURL() {
        return sImgURL;
    }

    public int getMsgCount() {
        return nMsgCount;
    }
    public int getReceiverType() {
        return nReceiverType;
    }

}