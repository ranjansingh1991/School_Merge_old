package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by RANJAN SINGH on 11/18/2017.
 */
@SuppressWarnings("ALL")
public class TeacherTeacherNames {
    private String name;
    private String speciality;
    private String email;
    private String ID;
    private String sImgURL;
    private int nMsgCount;
    private int nReceiverType;

    public TeacherTeacherNames(String name, String email, String ID, String sImgURL) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
    }

    public TeacherTeacherNames(String name, String email, String ID, String sImgURL, int nMsgCount, int nReceiverType) {
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