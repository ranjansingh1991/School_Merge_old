package in.semicolonindia.schoolcrm.student.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.StudentAttendanceAdapter;
import in.semicolonindia.schoolcrm.student.beans.AttendanceData;
import in.semicolonindia.schoolcrm.student.beans.AttendanceFirstModel;
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetAttendanceListURL;

/**
 * Created by Rupesh on 01-02-2018.
 */

@SuppressWarnings("ALL")
public class AttendanceActivity extends AppCompatActivity {
    private ListView lv_List;
    Spinner spinner_Month;
    ProgressDialog mProgressDialog;
    private StudentAttendanceAdapter attendanceListAdapter;
    private String[] subject;
    private String[] date;
    private String[] status;
    ArrayList<AttendanceFirstModel> arraylist = new ArrayList<AttendanceFirstModel>();

    TextView tv_kidName, tv_ClassName, tv_SectionName;
    TextView tv_Year;
    CircleImageView img_Kid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_attendance);

        init();
    }

    private void init() {
        // This code apply to get the data. whatever previous activity to send data.
        //Bundle mBundle = this.getIntent().getExtras();

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
        tv_kidName = (TextView) findViewById(R.id.tv_kidName);
        tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
        tv_SectionName = (TextView) findViewById(R.id.tv_SectionName);
        img_Kid = (CircleImageView) findViewById(R.id.img_Kid);

        Picasso.with(this).load(new PreferencesManager(getApplicationContext()).getImage()).into(img_Kid);
        tv_kidName.setText(new PreferencesManager(getApplicationContext()).getName());
        tv_ClassName.setText(new PreferencesManager(getApplicationContext()).getClassName());
        tv_SectionName.setText(new PreferencesManager(getApplicationContext()).getSectionName());


        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        tv_Year = (TextView) findViewById(R.id.tv_Year);
        tv_Year.setText(sdfYear.format(new Date()));

        tvToolbarTitle.setText(getString(R.string.attendance));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Here we have to get data...
        //sStudID = mBundle.getString("student_id");

        lv_List = (ListView) findViewById(R.id.lv_List);

        // Calling Methods...

        int[] months = {0, 1, 2, 3, 4 , 5, 6, 7, 8, 9, 10, 11};
        ArrayList<String> alMonths = new ArrayList<String>();

        for (int i = 0; i < months.length; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, months[i]);
            String month_name = month_date.format(cal.getTime());
            alMonths.add(month_name);
        }

        spinner_Month = (Spinner) findViewById(R.id.spinner_Month);
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.student_simple_spinner_item, alMonths);
        monthsAdapter.setDropDownViewResource(R.layout.student_simple_spinner_item);
        spinner_Month.setAdapter(monthsAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        spinner_Month.setSelection(Integer.parseInt(sdf.format(new Date())) - 1);

        spinner_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                getAttendanceList(spinner_Month.getSelectedItemPosition() + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAttendanceList(final int month) {
        mProgressDialog = new ProgressDialog(AttendanceActivity.this, "Loading Attendance...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetAttendanceListURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            String[] subjects = new String[jsonObject.getJSONArray("subject").length()];

                            for (int i = 0; i < subjects.length; i++) {
                                subjects[i] = jsonObject.getJSONArray("subject").getString(i);
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("attendance");
                            List<AttendanceData>[] mAttendanceData = new ArrayList[jsonObject.getJSONArray("subject").length()];
                            for (int i = 0; i < subjects.length; i++) {
                                mAttendanceData[i] = new ArrayList<AttendanceData>();
                            }

                            for (int i = 0; i < mAttendanceData.length; i++) {
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    if (subjects[i].equalsIgnoreCase(jsonArray.getJSONObject(j).getString("subject"))) {
                                        AttendanceData mData = new AttendanceData(jsonArray.getJSONObject(j).getString("date"),
                                                jsonArray.getJSONObject(j).getString("status"));
                                        mAttendanceData[i].add(mData);
                                    }
                                }
                            }
                            attendanceListAdapter = new StudentAttendanceAdapter( getApplicationContext(), mAttendanceData,
                                    subjects, month);
                            lv_List.setAdapter(attendanceListAdapter);
                        } else {
                            Toast.makeText(AttendanceActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AttendanceActivity.this, "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                }
                mProgressDialog.dismiss();
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
                params.put("tag", "get_student_attendance");
                params.put("student_id", new PreferencesManager(getApplicationContext()).getUserID());
                params.put("month", String.valueOf(month));
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}