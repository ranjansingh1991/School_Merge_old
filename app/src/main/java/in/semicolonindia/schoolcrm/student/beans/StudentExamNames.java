package in.semicolonindia.schoolcrm.student.beans;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rupesh on 01-02-2018.
 */
@SuppressWarnings("ALL")
public class StudentExamNames extends AppCompatActivity {

    //private String sExamTitle;
    private String sObtainedMarks;
    private String sTotalMarks;
    private String sSubjectName;
    private String sGrade;
    private String sRemarks;

    //private String sExamID;

    public StudentExamNames(String sSubjectName, String sObtainedMarks, String sTotalMarks, String sGrade, String sRemarks) {
        this.sObtainedMarks = sObtainedMarks;
        this.sTotalMarks = sTotalMarks;
        this.sSubjectName = sSubjectName;
        this.sGrade = sGrade;
        this.sRemarks = sRemarks;
    }

   /* public String getsExamID() {
        return sExamID;
    }*/

    public String getsObtainedMarks() {
        return sObtainedMarks;
    }

   public String getsTotalMarks() {
        return sTotalMarks;
    }

    public String getsSubjectName() {
        return sSubjectName;
    }

    public String getsGrade() {
        return sGrade;
    }

    public String getsRemarks() {
        return sRemarks;
    }
}
