package in.semicolonindia.schoolcrm.teacher.activities;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherDayNamesListAdapter;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherRoutinSubjectsHorzListAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherClassRoutine;
import in.semicolonindia.schoolcrm.util.PreferencesManager;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetRoutineURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetSubjectURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetClassURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetRoutineURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetSubjectURL;

@SuppressWarnings("ALL")
public class TeacherClassRoutinActivity extends AppCompatActivity {

    // ArrayList<TeacherSubjectNames> arraylist = new ArrayList<TeacherSubjectNames>();
    private static ArrayList<ArrayList<TeacherClassRoutine>> alRoutine = new ArrayList<>();
    private static String[] sClassIDs;
    private static String[] sSectionId;
    private static String sNameClass;
    private static String sSectionID;
    private static int nDayCount = 0;
    ProgressDialog mProgressDialog;
    private Spinner spinner_class_routing;
    private Spinner spinner_section_routing;
    private ListView lvDays;
    private RecyclerView rvSubjectRoutine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_class_routin);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        final Typeface appFontRegular = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        final Typeface appFontLight = Typeface.createFromAsset(getAssets(), "fonts/montserrat_light.ttf");

        lvDays = (ListView) findViewById(R.id.lvDays);
        final LinearLayout llRoutineParent = (LinearLayout) findViewById(R.id.llRoutineParent);

        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final int LIST_HEIGHT = (displayMetrics.heightPixels - (displayMetrics.heightPixels - 160));
        final TeacherDayNamesListAdapter mTeacherDayNamesListAdapter = new TeacherDayNamesListAdapter(getApplicationContext(), LIST_HEIGHT);

        rvSubjectRoutine = (RecyclerView) findViewById(R.id.rvSubjectRoutine);
        // final TeacherRoutinSubjectsHorzListAdapter mRoutinSubjectsHorzListAdapter = new TeacherRoutinSubjectsHorzListAdapter(getApplicationContext(), LIST_HEIGHT);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        final TextView tvRoutineMsg = (TextView) findViewById(R.id.tvRoutineMsg);
        final TextView tvRoutineTitle = (TextView) findViewById(R.id.tvRoutineTitle);
        spinner_class_routing = (Spinner) findViewById(R.id.spinner_class_routing);
        spinner_section_routing = (Spinner) findViewById(R.id.spinner_section_routing);

        spinner_class_routing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sNameClass = sClassIDs[pos];
                getSection(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_section_routing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sSectionID = sSectionId[pos];
                // getSubject();
                getSubjects();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                finish();
            }
        });
        lvDays.setAdapter(mTeacherDayNamesListAdapter);
        // rvSubjectRoutine.setLayoutManager(layoutManager);
        // rvSubjectRoutine.setAdapter(mRoutinSubjectsHorzListAdapter);
        tvRoutineMsg.setTypeface(appFontLight);
        tvRoutineTitle.setTypeface(appFontRegular);

        getClassDetails();
    }

    private void getClassDetails() {
        mProgressDialog = new ProgressDialog(TeacherClassRoutinActivity.this, getApplicationContext()
                .getResources().getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetClassURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("class");
                            String[] classes = new String[jsonArray.length()];
                            sClassIDs = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                classes[i] = jsonArray.getJSONObject(i).getString("name");
                                sClassIDs[i] = jsonArray.getJSONObject(i).getString("class_id");
                            }
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                                    R.layout.spinner_text, classes);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            spinner_class_routing.setAdapter(classAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
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
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_class");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getSection(int pos) {
        mProgressDialog = new ProgressDialog(TeacherClassRoutinActivity.this, getApplicationContext()
                .getResources().getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetClassURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("class");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getString("name")
                                        .equalsIgnoreCase(spinner_class_routing.getSelectedItem().toString())) {
                                    sSectionId = new String[jsonArray.getJSONObject(i).getJSONArray("sections").length()];
                                    String[] sections = new String[jsonArray.getJSONObject(i).getJSONArray("sections").length()];
                                    for (int j = 0; j < sections.length; j++) {
                                        sections[j] = jsonArray.getJSONObject(i).getJSONArray("sections").getJSONObject(j).getString("name");
                                        sSectionId[j] = jsonArray.getJSONObject(i).getJSONArray("sections").getJSONObject(j).getString("section_id");
                                    }
                                    try {
                                        ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                                                R.layout.spinner_text, sections);
                                        classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                        spinner_section_routing.setAdapter(classAdapter);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
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
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_class");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getSubjects() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetSubjectURL, new Response.Listener<String>() {
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
                params.put("class_id", sNameClass);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getRoutine() {
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetRoutineURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            final String[] sSubjectNames = new PreferencesManager(getApplicationContext()).getSubjectNames().split(",");
                            ArrayList<TeacherClassRoutine> alClsRoutime = new ArrayList<>();
                            if (jsonObject.getJSONArray("routine").length() > 0) {
                                JSONArray jsonArray = jsonObject.getJSONArray("routine");
                                String[] sSubFromServer = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    boolean found = false;
                                    TeacherClassRoutine mTeacherClassRoutine = new TeacherClassRoutine();
                                    mTeacherClassRoutine.setDayName(jsonArray.getJSONObject(i).getString("day"));
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
                                    mTeacherClassRoutine.setStartEndTime(sStartTime + "\n" + sEndTime);
                                    mTeacherClassRoutine.setSubjectName(jsonArray.getJSONObject(i).getString("subject"));
                                    alClsRoutime.add(mTeacherClassRoutine);
                                }
                            } else {
                                for (int i = 0; i < sSubjectNames.length; i++) {
                                    TeacherClassRoutine mTeacherClassRoutine = new TeacherClassRoutine();
                                    mTeacherClassRoutine.setDayName("-");
                                    mTeacherClassRoutine.setStartEndTime("-\n-");
                                    mTeacherClassRoutine.setSubjectName(sSubjectNames[i]);
                                    alClsRoutime.add(mTeacherClassRoutine);
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
                                final TeacherDayNamesListAdapter mTeacherDayNamesListAdapter = new TeacherDayNamesListAdapter(getApplicationContext(), LIST_HEIGHT);
                                final TeacherRoutinSubjectsHorzListAdapter mTeacherRoutinSubjectsHorzListAdapter = new TeacherRoutinSubjectsHorzListAdapter(getApplicationContext(), LIST_HEIGHT, alRoutine);
                                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                                lvDays.setAdapter(mTeacherDayNamesListAdapter);
                                rvSubjectRoutine.setLayoutManager(layoutManager);
                                rvSubjectRoutine.setAdapter(mTeacherRoutinSubjectsHorzListAdapter);
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
                params.put("tag", "get_class_routine");
                params.put("class_id", sNameClass);
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

}