package in.semicolonindia.schoolcrm.parent.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.parent.ParentHomeActivity;
@SuppressWarnings("ALL")
public class ParentFeedbackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tv_GenderLabel, tvRemainingChrs;
    private RadioGroup radioGroup;
    private RadioButton rb_Feedback, rb_excalation;
    private TextView tv_Categories;
    private Spinner spinner_Categories;
    private EditText et_Message;
    int pos;
    private Button btnCancel,btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_feedback);

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
        tvToolbarTitle.setText(getString(R.string.feedback));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ParentFeedbackActivity.this,ParentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        tv_GenderLabel = (TextView) findViewById(R.id.tv_GenderLabel);
        tvRemainingChrs = (TextView) findViewById(R.id.tvRemainingChrs);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_Feedback = (RadioButton) findViewById(R.id.rb_Feedback);
        rb_excalation = (RadioButton) findViewById(R.id.rb_excalation);
        tv_Categories = (TextView) findViewById(R.id.tv_Categories);
        spinner_Categories = (Spinner) findViewById(R.id.spinner_Categories);
        et_Message = (EditText) findViewById(R.id.et_Message);

        spinner_Categories.setOnItemSelectedListener(this);

        rb_Feedback.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    List<String> list = new ArrayList<String>();
                    list.add("Feedback 1");
                    list.add("Feedback 2");
                    list.add("Feedback 3");
                    list.add("Feedback 4");
                    list.add("Feedback 5");
                    list.add("Feedback 6");
                    ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.simple_spinner_item, list);
                    adp.setDropDownViewResource(R.layout.simple_spinner_item);
                    spinner_Categories.setAdapter(adp);
                }
            }
        });
        rb_excalation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    List<String> list = new ArrayList<String>();
                    list.add("Excalation 1");
                    list.add("Excalation 2");
                    list.add("Excalation 3");
                    list.add("Excalation 4");
                    ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.simple_spinner_item, list);
                    adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_Categories.setAdapter(adp);
                }
            }
        });

        et_Message.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int charLeft = 512 - et_Message.getText().length();
                tvRemainingChrs.setText(getApplicationContext().getResources()
                        .getString(R.string.char_limit_512_remaining) + ": " + charLeft);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rb_Feedback.setChecked(true);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // et_Message.setText("");
                et_Message.getText().clear();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        // 7.  // On selecting a spinner item.
        String item = parent.getItemAtPosition(pos).toString();
        // 8.  // Showing selected spinner item.
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onBackPressed() {

        Intent intent=new Intent(ParentFeedbackActivity.this,ParentHomeActivity.class);
        startActivity(intent);
        finish();
    }

}
