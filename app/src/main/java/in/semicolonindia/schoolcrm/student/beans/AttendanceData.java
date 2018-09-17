package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by RANJAN SINGH on 11/20/2017.
 */
@SuppressWarnings("ALL")
public class AttendanceData {

    private String date;
    private String status;

    public AttendanceData(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
