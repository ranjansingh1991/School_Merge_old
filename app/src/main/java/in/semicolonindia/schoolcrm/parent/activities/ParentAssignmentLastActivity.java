package in.semicolonindia.schoolcrm.parent.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Locale;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.StudentAssignmentAdapter;
import in.semicolonindia.schoolcrm.student.beans.AssignmentName;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetAssignmentURL;
@SuppressWarnings("ALL")
public class ParentAssignmentLastActivity extends AppCompatActivity {
    private String sClassID;
    private String sStudID;
    ListView listView;
    StudentAssignmentAdapter assignmentLastAdapter;
    private  String[] aTitle;
    private String[] aQuestion;
    private String[] aUpload_Date;
    private String[] a_Submit_Date;
    private  String[] aMarks;
    private   String[] Report;

    ArrayList<AssignmentName> arraylist = new ArrayList<AssignmentName>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_assignment_final);
        // This code apply to get the data. whatever previous activity to send data.
        Bundle mBundle = this.getIntent().getExtras();

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

        tvToolbarTitle .setText(mBundle.getString("name"));

        //tvToolbarTitle.setText(getString(R.string.assignment));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentAssignmentLastActivity.this, ParentAssignmentFirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        sClassID = mBundle.getString("class_id");
        sStudID = mBundle.getString("student_id");

        listView= (ListView) findViewById(R.id.lv_Assignment);

        getAssignmentTask();

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                assignmentLastAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    private void getAssignmentTask(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pGetAssignmentURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("assignment");
                            aTitle = new String[jsonArray.length()];
                            aQuestion = new String[jsonArray.length()];
                            aUpload_Date = new String[jsonArray.length()];
                            a_Submit_Date = new String[jsonArray.length()];
                            aMarks=new String[jsonArray.length()];
                            Report=new String[jsonArray.length()];
                            String[] sFile = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject assignmentObject = jsonArray.getJSONObject(i);
                                AssignmentName itemDetails = new AssignmentName(assignmentObject.getString("title"),
                                        assignmentObject.getString("description"),
                                        assignmentObject.getString("upload_date"),
                                        assignmentObject.getString("submit_date"),
                                        assignmentObject.getString("marks"),
                                        assignmentObject.getString("report_to"),
                                        assignmentObject.getString("class_id"),
                                        assignmentObject.getString("file_name"));

                                arraylist.add(itemDetails);
                            }

                            assignmentLastAdapter = new StudentAssignmentAdapter(ParentAssignmentLastActivity.this, arraylist);
                            listView.setAdapter(assignmentLastAdapter);


                        } else {
                            Toast.makeText(ParentAssignmentLastActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

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
                params.put("tag", "get_assignment");
                params.put("class_id", sClassID);
                params.put("student_id", sStudID);
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}








