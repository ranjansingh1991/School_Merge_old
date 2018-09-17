package in.semicolonindia.schoolcrm.parent.beans;

/**
 * Created by Rupesh on 09-08-2017.
 */
@SuppressWarnings("ALL")
public class ParentStudentNames {

    private String name;
    private String eMail;
    private String sClassID;
    private String sStudID;
    private String sTeaID;
    private String sSectionID;

    private String classAndSection;

    public ParentStudentNames(String name, String eMail, String sClassID) {
        this.name = name;
        this.eMail = eMail;
        this.sClassID = sClassID;
    }

    public ParentStudentNames(String name, String eMail, String sStudID, boolean noUse) {
        this.name = name;
        this.eMail = eMail;
        this.sStudID = sStudID;
    }

    public ParentStudentNames(String name, String eMail, String sClassID, String sSectionID) {
        this.name = name;
        this.eMail = eMail;
        this.sClassID = sClassID;
        this.sSectionID = sSectionID;
    }
    public ParentStudentNames(String name, String eMail, String sClassID, String sStudID, int i) {
        this.name = name;
        this.eMail = eMail;
        this.sClassID = sClassID;
        this.sStudID = sStudID;
    }

    public ParentStudentNames(String name, String eMail, String sClassID, String sStudID, String sectionID) {
        this.name = name;
        this.eMail = eMail;
        this.sClassID = sClassID;
        this.sStudID = sStudID;
        this.sSectionID = sectionID;
    }

    public String getStudID() {
        return sStudID;
    }

    public void setStudID(String sStudID) {
        this.sStudID = sStudID;
    }

    public String getSectionID() {
        return sSectionID;
    }

    public void setSectionID(String sSectionID) {
        this.sSectionID = sSectionID;
    }

    public String getClassID() {
        return sClassID;
    }

    public void setClassID(String sID) {
        this.sClassID = sID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getClassAndSection() {
        return classAndSection;
    }

    public void setClassAndSection(String classAndSection) {
        this.classAndSection = classAndSection;
    }
}
