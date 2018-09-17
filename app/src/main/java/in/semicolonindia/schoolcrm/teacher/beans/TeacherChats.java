package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class TeacherChats {

    public boolean left;
    public String message;
    public String dateTime;

    public TeacherChats(boolean left, String msg, String dateTime) {
        super();
        this.left = left;
        this.message = msg;
        this.dateTime = dateTime;
    }
}