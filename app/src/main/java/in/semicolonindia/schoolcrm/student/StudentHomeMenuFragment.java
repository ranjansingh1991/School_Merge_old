package in.semicolonindia.schoolcrm.student;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;


import in.semicolonindia.schoolcrm.activities.CalenderActivity;
import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.activities.NotificationActivity;
import in.semicolonindia.schoolcrm.student.activities.AccountActivity;
import in.semicolonindia.schoolcrm.student.activities.AssignmentActivity;
import in.semicolonindia.schoolcrm.student.activities.AttendanceActivity;
import in.semicolonindia.schoolcrm.student.activities.ClassRoutineActivity;
import in.semicolonindia.schoolcrm.student.activities.ExamMarksActivity;
import in.semicolonindia.schoolcrm.student.activities.LibraryActivity;
import in.semicolonindia.schoolcrm.activities.NoticeboardActivity;
import in.semicolonindia.schoolcrm.student.activities.MessageActivity;
import in.semicolonindia.schoolcrm.student.activities.PaymentsActivity;
import in.semicolonindia.schoolcrm.student.activities.StudyMatActivity;
import in.semicolonindia.schoolcrm.student.activities.SubjectActivity;
import in.semicolonindia.schoolcrm.student.activities.SyllabusActivity;
import in.semicolonindia.schoolcrm.student.activities.TeachersActivity;
import in.semicolonindia.schoolcrm.student.activities.TransportActivity;
import in.semicolonindia.schoolcrm.student.adapters.StudentHomeMenuAdapter;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetAssignmentURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetNoticeboardURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetPaymentURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetStudyMaterialURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetSubjectURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetSyllabusURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetTransportURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sHolidayURL;

/**
 * Created by Rupesh on 01-02-2018.
 */

@SuppressWarnings("ALL")
public class StudentHomeMenuFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    // private Activity aActivity;
    //private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private StudentHomeMenuAdapter studentHomeMenuAdapter;
    ListView HomeMenu_List;
    private static String sFilePath = "";
    private static String sFileName = "";

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_WRITE_STORAGE = 10;
    private static final int REQUEST_WRITE_STORAGE_1 = 11;
    private static final int REQUEST_WRITE_STORAGE_2 = 12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_home_list_menu, container, false);
        HomeMenu_List = rootView.findViewById(R.id.lv_First);

        StudentHomeMenuAdapter studentHomeMenuAdapter = new StudentHomeMenuAdapter((Activity) getContext(),
                getResources().getStringArray(R.array.student_home_menu_item_fragment));
        HomeMenu_List.setAdapter(studentHomeMenuAdapter);

        HomeMenu_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {
                        case 0:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ContextCompat.checkSelfPermission(getActivity(),
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                                } else {
                                    getAssignment();
                                }
                            } else {
                                getAssignment();
                            }
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), AttendanceActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 2:
                            startActivity(new Intent(getActivity(), TeachersActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 3:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE_1);
                            } else {
                                getSyllabus();
                            }
                            break;
                        case 4:
                            getSubjects();
                            break;
                        case 5:
                            startActivity(new Intent(getActivity(), ClassRoutineActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 6:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE_2);
                            } else {
                                getStudyMaterial();
                            }
                            break;
                        case 7:
                            startActivity(new Intent(getActivity(), ExamMarksActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 8:
                            getPaymentDetails();
                            break;
                        case 9:
                            startActivity(new Intent(getActivity(), LibraryActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 10:
                            getTransportList();
                            break;
                        case 11:
                            getNoticeBoard();
                            break;
                        case 12:
                            startActivity(new Intent(getActivity(), MessageActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 13:
                            getCalendarNotification();
                            break;
                        case 14:
                            startActivity(new Intent(getActivity(), AccountActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            getActivity().finish();
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

    private void getAssignment() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Assignment...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetAssignmentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("assignment");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sQuestion = new String[jsonArray.length()];
                            String[] sUploadDate = new String[jsonArray.length()];
                            String[] sSubmitDate = new String[jsonArray.length()];
                            String[] sMarks = new String[jsonArray.length()];
                            String[] sReport = new String[jsonArray.length()];
                            String[] sID = new String[jsonArray.length()];
                            String[] sFile = new String[jsonArray.length()];
                            //String[] sfile_type = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject assignmentObject = jsonArray.getJSONObject(i);
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sQuestion[i] = jsonArray.getJSONObject(i).getString("description");
                                sUploadDate[i] = jsonArray.getJSONObject(i).getString("upload_date");
                                sSubmitDate[i] = jsonArray.getJSONObject(i).getString("submit_date");
                                sMarks[i] = jsonArray.getJSONObject(i).getString("marks");
                                sReport[i] = jsonArray.getJSONObject(i).getString("report_to");
                                sID[i] = jsonArray.getJSONObject(i).getString("class_id");
                                sFile[i] = jsonArray.getJSONObject(i).getString("file_name");
                                //sfile_type[i] = jsonArray.getJSONObject(i).getString("file_type");
                            }

                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("description", sQuestion);
                            mBundle.putStringArray("upload_date", sUploadDate);
                            mBundle.putStringArray("submit_date", sSubmitDate);
                            mBundle.putStringArray("marks", sMarks);
                            mBundle.putStringArray("report_to", sReport);
                            mBundle.putStringArray("class_id", sID);
                            mBundle.putStringArray("file_name", sFile);
                            //mBundle.putStringArray("file_type", sfile_type);

                            Intent mIntent = new Intent(getActivity(), AssignmentActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                        } else {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                    getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

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
                params.put("tag", "get_assignment");
             /*   String[] temp = sFileName.split("\\.");
                String s = sFilePath;
                s = s.replace("\n", "+");
                params.put("file_name", s);
                params.put("file_type", "." + temp[0]);*/
                params.put("class_id", new PreferencesManager(getActivity()).getClassID());
                params.put("student_id", new PreferencesManager(getActivity()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getNotification() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Notification...");
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

                            Intent mIntent = new Intent(getActivity(), NotificationActivity.class);
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


    private void getSyllabus() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Syllabus...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetSyllabusURL, new Response.Listener<String>() {
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
                            String[] sDesp = new String[jsonArray.length()];
                            String[] sFile = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sSubject[i] = jsonArray.getJSONObject(i).getString("subject_name");
                                sUploader[i] = jsonArray.getJSONObject(i).getString("uploader_type");
                                sDate[i] = jsonArray.getJSONObject(i).getString("year");
                                sFile[i] = jsonArray.getJSONObject(i).getString("file_name");
                                sDesp[i] = jsonArray.getJSONObject(i).getString("description");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("subject_name", sSubject);
                            mBundle.putStringArray("uploader_type", sUploader);
                            mBundle.putStringArray("year", sDate);
                            mBundle.putStringArray("file_name", sFile);
                            mBundle.putStringArray("description", sDesp);

                            Intent mIntent = new Intent(getActivity(), SyllabusActivity.class);
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
                params.put("tag", "get_syllabus");
                //  String s = new PreferencesManager(getApplicationContext()).getClassID();
                params.put("class_id", new PreferencesManager(getActivity()).getClassID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getSubjects() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Subject...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetSubjectURL, new Response.Listener<String>() {
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
                            mBundle.putStringArray("sSubjectNames", sNames);
                            mBundle.putStringArray("sYear", sYear);
                            Intent mIntent = new Intent(getActivity(), SubjectActivity.class);
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
                params.put("tag", "get_subject");
                params.put("class_id", new PreferencesManager(getActivity()).getClassID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getPaymentDetails() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Payment...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetPaymentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {

                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("payment");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sDesc = new String[jsonArray.length()];
                            String[] sAmount = new String[jsonArray.length()];
                            String[] sStatus = new String[jsonArray.length()];
                            String[] sDate = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sDesc[i] = jsonArray.getJSONObject(i).getString("description");
                                sAmount[i] = jsonArray.getJSONObject(i).getString("amount");
                                sStatus[i] = jsonArray.getJSONObject(i).getString("status");
                                sDate[i] = jsonArray.getJSONObject(i).getString("year");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("description", sDesc);
                            mBundle.putStringArray("amount", sAmount);
                            mBundle.putStringArray("status", sStatus);
                            mBundle.putStringArray("year", sDate);

                            Intent mIntent = new Intent(getActivity(), PaymentsActivity.class);
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
                params.put("tag", "get_single_student_accounting");
                params.put("student_id", new PreferencesManager(getActivity()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getStudyMaterial() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Study Material...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetStudyMaterialURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {

                    try {
                        mProgressDialog.dismiss();
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
                            mBundle.putStringArray("fileName", sFile);
                            mBundle.putStringArray("description", sDesc);


                            Intent mIntent = new Intent(getActivity(), StudyMatActivity.class);
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
                params.put("tag", "get_study_material");
                // String s = new PreferencesManager(getApplicationContext()).getClassID();
                params.put("class_id", new PreferencesManager(getActivity()).getClassID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getTransportList() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Transport...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetTransportURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("transport");

                            String[] transport_id = new String[jsonArray.length()];

                            String[] sRouteName = new String[jsonArray.length()];
                            String[] sNoOfvehicles = new String[jsonArray.length()];
                            String[] sRouteFare = new String[jsonArray.length()];
                            String[] sDescription = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {

                                transport_id[i] = jsonArray.getJSONObject(i).getString("transport_id");

                                sRouteName[i] = jsonArray.getJSONObject(i).getString("route_name");
                                sNoOfvehicles[i] = jsonArray.getJSONObject(i).getString("number_of_vehicle");
                                sRouteFare[i] = jsonArray.getJSONObject(i).getString("route_fare");
                                sDescription[i] = jsonArray.getJSONObject(i).getString("description");
                            }
                            Bundle mBundle = new Bundle();

                            mBundle.putStringArray("transport_id", transport_id);

                            mBundle.putStringArray("route_name", sRouteName);
                            mBundle.putStringArray("number_of_vehicle", sNoOfvehicles);
                            mBundle.putStringArray("route_fare", sRouteFare);
                            mBundle.putStringArray("description", sDescription);

                            Intent mIntent = new Intent(getActivity(), TransportActivity.class);
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
                params.put("tag", "get_transports");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getNoticeBoard() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Noticeboard...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetNoticeboardURL, new Response.Listener<String>() {
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("notice_title");
                                sNotice[i] = jsonArray.getJSONObject(i).getString("notice");
                                sDate[i] = jsonArray.getJSONObject(i).getString("date_added");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("notice_title", sTitle);
                            mBundle.putStringArray("notice", sNotice);
                            mBundle.putStringArray("date_added", sDate);
                            Intent mIntent = new Intent(getActivity(), NoticeboardActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
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

    private void getCalendarNotification() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Calendar...");
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

                            Intent mIntent = new Intent(getActivity(), CalenderActivity.class);
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
        getActivity().overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == REQUEST_WRITE_STORAGE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAssignment();
            } else {
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_WRITE_STORAGE_1) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSyllabus();
            } else {
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_WRITE_STORAGE_2) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getStudyMaterial();
            } else {
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}