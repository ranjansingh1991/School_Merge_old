package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 21-08-2017.
 */
@SuppressWarnings("ALL")
public class TeacherExamMarkNames {

    private String rollNumber;
    private String name;
    private String Obtainedmarks;
    private String comment;
    private String StudID;
    private String ExmID;
    private String TotalMrks;


    public TeacherExamMarkNames() {
    }

    public TeacherExamMarkNames(String rollNumber, String name, String StudID) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.StudID = StudID;
    }


    public TeacherExamMarkNames(String rollNumber, String name, String Obtainedmarks, String comment, String StudID) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.Obtainedmarks = Obtainedmarks;
        this.comment = comment;
        this.StudID = StudID;
        //this.StudID = StudID;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObtainedmarks() {
        return Obtainedmarks;
    }

    public void setObtainedmarks(String obtainedmarks) {
        Obtainedmarks = obtainedmarks;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStudID() {
        return StudID;
    }

    public void setStudID(String studID) {
        StudID = studID;
    }

    public String getExmID() {
        return ExmID;
    }

    public void setExmID(String exmID) {
        ExmID = exmID;
    }

    public String getTotalMrks() {
        return TotalMrks;
    }

    public void setTotalMrks(String totalMrks) {
        TotalMrks = totalMrks;
    }
}

