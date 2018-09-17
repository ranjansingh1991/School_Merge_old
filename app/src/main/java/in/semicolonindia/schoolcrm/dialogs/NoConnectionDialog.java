package in.semicolonindia.schoolcrm.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by RANJAN SINGH on 11/9/2017.
 */
@SuppressWarnings("ALL")
public class NoConnectionDialog extends Dialog implements View.OnClickListener {

    // 1. Declear All Variable......
    private Context context;
    TextView tv_Title;
    TextView tv_No_Internet;
    Button btn_No_Connection;


    public NoConnectionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_dialog_no_connection);
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");

        // 2. Initializing all variable...............
        tv_Title = (TextView) findViewById(R.id.tv_Title);
        tv_No_Internet = (TextView) findViewById(R.id.tv_No_Internet);
        btn_No_Connection = (Button) findViewById(R.id.btn_No_Connection);

        tv_Title.setTypeface(appFontRegular);
        tv_No_Internet.setTypeface(appFontLight);
        btn_No_Connection.setTypeface(appFontRegular);

        btn_No_Connection.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_No_Connection:
                dismiss();
                break;
        }
    }
}