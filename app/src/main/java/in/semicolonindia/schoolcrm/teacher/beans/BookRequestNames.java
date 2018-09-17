package in.semicolonindia.schoolcrm.teacher.beans;

/**
 * Created by Rupesh on 07-08-2017.
 */
@SuppressWarnings("ALL")
public class BookRequestNames {

    private String sBookName;
    private String sStatus;
    private String sIssueStartDate;
    private String sIssueEndDate;
    private String bookID;

    public BookRequestNames(String sBookName, String sStatus, String sIssueStartDate, String sIssueEndDate, String bookID) {
        this.sBookName = sBookName;
        this.sStatus = sStatus;
        this.sIssueStartDate = sIssueStartDate;
        this.sIssueEndDate = sIssueEndDate;
        this.bookID = bookID;
    }

    public String getBookName() {
        return sBookName;
    }

    public String getStatus() {
        return sStatus;
    }

    public String getBookID() {
        return bookID;
    }

    public String getIssueStartDate() {
        return sIssueStartDate;
    }

    public String getIssueEndDate() {
        return sIssueEndDate;
    }
}