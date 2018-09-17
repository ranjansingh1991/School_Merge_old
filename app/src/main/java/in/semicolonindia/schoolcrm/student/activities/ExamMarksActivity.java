package in.semicolonindia.schoolcrm.student.activities;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import in.semicolonindia.schoolcrm.activities.StudentHomeActivity;
import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.StudentExamMarksListAdapter;
import in.semicolonindia.schoolcrm.student.beans.StudentExamNames;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetMarksURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sMarksListURL;
@SuppressWarnings("ALL")
public class ExamMarksActivity extends AppCompatActivity {

    RecyclerView rvExamMarks;
    StudentExamMarksListAdapter mExamMarkListAdapter;
    ArrayList<StudentExamNames> arrayList = new ArrayList<>();
    private String[] sMarksObtained;
    private String[] sMaxMarks;
    private String[] sSubject;
    private String[] sGrade;
    private String[] sRemarks;
    private Spinner sp_examMark_exam;
    private String sExamID;
    private String[] sExamIDs;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_exam_marks);
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
        tvToolbarTitle.setText(getString(R.string.exam_marks_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ExamMarksActivity.this, StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        sp_examMark_exam = (Spinner) findViewById(R.id.sp_examMark_exam);
        sp_examMark_exam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sExamID = sExamIDs[i];
                getMarksDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        rvExamMarks = (RecyclerView) findViewById(R.id.rvExamMarks);
        rvExamMarks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        getExamListDetails();
    }

    private void getExamListDetails() {
        mProgressDialog = new ProgressDialog(ExamMarksActivity.this, "Loading Exam Marks...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, sMarksListURL, new Response.Listener<String>() {
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
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(ExamMarksActivity.this,
                                    R.layout.student_spinner_text, classes);
                            classAdapter.setDropDownViewResource(R.layout.student_simple_spinner_dropdown);
                            sp_examMark_exam.setAdapter(classAdapter);
                            mProgressDialog.dismiss();
                        } else {
                            Toast.makeText(ExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(ExamMarksActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ExamMarksActivity.this);
        requestQueue.add(stringRequest);
    }


    private void getMarksDetails() {
        mProgressDialog = new ProgressDialog(ExamMarksActivity.this, "Loading Exam Marks...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetMarksURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(sResult);
                        JSONArray jsonArray = jsonObject.getJSONArray("marks");
                        sSubject = new String[jsonArray.length()];
                        sMarksObtained = new String[jsonArray.length()];
                        sMaxMarks = new String[jsonArray.length()];
                        sGrade = new String[jsonArray.length()];
                        sRemarks = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject marksObject = jsonArray.getJSONObject(i);
                            StudentExamNames itemDetails = new StudentExamNames(
                                    marksObject.getString("subject"),
                                    marksObject.getString("mark_obtained"),
                                    marksObject.getString("mark_total"),
                                    marksObject.getString("grade"),
                                    marksObject.getString("remark")
                            );
                            arrayList.add(itemDetails);
                        }

                        mExamMarkListAdapter = new StudentExamMarksListAdapter(getApplicationContext(), arrayList, rvExamMarks);
                        rvExamMarks.setAdapter(mExamMarkListAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        // mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(ExamMarksActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    //mProgressDialog.dismiss();
                }

                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
                // mProgressDialog.dismiss();
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_student_mark_information");
                params.put("exam_id", sExamID);
                params.put("student_id", new PreferencesManager(getApplicationContext()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ExamMarksActivity.this, StudentHomeActivity.class);
        startActivity(intent);
        finish();
    }
}
