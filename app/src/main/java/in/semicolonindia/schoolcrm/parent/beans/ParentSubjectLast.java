package in.semicolonindia.schoolcrm.parent.beans;

/**
 * Created by RANJAN SINGH on 9/29/2017.
 */
@SuppressWarnings("ALL")
public class ParentSubjectLast {
    private String name;
    private String year;
    private String sClassID;

    public ParentSubjectLast(String name, String year, String sClassID) {
        this.name = name;
        this.year = year;
        this.sClassID = sClassID;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
