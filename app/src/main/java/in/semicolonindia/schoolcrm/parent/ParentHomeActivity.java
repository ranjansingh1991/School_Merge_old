package in.semicolonindia.schoolcrm.parent;

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
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.parent.activities.ParentCalenderActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentNotificationNewActivity;
import in.semicolonindia.schoolcrm.parent.adapters.ParentHomeViewPagerAdapter;
import in.semicolonindia.schoolcrm.services.MessageService;
import in.semicolonindia.schoolcrm.sliding.SlidingTabLayout;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pHolidayURL;
@SuppressWarnings("ALL")
public class ParentHomeActivity extends AppCompatActivity {
    ProgressDialog mProgressDialog;
    CircleImageView civ_profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new PreferencesManager(getApplicationContext()).getLogedIn()) {
            setContentView(R.layout.parent_activity_home);
            //Strat Msg service here
            startService(new Intent(getApplicationContext(), MessageService.class));

            //....Initializing All variable Here.....

            final TextView tvUserFullName = (TextView) findViewById(R.id.tvUserFullName);
            final TextView tvSchoolName = (TextView) findViewById(R.id.tvSchoolName);
            final TextView tvCITY = (TextView) findViewById(R.id.tvCITY);
            final ImageButton imgBtnNotification = (ImageButton) findViewById(R.id.imgBtnNotification);
            civ_profileImage = (CircleImageView) findViewById(R.id.civ_profileImage);
            //
            Picasso.with(this).load(new PreferencesManager(getApplicationContext()).getImage()).into(civ_profileImage);
            tvUserFullName.setText(new PreferencesManager(getApplicationContext()).getName());

            // .... Calling Methods...
            //getUserImage();

            Typeface appFontRegular = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
            Typeface appFontLight = Typeface.createFromAsset(getAssets(), "fonts/montserrat_light.ttf");
            tvUserFullName.setTypeface(appFontRegular);
            tvSchoolName.setTypeface(appFontLight);
            tvCITY.setTypeface(appFontLight);

            final SlidingTabLayout stLibrary = (SlidingTabLayout) findViewById(R.id.stLibrary);
            final ViewPager vpLibrary = (ViewPager) findViewById(R.id.vpLibrary);

            final ParentHomeViewPagerAdapter mhomeVPAdapter = new ParentHomeViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
            stLibrary.setCustomTabView(R.layout.tab_title_item, R.id.tvTabName);
            stLibrary.setDistributeEvenly(true);
            vpLibrary.setAdapter(mhomeVPAdapter);
            try {
                stLibrary.setViewPager(vpLibrary);
            } catch (Exception e) {
                Log.i("FF", e.getMessage());
            }
            //...Notification ClickListener.....
            imgBtnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
                        getNotification();
                    } else {
                        final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(ParentHomeActivity.this,
                                R.style.DialogSlideAnim);
                        mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mNoConnectionDialog.setCancelable(false);
                        mNoConnectionDialog.setCanceledOnTouchOutside(false);
                        mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                        mNoConnectionDialog.show();

                    }
                }
            });
        } else {
            finish();
        }
    }

    private void getNotification() {
        mProgressDialog = new ProgressDialog(ParentHomeActivity.this, "Loading Notification ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pHolidayURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    //
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

                            Intent mIntent = new Intent(getApplicationContext(), ParentNotificationNewActivity.class);
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

                Log.e("status Response", String.valueOf(volleyError));
                mProgressDialog.dismiss();
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


    private void getCalendarNotification() {
        mProgressDialog = new ProgressDialog(ParentHomeActivity.this, "Loading Calendar...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pHolidayURL, new Response.Listener<String>() {
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

                            Intent mIntent = new Intent(getApplicationContext(), ParentCalenderActivity.class);
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

