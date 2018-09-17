package in.semicolonindia.schoolcrm.teacher.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.activities.NotificationActivity;
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.services.MessageService;
import in.semicolonindia.schoolcrm.sliding.SlidingTabLayout;
import in.semicolonindia.schoolcrm.student.adapters.StudentHomeViewPagerAdapter;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherHomeViewPagerAdapter;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sHolidayURL;
@SuppressWarnings("ALL")
public class TeacherHomeActivity extends AppCompatActivity {
    CircleImageView civUserHomePic;
    private TextView tvUserFullName;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_home);

        startService(new Intent(getApplicationContext(), MessageService.class));

        tvUserFullName = (TextView) findViewById(R.id.tvUserFullName);
        final TextView tvSchoolName = (TextView) findViewById(R.id.tvSchoolName);
        final TextView tvCITY = (TextView) findViewById(R.id.tvCITY);
        civUserHomePic = (CircleImageView) findViewById(R.id.civ_profileImage);

        final ImageButton imgBtnNotification = (ImageButton) findViewById(R.id.imgBtnNotification);
        Typeface appFontRegular = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(getAssets(), "fonts/montserrat_light.ttf");
        tvUserFullName.setTypeface(appFontRegular);
        tvSchoolName.setTypeface(appFontLight);
        tvCITY.setTypeface(appFontLight);

        Picasso.with(this).load(new PreferencesManager(getApplicationContext()).getImage()).into(civUserHomePic);
        tvUserFullName.setText(new PreferencesManager(getApplicationContext()).getName());

        final SlidingTabLayout stLibrary = (SlidingTabLayout) findViewById(R.id.stLibrary);
        final ViewPager vpLibrary = (ViewPager) findViewById(R.id.vpLibrary);

        final TeacherHomeViewPagerAdapter mhomeVPAdapter = new TeacherHomeViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        stLibrary.setCustomTabView(R.layout.tab_title_item, R.id.tvTabName);
        stLibrary.setDistributeEvenly(true);
        vpLibrary.setAdapter(mhomeVPAdapter);
        try {
            stLibrary.setViewPager(vpLibrary);
        } catch (Exception e) {
            Log.i("FF", e.getMessage());
        }

        imgBtnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Here we create a method to check Internet avalale or not.
                if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
                    getNotification();
                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(TeacherHomeActivity.this,
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();

                }
            }
        });
    }


    private void getNotification() {
        mProgressDialog = new ProgressDialog(TeacherHomeActivity.this, "Loading Notification...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sHolidayURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {

                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("holiday");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sFromDate = new String[jsonArray.length()];
                            String[] sToDate = new String[jsonArray.length()];
                            String[] sDescription = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sFromDate[i] = jsonArray.getJSONObject(i).getString("from_date");
                                sToDate[i] = jsonArray.getJSONObject(i).getString("to_date");
                                sDescription[i] = jsonArray.getJSONObject(i).getString("description");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("from_date", sFromDate);
                            mBundle.putStringArray("to_date", sToDate);
                            mBundle.putStringArray("description", sDescription);

                            Intent mIntent = new Intent(getApplicationContext(), NotificationActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_holiday");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (new PreferencesManager(getApplicationContext()).getLogedIn()) {

        } else {
            finish();
        }
    }
}










