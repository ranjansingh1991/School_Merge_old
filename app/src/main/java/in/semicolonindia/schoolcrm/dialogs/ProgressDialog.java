package in.semicolonindia.schoolcrm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.widgets.AVLoadingIndicatorView;


/**
 * Created by RANJAN SINGH on 11/14/2017.
 */
@SuppressWarnings("ALL")
public class ProgressDialog extends Dialog {

    private Activity activity;
    private String sMsg;

    public ProgressDialog(Activity activity, String msg) {
        super(activity);
        this.activity = activity;
        this.sMsg = msg;
    }

    public ProgressDialog(Activity activity, String s, String msg) {
        super(activity);
        this.activity = activity;
        this.sMsg = msg;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_dialog_progress);
        final DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        final LinearLayout llDialogParent = (LinearLayout) findViewById(R.id.llDialogParent);
        final AVLoadingIndicatorView loadingIndicator = (AVLoadingIndicatorView) findViewById(R.id.loadingIndicator);
        final TextView tvDialogMsg = (TextView) findViewById(R.id.tvDialogMsg);
        llDialogParent.setMinimumHeight(displayMetrics.heightPixels);
        llDialogParent.setMinimumWidth(displayMetrics.widthPixels);
        loadingIndicator.setIndicator("SquareSpinIndicator");
        loadingIndicator.show();
        tvDialogMsg.setText(sMsg);
    }
}