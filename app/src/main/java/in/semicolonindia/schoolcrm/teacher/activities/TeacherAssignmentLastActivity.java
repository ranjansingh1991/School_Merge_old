package in.semicolonindia.schoolcrm.teacher.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetClassURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetSubject;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetTeacherReportURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.tUploadAssignment;

@SuppressWarnings("ALL")
public class TeacherAssignmentLastActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner sp_Assignment_subject;
    Spinner sp_Assignment_class;
    Spinner sp_Assignment_section;
    Spinner sp_Assignment_ReportTo;
    TextView tv_selectDate;
    EditText et_Title;
    EditText et_Description;
    EditText et_Marks;
    TextView tv_File;
    Button btn_upload;
    String[] sClassIDs;
    private String[] sSectionId;
    private String[] sSubjectId;
    private String[] sTeacherId;
    private static String sClassID;
    private static String sUploderID;
    private static String sSectionID;
    private static String sSubjectID;
    private int mYear, mMonth, mDay;
    private static String sFilePath = "";
    private static String sFileName = "";
    private static final int FILE_SELECT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_assignment_last);
        Bundle mBundle = this.getIntent().getExtras();
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

        //...Here Name display in title....
        // tvToolbarTitle.setText(mBundle.getString("name"));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherAssignmentLastActivity.this, TeacherAssignmentFirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });


        tv_selectDate = (TextView) findViewById(R.id.tv_select_date);
        tv_selectDate.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        tv_selectDate.setText(sdf.format(new Date()));

        sp_Assignment_class = (Spinner) findViewById(R.id.sp_Assignment_class);
        // Select Class
        sp_Assignment_section = (Spinner) findViewById(R.id.sp_Assignment_section);
        // Select Section
        sp_Assignment_subject = (Spinner) findViewById(R.id.sp_Assignment_subject);

        sp_Assignment_ReportTo = (Spinner) findViewById(R.id.sp_ReportTo);

        et_Title = (EditText) findViewById(R.id.etTitle);
        et_Description = (EditText) findViewById(R.id.etDescription);
        et_Marks = (EditText) findViewById(R.id.etMarks);
        tv_File = (TextView) findViewById(R.id.tv_file);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);

        getClassAssignment();


        sp_Assignment_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                getSectionAssignment(pos);
                sClassID = sClassIDs[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_Assignment_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                sSubjectID = sSubjectId[pos];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tv_File.setOnClickListener(this);
    }

    private void getReportAssignment(int pos) {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAssignmentLastActivity.this, "Loading Teachers ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetTeacherReportURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("teachers");
                            String[] subjects = new String[jsonArray.length()];
                            sTeacherId = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                subjects[i] = jsonArray.getJSONObject(i).getString("name");
                                sTeacherId[i] = jsonArray.getJSONObject(i).getString("teacher_id");
                            }
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                                    R.layout.spinner_text, subjects);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_Assignment_ReportTo.setAdapter(classAdapter);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Toast.makeText(TeacherAssignmentLastActivity.this, "Oops! Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
                Toast.makeText(TeacherAssignmentLastActivity.this, "Oops! Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void uploadDataAssignment() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAssignmentLastActivity.this, "Uploading Assignment ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tUploadAssignment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResult) {
                        if (sResult != null) {
                            //mProgressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(sResult);
                                if (!jsonObject.getBoolean("error")) {
                                    Toast.makeText(TeacherAssignmentLastActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(TeacherAssignmentLastActivity.this, "Oops! Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(TeacherAssignmentLastActivity.this, "Oops! Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        mProgressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
                Toast.makeText(TeacherAssignmentLastActivity.this, "Oops! Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String title = et_Title.getText().toString();
                String description = et_Description.getText().toString();
                String marks = et_Marks.getText().toString();

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                params.put("tag", "upload_assignment");
                //Adding parameters
                params.put("title", title);
                params.put("description", description);
                params.put("marks", marks);
                params.put("uploader_type", "Teacher");
                String[] temp = sFileName.split("\\.");
                String s = sFilePath;
                s = s.replace("\n", "+");
                params.put("file_name", s);
                params.put("file_type", "." + temp[1]);
                params.put("submit_date", tv_selectDate.getText().toString());
                params.put("report_to", sp_Assignment_ReportTo.getSelectedItem().toString());
                params.put("upload_date", sdf.format(new Date()));
                params.put("subject_id", sSubjectID);
                params.put("class_id", sClassID);
                params.put("uploader_id", new PreferencesManager(getApplicationContext()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    private void getSubjectAssignment(final int pos) {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAssignmentLastActivity.this, "Loading Subject ...");
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
                            ArrayAdapter<CharSequence> classAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),
                                    R.layout.spinner_text, subjects);
                            classAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_Assignment_subject.setAdapter(classAdapter);
                            getReportAssignment(pos);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.",
                            Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
                Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.",
                        Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getSectionAssignment(final int pos) {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAssignmentLastActivity.this, "Loading Section ...");
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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (jsonArray.getJSONObject(i).getString("name")
                                        .equalsIgnoreCase(sp_Assignment_class.getSelectedItem().toString())) {
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
                                        sp_Assignment_section.setAdapter(classAdapter);
                                        getSubjectAssignment(pos);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
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

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    try {
                        sFilePath = encode(FileUtils.getPath(this, uri));
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                        e.printStackTrace();
                    }
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String encode(String sourceFile) throws Exception {
        String[] sTemp = sourceFile.split("/.");
        sFileName = sTemp[sTemp.length - 1];
        tv_File.setText(sFileName);
        byte[] base64EncodedData = Base64.encode(loadFileAsBytesArray(sourceFile), Base64.DEFAULT);
        return Base64.encodeToString(base64EncodedData, Base64.DEFAULT);
    }

    private byte[] loadFileAsBytesArray(String fileName) throws Exception {
        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;
    }

    private void getClassAssignment() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherAssignmentLastActivity.this, "Loading Class ...");
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
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
                Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_date:
                final Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherAssignmentLastActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_selectDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_upload:

                if (validation()) {
                    uploadDataAssignment();

                   /* et_Description.setText("");
                    et_Title.setText("");
                    et_Marks.setText("");*/

                }
                break;
            case R.id.tv_file:
                showFileChooser();
                break;
        }
    }

    private boolean validation() {
        boolean valid = true;
        if (et_Title.getText().length() < 5) {
            et_Title.setError("name cannot be blank and grater then 5 charater");
            valid = false;
        } else if (et_Description.getText().length() < 10) {
            et_Description.setError("Description cannot be blank and grater then 10 charater");
            valid = false;
        } else if (et_Marks.getText().length() < 1) {
            et_Marks.setError("marks can not be blank");
            valid = false;
        }
        return valid;
    }
}

