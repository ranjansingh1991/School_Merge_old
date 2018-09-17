package in.semicolonindia.schoolcrm.parent.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.student.adapters.StudentDayNamesListAdapter;
import in.semicolonindia.schoolcrm.student.adapters.StudentRoutinSubjectsHorzListAdapter;
import in.semicolonindia.schoolcrm.student.beans.StudentClassRoutine;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetRoutineURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetSubjectURL;
@SuppressWarnings("ALL")
public class ParentClassRoutingNextActivity extends AppCompatActivity {
    private String sClassID;
    private String sSectionID;
    private static ArrayList<ArrayList<StudentClassRoutine>> alRoutine = new ArrayList<>();
    private static int nDayCount = 0;
    private ListView lvDays;
    private RecyclerView rvSubjectRoutine;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_class_routing_next);
        Bundle mBundle = this.getIntent().getExtras();
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        final Typeface appFontRegular = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        final Typeface appFontLight = Typeface.createFromAsset(getAssets(), "fonts/montserrat_light.ttf");

        tvToolbarTitle.setText(mBundle.getString("name"));

        sClassID = mBundle.getString("class_id");
        sSectionID = mBundle.getString("section_id");

        lvDays = (ListView) findViewById(R.id.lvDays);
        final LinearLayout llRoutineParent = (LinearLayout) findViewById(R.id.llRoutineParent);

        rvSubjectRoutine = (RecyclerView) findViewById(R.id.rvSubjectRoutine);

        final TextView tvRoutineMsg = (TextView) findViewById(R.id.tvRoutineMsg);
        final TextView tvRoutineTitle = (TextView) findViewById(R.id.tvRoutineTitle);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.class_routine_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentClassRoutingNextActivity.this, ParentClassRoutinActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        tvRoutineMsg.setTypeface(appFontLight);
        tvRoutineTitle.setTypeface(appFontRegular);
        mProgressDialog = new ProgressDialog(ParentClassRoutingNextActivity.this, "Loading Class Routing...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        getSubjects();
    }

    private void getRoutine() {
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pGetRoutineURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            final String[] sSubjectNames = new PreferencesManager(getApplicationContext()).getSubjectNames().split(",");
                            ArrayList<StudentClassRoutine> alClsRoutime = new ArrayList<>();
                            if (jsonObject.getJSONArray("routine").length() > 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("routine");
                                String[] sSubFromServer = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    boolean found = false;
                                    StudentClassRoutine mClassRoutine = new StudentClassRoutine();
                                    mClassRoutine.setDayName(jsonArray.getJSONObject(i).getString("day"));
                                    String sStartTime = jsonArray.getJSONObject(i).getString("time_start") + ":"
                                            + jsonArray.getJSONObject(i).getString("time_start_min");
                                    String sEndTime = jsonArray.getJSONObject(i).getString("time_end") + ":"
                                            + jsonArray.getJSONObject(i).getString("time_end_min");
                                    if (!jsonArray.getJSONObject(i).getString("time_start").contains("-")) {
                                        if (Integer.parseInt(jsonArray.getJSONObject(i).getString("time_start")) < 13)
                                            sStartTime = sStartTime + "AM";
                                        else sStartTime = sStartTime + "PM";
                                        if (Integer.parseInt(jsonArray.getJSONObject(i).getString("time_end")) < 13)
                                            sEndTime = sEndTime + "AM";
                                        else sEndTime = sEndTime + "PM";
                                    }
                                    mClassRoutine.setStartEndTime(sStartTime + "\n" + sEndTime);
                                    mClassRoutine.setSubjectName(jsonArray.getJSONObject(i).getString("subject"));
                                    alClsRoutime.add(mClassRoutine);
                                }
                            } else {
                                for (int i = 0; i < sSubjectNames.length; i++) {
                                    StudentClassRoutine mClassRoutine = new StudentClassRoutine();
                                    mClassRoutine.setDayName("-");
                                    mClassRoutine.setStartEndTime("-\n-");
                                    mClassRoutine.setSubjectName(sSubjectNames[i]);
                                    alClsRoutime.add(mClassRoutine);
                                }
                            }
                            alRoutine.add(alClsRoutime);
                            nDayCount++;
                            if (nDayCount < 7) {
                                getRoutine();
                            } else {
                                mProgressDialog.dismiss();
                                final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                                final int LIST_HEIGHT = (displayMetrics.heightPixels - (displayMetrics.heightPixels - 160));
                                final StudentDayNamesListAdapter mStudentDayNamesListAdapter = new StudentDayNamesListAdapter(getApplicationContext(), LIST_HEIGHT);
                                final StudentRoutinSubjectsHorzListAdapter mStudentRoutinSubjectsHorzListAdapter = new StudentRoutinSubjectsHorzListAdapter(getApplicationContext(), LIST_HEIGHT, alRoutine);
                                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                lvDays.setAdapter(mStudentDayNamesListAdapter);
                                rvSubjectRoutine.setLayoutManager(layoutManager);
                                rvSubjectRoutine.setAdapter(mStudentRoutinSubjectsHorzListAdapter);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
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
                Log.e("status Response", String.valueOf(volleyError));
                mProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_routine");
                params.put("class_id", sClassID);
                params.put("section_id", sSectionID);
                params.put("authenticate", "false");
                switch (nDayCount) {
                    case 0:
                        params.put("day", "sunday");
                        break;
                    case 1:
                        params.put("day", "monday");
                        break;
                    case 2:
                        params.put("day", "tuesday");
                        break;
                    case 3:
                        params.put("day", "wednesday");
                        break;
                    case 4:
                        params.put("day", "thursday");
                        break;
                    case 5:
                        params.put("day", "friday");
                        break;
                    case 6:
                        params.put("day", "saturday");
                        break;
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getSubjects() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pGetSubjectURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        String sTemp = "";
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("subject");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTemp = sTemp + jsonArray.getJSONObject(i).getString("name") + ",";
                            }
                            if (sTemp.contains(",")) {
                                sTemp = sTemp.substring(0, sTemp.length() - 1);
                                new PreferencesManager(getApplicationContext()).setSubjectNames(sTemp);
                            }
                            getRoutine();
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_subject");
                params.put("class_id", sClassID);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(ParentClassRoutingNextActivity.this,ParentClassRoutinActivity.class);
        startActivity(intent);
        finish();
    }
}
