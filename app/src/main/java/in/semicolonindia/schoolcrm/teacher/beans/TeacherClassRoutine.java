package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 26-09-2017.
 */
@SuppressWarnings("ALL")
public class TeacherClassRoutine {

    private String DayName = "";
    private String StartEndTime = "";
    private String SubjectName = "";

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }

    public String getStartEndTime() {
        return StartEndTime;
    }

    public void setStartEndTime(String startEndTime) {
        StartEndTime = startEndTime;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}