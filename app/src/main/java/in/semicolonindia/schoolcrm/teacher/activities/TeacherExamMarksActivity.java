package in.semicolonindia.schoolcrm.teacher.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherExamMarkAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherExamMarkNames;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetClassURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetStudentURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetSubject;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tMarksListURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tUpLoadMarksURL;
@SuppressWarnings("ALL")
public class TeacherExamMarksActivity extends AppCompatActivity implements View.OnClickListener {

    public static ArrayList<TeacherExamMarkNames> alExmMrks = new ArrayList<TeacherExamMarkNames>();
    private ArrayList<TeacherExamMarkNames> list;
    private static String[] sClassIDs;
    private static String sClassID;
    private Spinner sp_examMark_exam;
    private Spinner sp_examMark_class;
    private Spinner sp_examMark_section;
    private Spinner sp_examMark_subject;
    private RecyclerView rv_examMark;
    private LinearLayoutManager layoutManager;
    private Button btn_examSubmit;
    private String[] sExamIDs;
    private String[] sName;
    private String[] sRollNo;
    private String[] sClassId;
    private String[] sSectionId;
    private String[] sSubjectId;
    private String[] sExamId;
    private String sExamID;
    private String sSectionID;
    private String sSubjectID;
    private int mYear, mMonth, mDay;
    private String sTempCom;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_exam_marks);
        initialize();

        sp_examMark_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                getSection(pos);
                sClassID = sClassIDs[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_examMark_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sSectionID = sSectionId[pos];
                getSubject(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_examMark_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sSubjectID = sSubjectId[pos];
                getStudentDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_examMark_exam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sExamID = sExamIDs[pos];
                getClassDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sp_examMark_exam.setSelection(0);
                getClassDetails();
            }
        });
    }

    private void initialize() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.exam_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Initialise you date
        final Animation animationSlidUp = AnimationUtils.loadAnimation(TeacherExamMarksActivity.this, R.anim.slide_in_up);
        // Select Subject
        sp_examMark_subject = (Spinner) findViewById(R.id.sp_examMark_subject);
        // Select Class
        sp_examMark_class = (Spinner) findViewById(R.id.sp_examMark_class);
        // Select Section
        sp_examMark_section = (Spinner) findViewById(R.id.sp_examMark_section);
        sp_examMark_exam = (Spinner) findViewById(R.id.sp_examMark_exam);
        // Recycler View
        layoutManager = new LinearLayoutManager(TeacherExamMarksActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_examMark = (RecyclerView) findViewById(R.id.rv_examMark);
        rv_examMark.setLayoutManager(new LinearLayoutManager(TeacherExamMarksActivity.this));
        // Button Submit
        btn_examSubmit = (Button) findViewById(R.id.btn_examSubmit);
        btn_examSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadMarksDetails();
            }
        });
//
//        rv_examMark.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    btn_examSubmit.startAnimation(animationSlidUp);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0 || dy < 0)
//                    btn_examSubmit.startAnimation(animationSlidUp);
//            }
//        });
        getExamDetails();
    }

    private void getExamDetails() {
        mProgressDialog = new ProgressDialog(TeacherExamMarksActivity.this, "Loading Exam Marks ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tMarksListURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("exam");
                            String[] classes = new String[jsonArray.length()];
                            sExamIDs = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                classes[i] = jsonArray.getJSONObject(i).getString("name");
                                sExamIDs[i] = jsonArray.getJSONObject(i).getString("exam_id");
                            }
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(TeacherExamMarksActivity.this,
                                    R.layout.spinner_text, classes);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_examMark_exam.setAdapter(classAdapter);
                            mProgressDialog.dismiss();
                        } else {
                            Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_exam_list");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TeacherExamMarksActivity.this);
        requestQueue.add(stringRequest);
    }

    private void uploadMarksDetails() {
        mProgressDialog = new ProgressDialog(TeacherExamMarksActivity.this, "Loading Exam Marks ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tUpLoadMarksURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResult) {
                        if (sResult != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(sResult);
                                if (!jsonObject.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), "Exam Marks uploaded" +
                                            " successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please" +
                                            " try again.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please" +
                                    " try again.", Toast.LENGTH_SHORT).show();
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
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < alExmMrks.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("student_id", alExmMrks.get(i).getStudID());
                        jsonObject.put("class_id", sClassID);
                        jsonObject.put("subject_id", sSubjectID);
                        jsonObject.put("section_id", sSectionID);
                        jsonObject.put("exam_id", sExamID);
                        jsonObject.put("mark_obtained", alExmMrks.get(i).getObtainedmarks());
                        jsonObject.put("comment", alExmMrks.get(i).getComment());
                        jsonArray.put(i, jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Map<String, String> params = new HashMap<>();
                params.put("authenticate", "false");
                params.put("data", jsonArray.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getStudentDetails() {
        mProgressDialog = new ProgressDialog(TeacherExamMarksActivity.this, "Loading Exam Marks ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetStudentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject studentObject = jsonArray.getJSONObject(i);
                            sName = new String[jsonArray.length()];
                            sRollNo = new String[jsonArray.length()];
                            TeacherExamMarkNames items = new TeacherExamMarkNames(studentObject.getString("roll"),
                                    studentObject.getString("name"),
                                    studentObject.getString("student_id"));
                            list.add(items);
                        }
                        alExmMrks = new ArrayList<TeacherExamMarkNames>();
                        TeacherExamMarkAdapter teacherExamMarkAdapter = new TeacherExamMarkAdapter(TeacherExamMarksActivity.this, list, rv_examMark);
                        rv_examMark.setAdapter(teacherExamMarkAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Status Response", String.valueOf(error));
                mProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_students_of_section");
                params.put("class_id", sClassID);
                params.put("section_id", sSectionID);
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TeacherExamMarksActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getClassDetails() {
        mProgressDialog = new ProgressDialog(TeacherExamMarksActivity.this, "Loading Exam Marks ...");
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
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("class");
                            String[] classes = new String[jsonArray.length()];
                            sClassIDs = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                classes[i] = jsonArray.getJSONObject(i).getString("name");
                                sClassIDs[i] = jsonArray.getJSONObject(i).getString("class_id");
                            }
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(TeacherExamMarksActivity.this,
                                    R.layout.spinner_text, classes);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_examMark_class.setAdapter(classAdapter);
                        } else {
                            Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        mProgressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
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
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TeacherExamMarksActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getSection(final int pos) {
        mProgressDialog = new ProgressDialog(TeacherExamMarksActivity.this, "Loading Exam Marks ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetClassURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("class");
                            // sSectionId = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getString("name")
                                        .equalsIgnoreCase(sp_examMark_class.getSelectedItem().toString())) {
                                    sSectionId = new String[jsonArray.getJSONObject(i).getJSONArray("sections").length()];
                                    String[] sections = new String[jsonArray.getJSONObject(i).getJSONArray("sections").length()];
                                    for (int j = 0; j < sections.length; j++) {
                                        sections[j] = jsonArray.getJSONObject(i).getJSONArray("sections").getJSONObject(j).getString("name");
                                        sSectionId[j] = jsonArray.getJSONObject(i).getJSONArray("sections").getJSONObject(j).getString("section_id");
                                    }
                                    try {
                                        ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(TeacherExamMarksActivity.this,
                                                R.layout.spinner_text, sections);
                                        classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                        sp_examMark_section.setAdapter(classAdapter);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        mProgressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
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
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TeacherExamMarksActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getSubject(final int pos) {
        mProgressDialog = new ProgressDialog(TeacherExamMarksActivity.this, "Loading Exam Marks ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetSubject, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("subject");
                            String[] subjects = new String[jsonArray.length()];
                            sSubjectId = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                subjects[i] = jsonArray.getJSONObject(i).getString("name");
                                sSubjectId[i] = jsonArray.getJSONObject(i).getString("subject_id");
                            }
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(TeacherExamMarksActivity.this,
                                    R.layout.spinner_text, subjects);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_examMark_subject.setAdapter(classAdapter);
                        }
                        mProgressDialog.dismiss();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(TeacherExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
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
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("authenticate", "false");
                params.put("class_id", sClassID);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TeacherExamMarksActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {

    }
}