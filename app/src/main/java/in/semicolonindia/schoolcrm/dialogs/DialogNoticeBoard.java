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

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class DialogNoticeBoard extends Dialog {
    private Context context;
    private String sTitleName;
    private String sHolidayDate;
    private String sDescription;
    //TeacherNoticeNames mNoticeNames;

    public DialogNoticeBoard(@NonNull Context context, @StyleRes int themeResId, String sTitleName, String sHolidayDate, String sDescription) {
        super(context, themeResId);
        this.context = context;
        this.sTitleName = sTitleName;
        this.sHolidayDate = sHolidayDate;
        this.sDescription = sDescription;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_dialog_notice);

        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        final TextView tvNoticeTitle = (TextView) findViewById(R.id.tvTitle1);
        final TextView tvHolidayDate = (TextView) findViewById(R.id.tvHolidayDate);
        final TextView tvDescription = (TextView) findViewById(R.id.tvDescription1);
        final LinearLayout llnoticeDigPnt = (LinearLayout) findViewById(R.id.llnoticeDigPnt);
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 16, 16, 16);
        llnoticeDigPnt.setLayoutParams(layoutParams);

        tvNoticeTitle.setText(sTitleName);
        tvHolidayDate.setText(sHolidayDate);
        tvDescription.setText(sDescription);

        tvNoticeTitle.setTypeface(appFontRegular);
        tvDescription.setTypeface(appFontLight);
        tvHolidayDate.setTypeface(appFontLight);

    }
}

