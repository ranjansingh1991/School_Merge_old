package in.semicolonindia.schoolcrm.teacher.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.student.beans.StudentTeacherNames;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherMessageListAdapter;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.tGetContactsTeacher;

@SuppressWarnings("ALL")
public class TeacherMessageActivity extends AppCompatActivity {
    private ListView lvOldMsg;
    private TeacherMessageListAdapter mTeacherMessageListAdapter;
    private ArrayList<StudentTeacherNames> arraylist = new ArrayList<StudentTeacherNames>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_message);
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
        tvToolbarTitle.setText(getString(R.string.messages));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ImageView imgCreateMsg = (ImageView) findViewById(R.id.imgCreateMsg);

        lvOldMsg = (ListView) findViewById(R.id.lvOldMsg);
        getMessages();

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        //imgCreateMsg.setOnClickListener(this);

        lvOldMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!arraylist.get(i).getID().equalsIgnoreCase("###")) {
                    Bundle mBundle = new Bundle();
                    mBundle.putString("sName", arraylist.get(i).getName());
                    mBundle.putString("sEmail", arraylist.get(i).getEmail());
                    mBundle.putString("sID", arraylist.get(i).getID());
                    mBundle.putString("sImgURL", arraylist.get(i).getImgURL());
                    mBundle.putInt("nReceiverType", arraylist.get(i).getReceiverType());
                    Intent mIntent = new Intent(getApplicationContext(), TeacherChatActivity.class);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                }
            }
        });
    }

    private void getMessages() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeacherMessageActivity.this, "Loading Messages...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tGetContactsTeacher, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            jsonObject = jsonObject.getJSONObject("contact");
                            JSONArray jaTeachers = jsonObject.getJSONArray("teacher");
                            JSONArray jaParents = jsonObject.getJSONArray("parent");
                            JSONArray jaStudents = jsonObject.getJSONArray("student");
                            String[] sNames = new String[jaTeachers.length()];
                            String[] sEmails = new String[jaTeachers.length()];
                            String[] sIDs = new String[jaTeachers.length()];
                            String[] sImgURL = new String[jaTeachers.length()];
                            arraylist.add(new StudentTeacherNames("TEACHERS",
                                    "###", "###", "###", 0, 0));
                            if (jaTeachers.length() > 0) {
                                for (int i = 0; i < jaTeachers.length(); i++) {
                                    sNames[i] = jaTeachers.getJSONObject(i).getString("name");
                                    sEmails[i] = jaTeachers.getJSONObject(i).getString("email");
                                    sIDs[i] = jaTeachers.getJSONObject(i).getString("teacher_id");
                                    sImgURL[i] = jaTeachers.getJSONObject(i).getString("image_url");
                                    StudentTeacherNames mStudentTeacherNames = new StudentTeacherNames(sNames[i],
                                            sEmails[i], sIDs[i], sImgURL[i],
                                            jaTeachers.getJSONObject(i).getInt("message_count"), 1);
                                    arraylist.add(mStudentTeacherNames);
                                }
                            }
                            if (jaParents.length() > 0) {
                                arraylist.add(new StudentTeacherNames("PARENTS",
                                        "###", "###", "###", 0, 0));
                                sNames = new String[jaParents.length()];
                                sEmails = new String[jaParents.length()];
                                sIDs = new String[jaParents.length()];
                                sImgURL = new String[jaParents.length()];
                                for (int i = 0; i < jaParents.length(); i++) {
                                    sNames[i] = jaParents.getJSONObject(i).getString("name");
                                    sEmails[i] = jaParents.getJSONObject(i).getString("email");
                                    sIDs[i] = jaParents.getJSONObject(i).getString("parent_id");
                                    sImgURL[i] = jaParents.getJSONObject(i).getString("image_url");
                                    StudentTeacherNames mStudentTeacherNames = new StudentTeacherNames(sNames[i],
                                            sEmails[i], sIDs[i], sImgURL[i],
                                            jaParents.getJSONObject(i).getInt("message_count"), 2);
                                    arraylist.add(mStudentTeacherNames);
                                }
                            }
                            if (jaStudents.length() > 0) {
                                arraylist.add(new StudentTeacherNames("STUDENTS",
                                        "###", "###", "###", 0, 0));
                                sNames = new String[jaStudents.length()];
                                sEmails = new String[jaStudents.length()];
                                sIDs = new String[jaStudents.length()];
                                sImgURL = new String[jaStudents.length()];
                                for (int i = 0; i < jaStudents.length(); i++) {
                                    sNames[i] = jaStudents.getJSONObject(i).getString("name");
                                    sEmails[i] = jaStudents.getJSONObject(i).getString("email");
                                    sIDs[i] = jaStudents.getJSONObject(i).getString("student_id");
                                    sImgURL[i] = jaStudents.getJSONObject(i).getString("image_url");
                                    StudentTeacherNames mStudentTeacherNames = new StudentTeacherNames(sNames[i],
                                            sEmails[i], sIDs[i], sImgURL[i],
                                            jaStudents.getJSONObject(i).getInt("message_count"), 3);
                                    arraylist.add(mStudentTeacherNames);
                                }
                            }
                            mTeacherMessageListAdapter = new TeacherMessageListAdapter(getApplicationContext(), arraylist);
                            lvOldMsg.setAdapter(mTeacherMessageListAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong." +
                                    " Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong. " +
                            "Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("teacher_id", new PreferencesManager(getApplicationContext()).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
