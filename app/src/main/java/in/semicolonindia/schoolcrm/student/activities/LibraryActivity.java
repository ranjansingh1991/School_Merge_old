package in.semicolonindia.schoolcrm.student.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import in.semicolonindia.schoolcrm.student.adapters.LibraryVPAdapter;
import in.semicolonindia.schoolcrm.student.beans.BookListNames;
import in.semicolonindia.schoolcrm.student.beans.BookRequestNames;
import in.semicolonindia.schoolcrm.sliding.SlidingTabLayout;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetBookListURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetBookRequestURL;

public class LibraryActivity extends AppCompatActivity {
    private ArrayList<BookRequestNames> alBookRequest = new ArrayList<BookRequestNames>();
    private ArrayList<BookListNames> alBoolList = new ArrayList<BookListNames>();
    private ProgressDialog progressDialog;
    private SlidingTabLayout stLibrary;
    private ViewPager vpLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_library);
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
        tvToolbarTitle.setText(getString(R.string.library_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LibraryActivity.this, StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        stLibrary = (SlidingTabLayout) findViewById(R.id.stLibrary);
        vpLibrary = (ViewPager) findViewById(R.id.vpLibrary);

        getBookRequestList();
    }

    private void getBookRequestList() {
        progressDialog = new ProgressDialog(LibraryActivity.this);
        progressDialog.setMessage("Loading please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetBookRequestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("book_list");
                            String[] sName = new String[jsonArray.length()];
                            String[] sStatus = new String[jsonArray.length()];
                            String[] sStartDate = new String[jsonArray.length()];
                            String[] sEndDate = new String[jsonArray.length()];
                            String[] sClassName = new String[jsonArray.length()];
                            String[] sBookID = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sName[i] = jsonArray.getJSONObject(i).getString("book_name_name");
                                switch (jsonArray.getJSONObject(i).getString("status").toLowerCase()) {
                                    case "1":
                                        sStatus[i] = "approved";
                                        break;
                                    case "2":
                                        sStatus[i] = "rejected";
                                        break;
                                    default:
                                        sStatus[i] = "pending";
                                        break;
                                }
                                sStartDate[i] = jsonArray.getJSONObject(i).getString("issue_start_date");
                                sEndDate[i] = jsonArray.getJSONObject(i).getString("issue_end_date");
                                sBookID[i] = jsonArray.getJSONObject(i).getString("book_id");
                                BookRequestNames mBookRequestNames = new BookRequestNames(sName[i], sStatus[i],
                                        sStartDate[i], sEndDate[i], sBookID[i]);
                                alBookRequest.add(mBookRequestNames);
                            }
                            //to call the getBookList metho........
                            getBookList();
                        } else {
                            Toast.makeText(LibraryActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(LibraryActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                progressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_book_issued_list");
                params.put("user_id", new PreferencesManager(LibraryActivity.this).getUserID());
                params.put("user_type", "student");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LibraryActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getBookList() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetBookListURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("book_list");
                            String[] sName = new String[jsonArray.length()];
                            String[] sAuthor = new String[jsonArray.length()];
                            String[] sPrice = new String[jsonArray.length()];
                            String[] sDescp = new String[jsonArray.length()];
                            String[] sBookID = new String[jsonArray.length()];
                            String[] sClassName = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                BookListNames mBookListNames = null;
                                sName[i] = jsonArray.getJSONObject(i).getString("name");
                                sAuthor[i] = jsonArray.getJSONObject(i).getString("author");
                                sPrice[i] = jsonArray.getJSONObject(i).getString("price");
                                sClassName[i] = getRoman(jsonArray.getJSONObject(i).getString("class_id"));
                                sDescp[i] = jsonArray.getJSONObject(i).getString("description");
                                sBookID[i] = jsonArray.getJSONObject(i).getString("book_id");
                                if (alBookRequest.size() > 0) {
                                    for (int j = 0; j < alBookRequest.size(); j++) {
                                        String status;
                                        if (sBookID[i].equalsIgnoreCase(alBookRequest.get(j).getBookID())) {
                                            status = "1";
                                        } else {
                                            status = "0";
                                        }
                                        mBookListNames = new BookListNames(sName[i], sAuthor[i], sPrice[i],
                                                sClassName[i], sDescp[i], sBookID[i], status);
                                    }
                                    alBoolList.add(mBookListNames);
                                } else {
                                    alBoolList.add(new BookListNames(sName[i], sAuthor[i], sPrice[i],
                                            sClassName[i], sDescp[i], sBookID[i], "0"));
                                }
                            }
                            // Here we can used LibraryVPAdapter for tab the screen.......
                            final LibraryVPAdapter mLibraryVPAdapter = new LibraryVPAdapter(getSupportFragmentManager(),
                                    getApplicationContext(), alBoolList, alBookRequest);
                            stLibrary.setCustomTabView(R.layout.tab_title_item, R.id.tvTabName);
                            stLibrary.setDistributeEvenly(true);
                            vpLibrary.setAdapter(mLibraryVPAdapter);
                            try {
                                stLibrary.setViewPager(vpLibrary);
                            } catch (Exception e) {
                                Log.i("FF", e.getMessage());
                            }
                        } else {
                            Toast.makeText(LibraryActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (
                            JSONException e)

                    {
                        e.printStackTrace();
                    }
                } else

                {
                    Toast.makeText(LibraryActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                progressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_book_list");
                params.put("class_id", new PreferencesManager(LibraryActivity.this).getClassID());
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LibraryActivity.this);
        requestQueue.add(stringRequest);
    }

    private String getRoman(String value) {
        switch (value) {
            case "1":
                value = "I";
                break;
            case "2":
                value = "II";
                break;
            case "3":
                value = "III";
                break;
            case "4":
                value = "IV";
                break;
            case "5":
                value = "V";
                break;
            case "6":
                value = "VI";
                break;
            case "7":
                value = "VII";
                break;
            case "8":
                value = "VIII";
                break;
            case "9":
                value = "IX";
                break;
            case "10":
                value = "X";
                break;
            case "11":
                value = "XI";
                break;
            case "12":
                value = "XII";
                break;
        }
        return value;
    }
}
