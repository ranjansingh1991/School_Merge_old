package in.semicolonindia.schoolcrm.teacher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.adapters.BookRequestListAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.BookRequestNames;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class BookRequestFragment extends Fragment {

    private ArrayList<BookRequestNames> arraylist = new ArrayList<BookRequestNames>();
   /* private ParentBookRequestListAdapter mBookRequestListAdapter;
    private ListView lvBookRequestList;
    ProgressDialog progressDialog;*/

    //1. Create Constructer and pass Model parameter...
    public BookRequestFragment(List<BookRequestNames> bookRequestNamesList) {
        this.arraylist = new ArrayList<BookRequestNames>();
        this.arraylist.addAll(bookRequestNamesList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_fragment_book_request, container, false);

        final ListView lvBookRequestList = (ListView) rootView.findViewById(R.id.lvBookRequestList);
        //2. Take one Adapter and set adapter
        final BookRequestListAdapter mBookRequestListAdapter = new BookRequestListAdapter(getActivity(), arraylist);
        lvBookRequestList.setAdapter(mBookRequestListAdapter);
        final EditText etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                if (mBookRequestListAdapter != null) {
                    mBookRequestListAdapter.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        //getBookRequestList();

        return rootView;
    }

   /* private void getBookRequestList() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetBookRequestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("book_list");
                            String[] sName = new String[jsonArray.length()];
                            String[] sStatus = new String[jsonArray.length()];
                            String[] sStartDate = new String[jsonArray.length()];
                            String[] sEndDate = new String[jsonArray.length()];
                            String[] sClassName = new String[jsonArray.length()];
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
                            }
                            for (int i = 0; i < sName.length; i++) {
                                ParentBookRequestNames mBookRequestNames = new ParentBookRequestNames(sName[i], sStatus[i],
                                        sStartDate[i], sEndDate[i]);
                                arraylist.add(mBookRequestNames);
                            }
                            mBookRequestListAdapter = new ParentBookRequestListAdapter(getActivity(), arraylist);
                            lvBookRequestList.setAdapter(mBookRequestListAdapter);

                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
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
                params.put("user_id", new PreferencesManager(getContext()).getUserID());
                params.put("user_type", "teacher");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }*/
}

