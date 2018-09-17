package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class Chats {
    public boolean left;
    public String message;
    public String dateTime;

    public Chats(boolean left, String msg, String dateTime) {
        super();
        this.left = left;
        this.message = msg;
        this.dateTime = dateTime;
    }
}
