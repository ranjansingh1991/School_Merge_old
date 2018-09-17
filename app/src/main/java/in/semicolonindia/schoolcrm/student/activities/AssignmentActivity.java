package in.semicolonindia.schoolcrm.student.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import in.semicolonindia.schoolcrm.activities.StudentHomeActivity;
import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.StudentAssignmentAdapter;
import in.semicolonindia.schoolcrm.student.beans.AssignmentName;
import in.semicolonindia.schoolcrm.student.StudentHomeMenuFragment;
@SuppressWarnings("ALL")
public class AssignmentActivity extends AppCompatActivity {

    ListView listView;
    StudentAssignmentAdapter studentAssignmentAdapter;
    ArrayList<AssignmentName> arraylist = new ArrayList<AssignmentName>();
    StudentHomeMenuFragment fActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_assignment);

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
        tvToolbarTitle.setText(getString(R.string.assignment_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getFragmentManager().popBackStackImmediate();
                // onBackPressed();
                Intent intent = new Intent(AssignmentActivity.this, StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.lv_Assignment);

        final Bundle bundle = this.getIntent().getExtras();
        for (int i = 0; i < bundle.getStringArray("title").length; i++) {
            AssignmentName mAssignmentName = new AssignmentName(
                    bundle.getStringArray("title")[i],
                    bundle.getStringArray("description")[i],
                    bundle.getStringArray("upload_date")[i],
                    bundle.getStringArray("submit_date")[i],
                    bundle.getStringArray("marks")[i],
                    bundle.getStringArray("report_to")[i],
                    bundle.getStringArray("class_id")[i],
                    bundle.getStringArray("file_name")[i]
                    //bundle.getStringArray("file_type")[i]
            );
            arraylist.add(mAssignmentName);
            studentAssignmentAdapter = new StudentAssignmentAdapter(AssignmentActivity.this, arraylist);
            listView.setAdapter(studentAssignmentAdapter);
        }

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                studentAssignmentAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });


    }
   /* @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(), StudentHomeActivity.class);
        startActivity(setIntent);
        finish();
    }*/


}
