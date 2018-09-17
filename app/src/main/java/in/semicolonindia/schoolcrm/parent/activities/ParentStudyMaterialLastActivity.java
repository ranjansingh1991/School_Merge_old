package in.semicolonindia.schoolcrm.parent.activities;

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
import in.semicolonindia.schoolcrm.parent.adapters.ParentStudyMatLastAdapter;
import in.semicolonindia.schoolcrm.parent.beans.ParentStudyMaterialLast;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetStudyMaterialURL;
@SuppressWarnings("ALL")
public class ParentStudyMaterialLastActivity extends AppCompatActivity {
    ListView lvStudyMat;
    ParentStudyMatLastAdapter mParentStudyMatLastAdapter;
    private String[] sTitle;
    private String[] sSubject;
    private String[] sClass;
    private String[] sDate;
    private String[] sDesp;

    private String sClassID;

    ArrayList<ParentStudyMaterialLast> arraylist = new ArrayList<ParentStudyMaterialLast>();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_study_mat_last);
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

        //...Here Name display in title....
        tvToolbarTitle.setText(mBundle.getString("name"));

        // tvToolbarTitle.setText(getString(R.string.study_material_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentStudyMaterialLastActivity.this, ParentStudyMaterialFirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        sClassID = mBundle.getString("class_id");

        lvStudyMat = (ListView) findViewById(R.id.lvStudyMat);

        getStudyMaterial();


        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mParentStudyMatLastAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

    }

    private void getStudyMaterial() {
        mProgressDialog = new ProgressDialog(ParentStudyMaterialLastActivity.this, "Loading Study Loading...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pGetStudyMaterialURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("study_material");
                            sTitle = new String[jsonArray.length()];
                            sDesp = new String[jsonArray.length()];
                            sSubject = new String[jsonArray.length()];
                            sClass = new String[jsonArray.length()];
                            sDate = new String[jsonArray.length()];
                            String[] sFile = new String[jsonArray.length()];


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject studyMatObject = jsonArray.getJSONObject(i);
                                ParentStudyMaterialLast itemDetails = new ParentStudyMaterialLast(studyMatObject.getString("title"),
                                        studyMatObject.getString("subject_name"),
                                        studyMatObject.getString("class_name"),
                                        studyMatObject.getString("date_added"),
                                        studyMatObject.getString("file_name"),
                                        studyMatObject.getString("description"));

                                arraylist.add(itemDetails);


                            }
                            mParentStudyMatLastAdapter = new ParentStudyMatLastAdapter(ParentStudyMaterialLastActivity.this, arraylist, lvStudyMat);
                            lvStudyMat.setAdapter(mParentStudyMatLastAdapter);

                        } else {
                            Toast.makeText(ParentStudyMaterialLastActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
                    }
                    mParentStudyMatLastAdapter.notifyDataSetChanged();
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
                params.put("tag", "get_study_material");
                params.put("class_id",sClassID);
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(getApplicationContext(),ParentStudyMaterialFirstActivity.class);
        startActivity(intent);
        finish();
    }

}

