package in.semicolonindia.schoolcrm.student.beans;

/**
 * Created by RANJAN SINGH on 10/4/2017.
 */
@SuppressWarnings("ALL")
public class AssignmentName {

    private String aTitle;
    private String aQuestion;
    private String aUpload_Date;
    private String a_Submit_Date;
    private String aMarks;
    private String Report;
    private String sClassID;
    private String fileName;
    //private String fileType;

    public AssignmentName(String aTitle, String aQuestion, String aUpload_Date, String a_Submit_Date,
                          String aMarks, String report, String sClassID,
                          String fileName/*,String fileType*/) {
        this.aTitle = aTitle;
        this.aQuestion = aQuestion;
        this.aUpload_Date = aUpload_Date;
        this.a_Submit_Date = a_Submit_Date;
        this.aMarks = aMarks;
        this.Report = report;
        this.sClassID = sClassID;
        this.fileName = fileName;
        //this.fileType = fileType;
    }


    public String getClassID() {
        return sClassID;
    }

    public void setClassID(String sID) {
        this.sClassID = sID;
    }

    public String getaTitle() {
        return aTitle;
    }

    public void setaTitle(String aTitle) {
        this.aTitle = aTitle;
    }

    public String getaQuestion() {
        return aQuestion;
    }

    public void setaQuestion(String aQuestion) {
        this.aQuestion = aQuestion;
    }

    public String getaUpload_Date() {
        return aUpload_Date;
    }

    public void setaUpload_Date(String aUpload_Date) {
        this.aUpload_Date = aUpload_Date;
    }

    public String getA_Submit_Date() {
        return a_Submit_Date;
    }

    public void setA_Submit_Date(String a_Submit_Date) {
        this.a_Submit_Date = a_Submit_Date;
    }

    public String getaMarks() {
        return aMarks;
    }

    public void setaMarks(String aMarks) {
        this.aMarks = aMarks;
    }

    public String getReport() {
        return Report;
    }

    public void setReport(String report) {
        Report = report;
    }

    public String getFileName() {
        return fileName;
    }


   /* public String getFileType() {
        return fileType;
    }
*/
}