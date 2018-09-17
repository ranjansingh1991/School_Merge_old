package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class BookListNames {
    private String sBookName;
    private String sAuthorName;
    private String sPrice;
    private String sClassName;
    private String sDesp;
    private String bookID;
    private String status;

    public BookListNames(String sBookName, String sAuthorName, String sPrice, String sClassName,
                         String sDesp, String bookID, String status) {
        this.sBookName = sBookName;
        this.sAuthorName = sAuthorName;
        this.sPrice = sPrice;
        this.sClassName = sClassName;
        this.sDesp = sDesp;
        this.bookID = bookID;
        this.status = status;
    }

    public String getBookName() {
        return sBookName;
    }

    public String getAuthorName() {
        return sAuthorName;
    }

    public String getPrice() {
        return sPrice;
    }

    public String getClassName() {
        return sClassName;
    }

    public String getDesp() {
        return sDesp;
    }

    public String getBookID() {
        return bookID;
    }

    public String getStatus() {
        return status;
    }
}
