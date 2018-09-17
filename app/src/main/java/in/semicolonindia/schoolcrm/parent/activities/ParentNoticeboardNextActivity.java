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
import in.semicolonindia.schoolcrm.parent.adapters.ParentNoticeboardListAdapter;
import in.semicolonindia.schoolcrm.parent.beans.ParentNoticeNames;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetNoticeboardURL;
import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetNoticeboardURL;
@SuppressWarnings("ALL")
public class ParentNoticeboardNextActivity extends AppCompatActivity {
    ListView lvNoticeBoard;
    ParentNoticeboardListAdapter mParentNoticeboardListAdapter;
    ArrayList<ParentNoticeNames> arraylist = new ArrayList<ParentNoticeNames>();
    private String[] sTitle;
    private String[] sNotice;
    private String[] sDate;
    private String[] sNoticeName;
    private String[] sDesp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_noticeboard_next);
        getNoticeboard();

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
        tvToolbarTitle.setText(getString(R.string.noticeboard_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentNoticeboardNextActivity.this, ParentNoticeboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });


        lvNoticeBoard = (ListView) findViewById(R.id.lvNoticeBoard);

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mParentNoticeboardListAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }


    private void getNoticeboard()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pGetNoticeboardURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("noticeboard");
                            sTitle = new String[jsonArray.length()];
                            sNotice = new String[jsonArray.length()];
                            sDate = new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject noticeObject = jsonArray.getJSONObject(i);
                               /* sTitle[i] = jsonArray.getJSONObject(i).getString("notice_title");
                                sNotice[i] = jsonArray.getJSONObject(i).getString("notice");
                                sDate[i] = jsonArray.getJSONObject(i).getString("date_added");
                               */
                                ParentNoticeNames parentNoticeNames = new ParentNoticeNames(noticeObject.getString("notice_title"),
                                        noticeObject.getString("date_added"),
                                        noticeObject.getString("notice"));

                                arraylist.add(parentNoticeNames);
                            }

                            mParentNoticeboardListAdapter = new ParentNoticeboardListAdapter(ParentNoticeboardNextActivity.this,
                                    arraylist);
                            lvNoticeBoard.setAdapter(mParentNoticeboardListAdapter);

                            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("Status Response", String.valueOf(error));

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_event_calendar");
                params.put("authenticate", "false");
                return params ;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(getApplicationContext(),ParentNoticeboardActivity.class);
        startActivity(intent);
        finish();
    }
}

