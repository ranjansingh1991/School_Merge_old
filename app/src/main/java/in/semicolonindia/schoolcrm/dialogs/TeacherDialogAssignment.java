package in.semicolonindia.schoolcrm.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;

@SuppressWarnings("All")
public class TeacherDialogAssignment extends Dialog {

    private Context context;
    private String sAssignmentName;
    private String sAssignDate;
    private String sSubmitDate;
    private String sDescription;


    /* private DatePickerDialog.OnDateSetListener mDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

                }
            };

    private int nYear;
    private int nMonth;
    private int nDay;
    private final int nDEST_JRNY_DATE = 0;

*/
    public TeacherDialogAssignment(@NonNull Context context, @StyleRes int themeResId, String sAssignmentName,
                                   String sAssignDate, String sSubmitDate, String sDescription) {
        super(context, themeResId);
        this.context = context;
        this.sAssignmentName = sAssignmentName;
        this.sAssignDate = sAssignDate;
        this.sSubmitDate = sSubmitDate;
        this.sDescription = sDescription;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.teacher_dialog_assignment);

        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        final TextView tvTitle = (TextView) findViewById(R.id.tvTitle1);
        final TextView tvAssignDate = (TextView) findViewById(R.id.tvAssignDate);
        final TextView tvSubmitDate = (TextView) findViewById(R.id.tvSubmitDate1);
        final TextView tvDescription = (TextView) findViewById(R.id.tvDescription1);
        final LinearLayout llAssignmentDigPnt = (LinearLayout) findViewById(R.id.llAssignmentDigPnt);
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        //FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
              //  (2 * displayMetrics.heightPixels) / 3);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 16, 16, 16);
        llAssignmentDigPnt.setLayoutParams(layoutParams);

        tvTitle.setTypeface(appFontRegular);
        tvDescription.setTypeface(appFontLight);
        tvAssignDate.setTypeface(appFontLight);
        tvSubmitDate.setTypeface(appFontLight);

        tvTitle.setText(sAssignmentName);
        tvAssignDate.setText(sAssignDate);
        tvSubmitDate.setText(sSubmitDate);
        tvDescription.setText(sDescription);


    }
}