package in.semicolonindia.schoolcrm.parent.beans;

/**
 * Created by Rupesh on 14-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentTeacherNames {
    private String name;
    private String speciality;
    private String email;
    private String ID;
    private String sImgURL;
    private int nMsgCount;

    public ParentTeacherNames(String name, String email, String ID, String sImgURL) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
    }
    public ParentTeacherNames(String name, String email, String ID, String sImgURL, int nMsgCount) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.sImgURL = sImgURL;
        this.nMsgCount = nMsgCount;
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
}
