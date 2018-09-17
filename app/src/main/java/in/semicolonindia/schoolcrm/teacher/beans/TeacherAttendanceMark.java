package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by MY_PC on 16-10-2017.
 */
@SuppressWarnings("ALL")
public class TeacherAttendanceMark {
    private String sRollNumber = "";
    private String sName = "";
    private String sStudentId = "";
    private String sStatus = "";

    public TeacherAttendanceMark(String rollNumber, String sName,
                                 String sStudentId, String status) {
        this.sRollNumber = rollNumber;
        this.sName = sName;
        this.sStudentId = sStudentId;
        this.sStatus = status;
    }

    public String getStudentId() {
        return sStudentId;
    }

    public String getStatus() {
        return sStatus;
    }

    public String getRollNumber() {
        return sRollNumber;
    }

    public String getName() {
        return sName;
    }
}
