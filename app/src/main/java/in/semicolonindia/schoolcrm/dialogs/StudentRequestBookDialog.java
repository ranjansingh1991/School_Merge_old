package in.semicolonindia.schoolcrm.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.BookListNames;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetSubmitBookRequestURL;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class StudentRequestBookDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private BookListNames mBookListNames;
    private TextView tvIssueStartDate;
    private TextView tvIssueEndDate;
    private Button btnRequestBookConfirm;

    private DatePickerDialog.OnDateSetListener mDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

                }
            };
    private int nYear;
    private int nMonth;
    private int nDay;
    private final int nDEST_JRNY_DATE = 0;

    // Constructer.........
    public StudentRequestBookDialog(Context context, @StyleRes int themeResId,
                                    BookListNames mBookListNames) {
        super(context, themeResId);
        this.context = context;
        this.mBookListNames = mBookListNames;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.student_dialog_request_book);
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        final TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        final TextView tvBookName = (TextView) findViewById(R.id.tvBookName);
        tvIssueStartDate = (TextView) findViewById(R.id.tvIssueStartDate);
        tvIssueEndDate = (TextView) findViewById(R.id.tvIssueEndDate);
        btnRequestBookConfirm = (Button) findViewById(R.id.btnRequestBookConfirm);
        tvTitle.setTypeface(appFontRegular);
        tvBookName.setTypeface(appFontLight);
        tvIssueStartDate.setTypeface(appFontLight);
        tvIssueEndDate.setTypeface(appFontLight);
        btnRequestBookConfirm.setTypeface(appFontRegular);

        tvBookName.setText(mBookListNames.getBookName());
        btnRequestBookConfirm.setOnClickListener(this);

        tvIssueStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tvIssueStartDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                dpd.show();
            }
        });

        tvIssueEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                nYear = calendar.get(Calendar.YEAR);
                nMonth = calendar.get(Calendar.MONTH);
                nDay = calendar.get(Calendar.DAY_OF_MONTH);
                showDate(nYear, nMonth + 1, nDay, tvIssueEndDate);
                final Calendar c = Calendar.getInstance();

                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        tvIssueEndDate.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                dpd.show();
            }
        });
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        tvTitle.setMinWidth(displayMetrics.widthPixels - 32);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRequestBookConfirm:
                // calling  getSubmitRequest methods..........
                if (tvIssueStartDate.getText().length() > 10){
                    Toast.makeText(context, "Select issue start date", Toast.LENGTH_SHORT).show();
                }else if (tvIssueEndDate.getText().length() > 10) {
                    Toast.makeText(context, "Select issue end date", Toast.LENGTH_SHORT).show();
                }else {
                    getSubmitRequest();
                    dismiss();
                }
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == nDEST_JRNY_DATE) {
            return new DatePickerDialog(context, mDateListener, nYear, nMonth, nDay);
        }
        return null;
    }

    private void showDate(int year, int month, int day, TextView textView) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        textView.setText(mSimpleDateFormat.format(new Date(year, month, day)));
    }

    // Create a  getSubmitRequest Methods. to write Json......using Volley....
    private void getSubmitRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetSubmitBookRequestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResult) {
                        if (sResult != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(sResult);
                                // if (jsonObject.getString("error").equalsIgnoreCase("false"))
                                //{
                                if (!jsonObject.getBoolean("error")) {
                                    Toast.makeText(getContext(), "Requested successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "submit_book_request");
                params.put("book_id", mBookListNames.getBookID());
                params.put("user_id", new PreferencesManager(getContext()).getUserID());
                params.put("user_type", "student");
                params.put("issue_start_date", tvIssueStartDate.getText().toString());
                params.put("issue_end_date", tvIssueEndDate.getText().toString());
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
