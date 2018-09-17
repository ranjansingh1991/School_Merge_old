package in.semicolonindia.schoolcrm.parent.beans;

/**
 * Created by Rupesh on 14-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentBookRequestNames {
    private String sBookName;
    private String sStatus;
    private String sIssueStartDate;
    private String sIssueEndDate;

    public ParentBookRequestNames(String sBookName, String sStatus, String sIssueStartDate, String sIssueEndDate) {
        this.sBookName = sBookName;
        this.sStatus = sStatus;
        this.sIssueStartDate = sIssueStartDate;
        this.sIssueEndDate = sIssueEndDate;
    }

    public String getBookName() {
        return sBookName;
    }

    public void setBookName(String bookName) {
        sBookName = bookName;
    }

    public String getStatus() {
        return sStatus;
    }

    public void setStatus(String status) {
        sStatus = status;
    }

    public String getIssueStartDate() {
        return sIssueStartDate;
    }

    public void setIssueStartDate(String issueStartDate) {
        sIssueStartDate = issueStartDate;
    }

    public String getIssueEndDate() {
        return sIssueEndDate;
    }

    public void setIssueEndDate(String issueEndDate) {
        sIssueEndDate = issueEndDate;
    }
}