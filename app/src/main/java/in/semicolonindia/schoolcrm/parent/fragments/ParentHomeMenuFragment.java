package in.semicolonindia.schoolcrm.parent.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.parent.activities.ParentAccountActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentAssignmentFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentAttendanceFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentCalenderActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentClassRoutinActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentExamMarksFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentKidsLocationMapActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentLibraryActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentMessageActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentNoticeboardActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentPaymentFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentStudyMaterialFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentSubjectFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentSyllabusFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentTeacherFirstActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentTransportFirstActivity;
import in.semicolonindia.schoolcrm.parent.adapters.ParentHomeMenuAdapter;
import in.semicolonindia.schoolcrm.student.beans.FirstName;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pHolidayURL;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentHomeMenuFragment extends Fragment {
    ListView lv_First;
    private ProgressDialog mProgressDialog;
    private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private ParentHomeMenuAdapter parentHomeMenuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.teacher_fragment_home_menu, container, false);
        lv_First = rootView.findViewById(R.id.lv_First);

        ParentHomeMenuAdapter parentHomeMenuAdapter = new ParentHomeMenuAdapter(getContext(),
                getResources().getStringArray(R.array.parent_home_menu_item_fragment));
        lv_First.setAdapter(parentHomeMenuAdapter);

        //... ItemClickListner.......in ListView....//

        lv_First.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {

                        case 0:
                            startActivity(new Intent(getActivity(), ParentAttendanceFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), ParentAssignmentFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 2:
                            //getTeachers();
                            startActivity(new Intent(getActivity(), ParentTeacherFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 3:
                            // getSubjects();
                            startActivity(new Intent(getActivity(), ParentSyllabusFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 4:
                            startActivity(new Intent(getActivity(), ParentClassRoutinActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 5:
                            startActivity(new Intent(getActivity(), ParentStudyMaterialFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 6:
                            startActivity(new Intent(getActivity(), ParentSubjectFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 7:
                            startActivity(new Intent(getActivity(), ParentExamMarksFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 8:
                            startActivity(new Intent(getActivity(), ParentPaymentFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 9:
                            startActivity(new Intent(getActivity(), ParentLibraryActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 10:
                            startActivity(new Intent(getActivity(), ParentTransportFirstActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 11:
                            startActivity(new Intent(getActivity(), ParentKidsLocationMapActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 12:
                            startActivity(new Intent(getActivity(), ParentNoticeboardActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 13:
                            startActivity(new Intent(getActivity(), ParentMessageActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 14:
                            getCalendarNotification();
                            break;
                        case 15:
                            startActivity(new Intent(getActivity(), ParentAccountActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                    }
                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(getActivity(),
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });


        return rootView;
    }

    private void getCalendarNotification() {
        mProgressDialog = new ProgressDialog(getActivity(), "Loading Calendar...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pHolidayURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {

                if (sResult != null) {
                    mProgressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("holiday");
                            String[] sTitle = new String[jsonArray.length()];
                            String[] sFromDate = new String[jsonArray.length()];
                            String[] sToDate = new String[jsonArray.length()];
                            String[] sDescription = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sTitle[i] = jsonArray.getJSONObject(i).getString("title");
                                sFromDate[i] = jsonArray.getJSONObject(i).getString("from_date");
                                sToDate[i] = jsonArray.getJSONObject(i).getString("to_date");
                                sDescription[i] = jsonArray.getJSONObject(i).getString("description");
                            }
                            Bundle mBundle = new Bundle();
                            mBundle.putStringArray("title", sTitle);
                            mBundle.putStringArray("from_date", sFromDate);
                            mBundle.putStringArray("to_date", sToDate);
                            mBundle.putStringArray("description", sDescription);

                            Intent mIntent = new Intent(getActivity(), ParentCalenderActivity.class);
                            mIntent.putExtras(mBundle);
                            startActivity(mIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        } else {
                            Toast.makeText(getActivity(), "Oops! Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mProgressDialog.dismiss();
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
                mProgressDialog.dismiss();
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_holiday");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



}
