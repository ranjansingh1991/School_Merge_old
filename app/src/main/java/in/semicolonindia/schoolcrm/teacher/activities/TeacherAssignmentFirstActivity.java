package in.semicolonindia.schoolcrm.teacher.activities;

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
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherAssignmentDetailsAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherAssignmentName;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetAssignmentURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetClassURL;

@SuppressWarnings("ALL")
public class TeacherAssignmentFirstActivity extends AppCompatActivity {

    Spinner sp_Assignment_class;
    RecyclerView rv_assignment;
    private Button btn_NewAssignment;
    private String[] sClassIDs;
    private String sClassID;
    private String[] sTitle;
    private String[] sUpload_Date;
    private String[] sSubmit_Date;
    private  String[] sDescription;
    TeacherAssignmentDetailsAdapter teacherAssignmentDetailsAdapter;
    ArrayList<TeacherAssignmentName> arraylist = new ArrayList<TeacherAssignmentName>();
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_assignment_first);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.Assignment));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sp_Assignment_class = (Spinner) findViewById(R.id.sp_Assignment_class);
        btn_NewAssignment = (Button) findViewById(R.id.btn_assignment);

        rv_assignment = (RecyclerView) findViewById(R.id.rvASsignmentList);

        rv_assignment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        getClassAssignment();


        btn_NewAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TeacherAssignmentLastActivity.class);
                startActivity(intent);
                mProgressDialog.dismiss();
            }
        });

        sp_Assignment_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sClassID = sClassIDs[i];
                getAssignmentDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAssignmentDetails() {
        mProgressDialog = new ProgressDialog(TeacherAssignmentFirstActivity.this, "Loading Assignment ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetAssignmentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("assignment");
                            sTitle = new String[jsonArray.length()];
                            sUpload_Date = new String[jsonArray.length()];
                            sSubmit_Date = new String[jsonArray.length()];
                            sDescription = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject assignmentObject = jsonArray.getJSONObject(i);
                                TeacherAssignmentName itemDetails = new TeacherAssignmentName(assignmentObject.getString("title"),
                                        assignmentObject.getString("description"),
                                        assignmentObject.getString("upload_date"),
                                        assignmentObject.getString("submit_date"));
                                arraylist.add(itemDetails);
                            }

                            teacherAssignmentDetailsAdapter = new TeacherAssignmentDetailsAdapter(TeacherAssignmentFirstActivity.this, arraylist);
                            rv_assignment.setAdapter(teacherAssignmentDetailsAdapter);


                        } else {
                            Toast.makeText(TeacherAssignmentFirstActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

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
                params.put("class_id", sClassID);
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void getClassAssignment() {
        mProgressDialog = new ProgressDialog(TeacherAssignmentFirstActivity.this, "Loading Assignment ...");
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
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                                    R.layout.spinner_text, classes);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_Assignment_class.setAdapter(classAdapter);
                            mProgressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong." +
                                    " Please try again.", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong" +
                            ". Please try again.", Toast.LENGTH_SHORT).show();
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
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}

