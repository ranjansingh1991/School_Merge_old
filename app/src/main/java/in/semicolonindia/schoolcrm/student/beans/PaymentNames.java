package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class PaymentNames {

    private String sTitle;
    private String sStatus;
    private String sAmount;
    private String sDate;
    private String sDesp;

    public PaymentNames(String sTitle, String sStatus, String sAmount, String sDate, String sDesp) {
        this.sTitle = sTitle;
        this.sStatus = sStatus;
        this.sAmount = sAmount;
        this.sDate = sDate;
        this.sDesp = sDesp;
    }

    public String getTitle() {
        return sTitle;
    }

    public void setTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getStatus() {
        return sStatus;
    }

    public void setsStatus(String Status) {
        this.sStatus = sStatus;
    }

    public String getAmount() {
        return sAmount;
    }

    public void setAmount(String sAmount) {
        this.sAmount = sAmount;
    }

    public String getDate() {
        return sDate;
    }

    public void setDate(String sDate) {
        this.sDate = sDate;
    }

    public String getDesp() {
        return sDesp;
    }

    public void setDesp(String sDesp) {
        this.sDesp = sDesp;
    }
}