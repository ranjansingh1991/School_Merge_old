package in.semicolonindia.schoolcrm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetChangePasswordURL;


/**
 * Created by Rupesh on 06-08-2017.
 */
@SuppressWarnings("ALL")
public class PasswordChangeDialog extends Dialog implements View.OnClickListener {

    private EditText etPwdOld;
    private EditText etNewPwd;
    private Activity activity;

    public PasswordChangeDialog(Activity activity, int themeResId) {
        super(activity, themeResId);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_dialog_change_password);
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        final TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        etPwdOld = (EditText) findViewById(R.id.etPwdOld);
        etNewPwd = (EditText) findViewById(R.id.etNewPwd);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        tvTitle.setTypeface(appFontRegular);
        etPwdOld.setTypeface(appFontLight);
        etNewPwd.setTypeface(appFontLight);
        btnCancel.setTypeface(appFontRegular);
        btnSubmit.setTypeface(appFontRegular);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        tvTitle.setMinWidth(displayMetrics.widthPixels - 32);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnSubmit:
                // 1. Here we create a method to check Internet avalale or not.
                if (new ConnectionDetector(getContext()).isConnectingToInternet()) {
                    //methods Calling.....
                    changePassword();
                    //2.....
                }else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(getContext(),
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                    dismiss();
                    break;
                }
        }
    }

    private void changePassword() {
        final String currPassword = etPwdOld.getText().toString();
        final String newPassword = etNewPwd.getText().toString();
        if (etPwdOld.getText().toString().trim().length() < 1) {
            etPwdOld.setError("Please enter current password");
        } else if (etPwdOld.getText().toString().equals(etNewPwd.getText().toString())) {
            etNewPwd.setError("New password woun't be the same as current password");
        } else if (etNewPwd.getText().toString().trim().length() < 8) {
            etNewPwd.setError("Password must be minimum of 8 characters");
        } else {
            final ProgressDialog mProgressDialog = new ProgressDialog(activity, activity.getApplicationContext()
                    .getResources().getString(R.string.please_wait));
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetChangePasswordURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String sResult) {
                            if (sResult != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(sResult);
                                    Toast.makeText(activity, "Updated successfully", Toast.LENGTH_LONG).show();
                                    mProgressDialog.dismiss();
                                    dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mProgressDialog.dismiss();
                                }
                            } else {
                                Toast.makeText(activity, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                                mProgressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //Dismissing the progress dialog
                    Log.e("status Response", String.valueOf(volleyError));
                    mProgressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("login_type", "teacher");
                    params.put("old_password", currPassword);
                    params.put("new_password", newPassword);
                    params.put("login_user_id", new PreferencesManager(activity).getUserID());
                    params.put("authenticate", "false");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(stringRequest);
        }
    }
}