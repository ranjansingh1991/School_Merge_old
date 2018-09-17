package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 11-10-2017.
 */
@SuppressWarnings("ALL")
public class TeacherNotificationModel {

    private String title;
    private String fromDate;
    private String toDate;
    private String description;

    public TeacherNotificationModel(String title, String fromDate, String toDate, String description) {
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
