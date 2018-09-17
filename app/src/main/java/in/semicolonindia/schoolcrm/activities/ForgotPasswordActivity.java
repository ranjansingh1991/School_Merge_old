package in.semicolonindia.schoolcrm.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sForgotPasswordURL;


@SuppressWarnings("ALL")
public class ForgotPasswordActivity extends AppCompatActivity
        implements View.OnClickListener {
    EditText etEmail;
    Button btnContinue;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_forgot_password);

        final TextView tvAppName = (TextView) findViewById(R.id.tvAppName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        final TextView tvForgetPwdMsg = (TextView) findViewById(R.id.tvForgetPwdMsg);

        Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        Typeface appFontRegular = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(getAssets(), "fonts/montserrat_light.ttf");

        tvAppName.setTypeface(appFontBold);
        etEmail.setTypeface(appFontRegular);
        btnContinue.setTypeface(appFontRegular);
        tvForgetPwdMsg.setTypeface(appFontLight);

        btnContinue.setOnClickListener(this);

    }

    private void recoverPassword() {
        email = etEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, sForgotPasswordURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                Toast.makeText(ForgotPasswordActivity.this, "Your new password is sent to your email.", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Oops! Something went wrong, please try again.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPasswordActivity.this, "Oops! Something went wrong, please try again.", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", email);
                map.put("authenticate", "false");
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    public void onClick(View v) {
        if (v == btnContinue) {
            if (etEmail.getText().length() < 1) {
                Toast.makeText(ForgotPasswordActivity.this, "Email id can not be blank", Toast.LENGTH_SHORT).show();
            } else {
                recoverPassword();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);

    }
}
