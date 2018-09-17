package in.semicolonindia.schoolcrm.teacher.activities;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherAttendanceAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherAttendanceMark;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetRoutineURL;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetClassURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetRoutineURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetStudentURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tUploadAttendance;

@SuppressWarnings("ALL")
public class TeacherAttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_Attendence_subject;
    private Spinner sp_Attendence_class;
    private Spinner sp_Attendence_section;
    private TextView tv_select_date;
    private RecyclerView rv_Attendence;
    private Button btn_Submit;
    private static String[] sClassIDs;
    private static String[] sSectionId;
    private static String[] sSubjectId;
    private static String sNameClass;
    private static String sSectionID;
    private static String sSubjectID;
    private int nYear, nMonth, nDay;
    private String sDay;
    public static ArrayList<TeacherAttendanceMark> alAttendancelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_attendance);
        initialize();

        sp_Attendence_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sNameClass = sClassIDs[pos];
                getSection(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_Attendence_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sSectionID = sSectionId[pos];
                getSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_Attendence_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sSubjectID = sSubjectId[pos];
                getStudentDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        tvToolbarTitle.setText(getString(R.string.attendance));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Initialise you date
        tv_select_date = (TextView) findViewById(R.id.tv_select_date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        tv_select_date.setText(sdf.format(new Date()));
        sdf = new SimpleDateFormat("EEEE");
        sDay = sdf.format(new Date());
        tv_select_date.setOnClickListener(this);

        final Animation animationSlidUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_up);
        // Select Subject
        sp_Attendence_class = (Spinner) findViewById(R.id.spinner_class);
        // Select Class
        sp_Attendence_section = (Spinner) findViewById(R.id.spinner_section);
        // Select Section
        sp_Attendence_subject = (Spinner) findViewById(R.id.spinner_subject);
        // Recycler View
        rv_Attendence = (RecyclerView) findViewById(R.id.rv_attendance);
        rv_Attendence.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // Button Submit
        btn_Submit = (Button) findViewById(R.id.btn_Submit);

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alAttendancelist.size() < 1 || alAttendancelist == null) {
                    Toast.makeText(getApplicationContext(), "Please mark attendance first",
                            Toast.LENGTH_LONG).show();
                } else {
                    uploadAtndDetails();
                }
            }
        });

        rv_Attendence.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    btn_Submit.startAnimation(animationSlidUp);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 || dy < 0)
                    btn_Submit.startAnimation(animationSlidUp);
            }
        });

        getClassDetails();
    }

    private void uploadAtndDetails() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAttendanceActivity.this,
                getApplicationContext().getResources().getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tUploadAttendance,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResult) {
                        if (sResult != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(sResult);
                                if (!jsonObject.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), "Attendance uploaded" +
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
                //Creating parameters
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < alAttendancelist.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("timestamp", tv_select_date.getText().toString());
                        jsonObject.put("class_id", sNameClass);
                        jsonObject.put("section_id", sSectionID);
                        jsonObject.put("class_routine_id", sSubjectID);
                        jsonObject.put("student_id", alAttendancelist.get(i).getStudentId());
                        if (alAttendancelist.get(i).getStatus().equalsIgnoreCase("")) {
                            jsonObject.put("status", "0");
                        } else if (alAttendancelist.get(i).getStatus().equalsIgnoreCase("P")) {
                            jsonObject.put("status", "1");
                        } else if (alAttendancelist.get(i).getStatus().equalsIgnoreCase("A")) {
                            jsonObject.put("status", "2");
                        }
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

    private void getSubject() {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            final SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            sDay = tv_select_date.getText().toString();
            sDay = sdf.format(df.parse(sDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAttendanceActivity.this, getApplicationContext()
                .getResources().getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetRoutineURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("routine");
                            String[] subjects = new String[jsonArray.length()];
                            sSubjectId = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                subjects[i] = jsonArray.getJSONObject(i).getString("subject");
                                sSubjectId[i] = jsonArray.getJSONObject(i).getString("class_routine_id");
                            }
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                                    R.layout.spinner_text, subjects);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_Attendence_subject.setAdapter(classAdapter);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
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
                params.put("authenticate", "false");
                params.put("day", sDay.toLowerCase());
                params.put("section_id", sSectionID);
                params.put("class_id", sNameClass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getSection(int pos) {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAttendanceActivity.this, getApplicationContext()
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
                                        .equalsIgnoreCase(sp_Attendence_class.getSelectedItem().toString())) {
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
                                        sp_Attendence_section.setAdapter(classAdapter);
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

    private void getClassDetails() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAttendanceActivity.this, getApplicationContext()
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
                            sp_Attendence_class.setAdapter(classAdapter);
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

    @Override
    public void onClick(View view) {
        if (view == tv_select_date) {
            // Get Current Date
            final Calendar calendar = Calendar.getInstance();
            nYear = calendar.get(Calendar.YEAR);
            nMonth = calendar.get(Calendar.MONTH);
            nDay = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherAttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tv_select_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    getSubject();
                }
            }, nYear, nMonth, nDay);
            datePickerDialog.show();
        }
    }

    private void getStudentDetails() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAttendanceActivity.this, getApplicationContext()
                .getResources().getString(R.string.please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetStudentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        ArrayList<TeacherAttendanceMark> markArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject studentObject = jsonArray.getJSONObject(i);
                            TeacherAttendanceMark attendanceMark = new TeacherAttendanceMark(studentObject.getString("roll"),
                                    studentObject.getString("name"),
                                    studentObject.getString("student_id"),
                                    "");
                            markArrayList.add(attendanceMark);
                        }
                        rv_Attendence.setAdapter(new TeacherAttendanceAdapter(getApplicationContext(), markArrayList, rv_Attendence));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
                Log.e("Status Response", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_students_of_section");
                params.put("class_id", sNameClass);
                params.put("section_id", sSectionID);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}