package in.semicolonindia.schoolcrm.teacher.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

import in.semicolonindia.schoolcrm.LocationDetector;
import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.student.beans.FirstName;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherAccountActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherAssignmentFirstActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherAttendanceActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherCalenderActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherClassRoutinActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherExamMarksActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherLibraryActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherMessageActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherNoticeboardActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherStudyMaterialActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherSubjectActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherSyllabusActivity;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherHomeMenuAdapter;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetClassURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetNoticeboardURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetStudyMaterialURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetSubjectTeacherURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetSyllabusURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tHolidayURL;

/**
 * Created by Rupesh on 20-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherHomeMenuFragment extends Fragment implements LocationListener {

    private final int PERMISSION_REQUEST_CODE = 1;
    private final int REQUEST_WRITE_STORAGE = 10;
    private final int REQUEST_WRITE_STORAGE_1 = 11;
    private final int REQUEST_CHECK_SETTINGS = 0x1;
    private final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    ListView lv_First;
    private ProgressDialog mProgressDialog;
    // private Context context;
    private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private TeacherHomeMenuAdapter homeMenuAdapter;
    private LocationManager locationManager;
    private boolean GPS_ENABLED, PASSIVE_ENABLED;
    private double dLat;
    private double dLng;
    /*Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                    // updateGPSStatus("GPS is Enabled in your device");
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showSettingDialog();
                        }
                    }, 10);
                    // updateGPSStatus("GPS is Disabled in your device");
                    Log.e("About GPS", "GPS is Disabled in your device");
                }
            }
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            @SuppressLint("MissingPermission") Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                onLocationChanged(loc);
            } else {
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (loc != null)
                    onLocationChanged(loc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.teacher_fragment_home_menu, container, false);
        lv_First = rootView.findViewById(R.id.lv_First);
        TeacherHomeMenuAdapter homeMenuAdapter = new TeacherHomeMenuAdapter(getContext(),
                getResources().getStringArray(R.array.teacher_home_menu_item_fragment));
        lv_First.setAdapter(homeMenuAdapter);
        lv_First.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), TeacherAssignmentFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), TeacherAttendanceActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 2:
                            getTeacherSubjects();
                            break;
                        case 3:
                            startActivity(new Intent(getActivity(), TeacherClassRoutinActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 4:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ContextCompat.checkSelfPermission(getActivity(),
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                                } else {
                                    getTeacherStudyMaterial();
                                }
                            } else {
                                getTeacherStudyMaterial();
                            }
                            break;

                        case 5:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                            } else {
                                TeachergetSyllabus();
                            }
                            break;

                        case 6:
                            startActivity(new Intent(getActivity(), TeacherExamMarksActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 7:
                            getTeacherAssignedClassIDs();
                            break;
                        case 8:
                            getTeacherNoticeBoard();
                            break;
                        case 9:
                            startActivity(new Intent(getActivity(), TeacherMessageActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 10:
                            getTeacherCalendarNotification();
                            break;
                        case 11:
                            startActivity(new Intent(getActivity(), TeacherAccountActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                    }
                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(getActivity(),
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });


        return rootView;
    }

    private void getTeacherSubjects() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading subject...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetSubjectTeacherURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("subject");
                            String[] sNames = new String[jsonArray.length()];
                            String[] sYear = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sNames[i] = jsonArray.getJSONObject(i).getString("name");
                                sYear[i] = jsonArray.getJSONObject(i).getString("year");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("name", sNames);
                            mBundle.putStringArray("year", sYear);
                            Intent mIntent = new Intent(getActivity(), TeacherSubjectActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_teacher_subjects");
                params.put("teacher_id", new PreferencesManager(getActivity()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void TeachergetSyllabus() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading syllabus...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetSyllabusURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("syllabus");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sSubject = new String[jsonArray.length()];
                            String[] sUploader = new String[jsonArray.length()];
                            String[] sDate = new String[jsonArray.length()];
                            String[] sFile = new String[jsonArray.length()];
                            String[] sDesp = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sSubject[i] = jsonArray.getJSONObject(i).getString("subject_name");
                                sDate[i] = jsonArray.getJSONObject(i).getString("year");
                                sFile[i] = jsonArray.getJSONObject(i).getString("file_name");
                                sDesp[i] = jsonArray.getJSONObject(i).getString("description");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("subject_name", sSubject);
                            mBundle.putStringArray("year", sDate);
                            mBundle.putStringArray("file_name", sFile);
                            mBundle.putStringArray("description", sDesp);

                            Intent mIntent = new Intent(getActivity(), TeacherSyllabusActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_syllabus");
                //  String s = new PreferencesManager(getApplicationContext()).getClassID();
                params.put("teacher_id", new PreferencesManager(getActivity()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getTeacherAssignedClassIDs() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading library...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetClassURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        String sTemp = "";
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("class");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getString("teacher_id").equalsIgnoreCase
                                        (new PreferencesManager(getActivity()).getUserID())) {
                                    sTemp = sTemp + jsonArray.getJSONObject(i).getString("class_id") + ",";
                                }
                            }
                            if (sTemp.contains(",")) {
                                sTemp = sTemp.substring(0, sTemp.length() - 1);
                                new PreferencesManager(getActivity()).setClassIDs(sTemp);
                                Intent mIntent = new Intent(getActivity(), TeacherLibraryActivity.class);
                                startActivity(mIntent);
                                getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            } else {
                                Toast.makeText(getActivity(), "No class assigned to your Id.\nPlease contact Administrater for assignment of class.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_class");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getTeacherStudyMaterial() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading study material...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetStudyMaterialURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("study_material");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sDesc = new String[jsonArray.length()];
                            String[] sSubject = new String[jsonArray.length()];
                            String[] sClassName = new String[jsonArray.length()];
                            String[] sDate = new String[jsonArray.length()];
                            String[] sFile = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sSubject[i] = jsonArray.getJSONObject(i).getString("subject_name");
                                sClassName[i] = jsonArray.getJSONObject(i).getString("class_name");
                                sDate[i] = jsonArray.getJSONObject(i).getString("date_added");
                                sFile[i] = jsonArray.getJSONObject(i).getString("file_name");
                                sDesc[i] = jsonArray.getJSONObject(i).getString("description");

                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("subject_name", sSubject);
                            mBundle.putStringArray("class_name", sClassName);
                            mBundle.putStringArray("date_added", sDate);
                            mBundle.putStringArray("file_name", sFile);
                            mBundle.putStringArray("description", sDesc);


                            Intent mIntent = new Intent(getActivity(), TeacherStudyMaterialActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_teacher_study_material");
                params.put("teacher_id", new PreferencesManager(getActivity()).getLogintype());
                //params.put("teacher_id", new PreferencesManager(getActivity()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getTeacherNoticeBoard() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading noticeboard...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetNoticeboardURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("noticeboard");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sNotice = new String[jsonArray.length()];
                            String[] sDate = new String[jsonArray.length()];
                            // String sResultDate = sDate.split(" ")[0];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("notice_title");
                                sNotice[i] = jsonArray.getJSONObject(i).getString("notice");
                                sDate[i] = jsonArray.getJSONObject(i).getString("date_added");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("notice_title", sTitle);
                            mBundle.putStringArray("notice", sNotice);
                            mBundle.putStringArray("date_added", sDate);
                            Intent mIntent = new Intent(getActivity(), TeacherNoticeboardActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_event_calendar");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getTeacherCalendarNotification() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Calendar...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tHolidayURL, new Response.Listener<String>() {
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

                            Intent mIntent = new Intent(getActivity(), TeacherCalenderActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gpsLocationReceiver != null)
            getActivity().unregisterReceiver(gpsLocationReceiver);
        getActivity().overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    @Override
    public void onLocationChanged(Location location) {
        dLat = location.getLatitude();
        dLng = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();
    }

    /* Show Location Access Dialog */
    private void showSettingDialog() {
        LocationDetector mLocationDetector = new LocationDetector(getActivity());
        mLocationDetector.showSettingDialog();
    }


    /*  Show Popup to access User Permission  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("Settings", "Result OK");
                        break;
                    case RESULT_CANCELED:
                        Log.e("Settings", "Result Cancel");
                        getActivity().finish();
                        System.exit(0);
                        break;
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Register broadcast receiver to check the status of GPS
        getActivity().registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    //On Request permission method to check the permisison is granted or not for Marshmallow+ Devices
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //If permission granted show location dialog if APIClient is not null
                    showSettingDialog();
                } else {
                    Log.e("About GPS", "Location Permission denied.");
                    // Toast.makeText(SplashActivity.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case REQUEST_WRITE_STORAGE:
                //If permission is granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TeachergetSyllabus();
                } else {
                    Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_WRITE_STORAGE_1:
                //If permission is granted
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getTeacherStudyMaterial();
                } else {
                    Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
