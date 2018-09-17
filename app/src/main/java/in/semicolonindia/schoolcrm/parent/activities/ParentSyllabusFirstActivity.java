package in.semicolonindia.schoolcrm.parent.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.parent.ParentHomeActivity;
import in.semicolonindia.schoolcrm.parent.adapters.AssignmentFirstAdapter;
import in.semicolonindia.schoolcrm.parent.beans.ParentStudentNames;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pLoginURL;
@SuppressWarnings("ALL")
public class ParentSyllabusFirstActivity extends AppCompatActivity {
    ListView lvStudentName;
    AssignmentFirstAdapter mStudentListAdapter;
    ArrayList<ParentStudentNames> arraylist = new ArrayList<>();
    ProgressDialog mProgressDialog;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_WRITE_STORAGE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_syllabus_first);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        // optiondisp = (TextView) findViewById(R.id.spindisplay);

        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.academic_syllabus_));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentSyllabusFirstActivity.this, ParentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        lvStudentName = (ListView) findViewById(R.id.lvStudentName);
        // Dynamic list for children
        getChildrenList();


        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mStudentListAdapter.filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        lvStudentName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // 1. Here we create a method to check Internet avalale or not.
                if (new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {

                    Bundle bundle = new Bundle();
                    ParentStudentNames studentNames = (ParentStudentNames) lvStudentName.getItemAtPosition(i);
                    bundle.putString("name", studentNames.getName());
                    bundle.putString("email", studentNames.geteMail());
                    bundle.putString("class_id", studentNames.getClassID());
                    Intent intent = new Intent(getApplicationContext(), ParentSyllabusLastActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                    // 2. Here we create ConnectionDetector class to check internet connection .
                    // if connection is not available then dailog box will open
                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(ParentSyllabusFirstActivity.this,
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
                } else {
                    //getChildrenList();
                }

            }
        });
    }


    private void getChildrenList() {
        mProgressDialog = new ProgressDialog(ParentSyllabusFirstActivity.this, "Loading Syllabus...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pLoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    mProgressDialog.dismiss();
                    Log.d("response", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray childArray = jsonObject.getJSONArray("children");
                        for (int i = 0; i < childArray.length(); i++) {
                            JSONObject childObject = childArray.getJSONObject(i);
                            ParentStudentNames itemDetails = new ParentStudentNames(childObject.getString("name"),
                                    childObject.getString("email"),
                                    childObject.getString("class_id"));

                            arraylist.add(itemDetails);
                        }

                        mStudentListAdapter = new AssignmentFirstAdapter(getApplicationContext(), arraylist,lvStudentName);
                        lvStudentName.setAdapter(mStudentListAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }

                    // overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                } else {
                    Toast.makeText(ParentSyllabusFirstActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("tag", "login");
                params.put("email", new PreferencesManager(getApplicationContext()).getEmail());
                params.put("password", new PreferencesManager(getApplicationContext()).getPass());
                params.put("authenticate", "false");
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(getApplicationContext(),ParentHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == REQUEST_WRITE_STORAGE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getApplicationContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}

