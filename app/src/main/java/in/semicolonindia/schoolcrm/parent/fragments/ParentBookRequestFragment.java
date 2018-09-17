package in.semicolonindia.schoolcrm.parent.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
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
import in.semicolonindia.schoolcrm.parent.adapters.ParentBookRequestListAdapter;
import in.semicolonindia.schoolcrm.parent.beans.ParentBookRequestNames;


import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetBookRequestURL;

/**
 * Created by Rupesh on 14-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentBookRequestFragment extends Fragment {

    private ArrayList<ParentBookRequestNames> arraylist = new ArrayList<ParentBookRequestNames>();
    private ParentBookRequestListAdapter mParentBookRequestListAdapter;
    private ListView lvBookRequestList;
    String[] sBookName;
    String[] sStatus;
    String[] sIssueStartDate;
    String[] sIssueEndDate;
    ProgressDialog mProgressDialog;
    private String sStudID;

    public ParentBookRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.student_fragment_book_request, container, false);
        lvBookRequestList = (ListView) rootView.findViewById(R.id.lvBookRequestList);
        final EditText etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                if (mParentBookRequestListAdapter != null) {
                    mParentBookRequestListAdapter.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        sStudID = getArguments().getString("student_id");
        getBookRequestList();

        return rootView;
    }

    private void getBookRequestList() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Book Library...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pGetBookRequestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("book_list");
                            sBookName = new String[jsonArray.length()];
                            sStatus = new String[jsonArray.length()];
                            sIssueStartDate = new String[jsonArray.length()];
                            sIssueEndDate = new String[jsonArray.length()];


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject bookRequestObject = jsonArray.getJSONObject(i);
                                String status = "";
                                switch (bookRequestObject.getString("status")) {
                                    case "0":
                                        status = "pending";
                                        break;
                                    case "1":
                                        status = "approved";
                                        break;
                                    case "2":
                                        status = "rejected";
                                        break;
                                }

                                ParentBookRequestNames itemDetails = new ParentBookRequestNames(bookRequestObject.getString("book_name_name"),
                                        status, bookRequestObject.getString("issue_start_date"),
                                        bookRequestObject.getString("issue_end_date"));
                                arraylist.add(itemDetails);
                            }

                            mParentBookRequestListAdapter = new ParentBookRequestListAdapter(getActivity(), arraylist);
                            lvBookRequestList.setAdapter(mParentBookRequestListAdapter);


                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();

                        //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);

                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("tag", "get_book_issued_list");
                params.put("user_id", sStudID);
                params.put("user_type", "student");
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}