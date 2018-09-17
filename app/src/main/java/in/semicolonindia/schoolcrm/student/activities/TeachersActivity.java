package in.semicolonindia.schoolcrm.student.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import in.semicolonindia.schoolcrm.activities.StudentHomeActivity;
import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.StudentTeacherListAdapter;
import in.semicolonindia.schoolcrm.student.beans.StudentTeacherNames;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetTeacherMsg;


/**
 * Created by Rupesh on 01-02-2018.
 */

public class TeachersActivity extends AppCompatActivity { /*implements View.OnClickListener*/
    private ListView lvOldMsg;
    private StudentTeacherListAdapter mMessageListAdapter;
    private ArrayList<StudentTeacherNames> arraylist = new ArrayList<StudentTeacherNames>();
    private String[] sNames;
    private String[] sEmails;
    private String[] sIDs;
    private String[] sImgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_teacher);

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
        tvToolbarTitle.setText(getString(R.string.teachers));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeachersActivity.this, StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
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
                Bundle mBundle = new Bundle();
                mBundle.putString("sTeachersName", sNames[i]);
                mBundle.putString("sEmail", sEmails[i]);
                mBundle.putString("sID", sIDs[i]);
                mBundle.putString("sImgURL", sImgURL[i]);
                Intent mIntent = new Intent(getApplicationContext(), ChatActivity.class);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getMessages() {
        final ProgressDialog mProgressDialog = new ProgressDialog(TeachersActivity.this, "Loading Messages...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetTeacherMsg, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (jsonObject.getJSONArray("teachers").length() > 0) {
                            JSONArray jsonArray = jsonObject.getJSONArray("teachers");
                            sNames = new String[jsonArray.length()];
                            sEmails = new String[jsonArray.length()];
                            sIDs = new String[jsonArray.length()];
                            sImgURL = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sNames[i] = jsonArray.getJSONObject(i).getString("name");
                                sEmails[i] = jsonArray.getJSONObject(i).getString("email");
                                sIDs[i] = jsonArray.getJSONObject(i).getString("teacher_id");
                                sImgURL[i] = jsonArray.getJSONObject(i).getString("image_url");
                                StudentTeacherNames mStudentTeacherNames = new StudentTeacherNames(sNames[i],
                                        sEmails[i], sIDs[i],
                                        sImgURL[i], jsonArray.getJSONObject(i).getInt("message_count"));
                                arraylist.add(mStudentTeacherNames);
                            }
                            mMessageListAdapter = new StudentTeacherListAdapter(getApplicationContext(), arraylist);
                            lvOldMsg.setAdapter(mMessageListAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_teachers_message");
                params.put("email", new PreferencesManager(getApplicationContext()).getEmail());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(), StudentHomeActivity.class);
        startActivity(setIntent);
        finish();
    }

}
