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
import in.semicolonindia.schoolcrm.student.adapters.StudentSyllabusListAdapter;
import in.semicolonindia.schoolcrm.student.beans.SyllabusNames;
@SuppressWarnings("ALL")
public class SyllabusActivity extends AppCompatActivity {

    ListView lvSyllabus;
    StudentSyllabusListAdapter mStudentSyllabusListAdapter;
    private String[] sTitle;
    private String[] sSubject;
    private String[] sUploader;
    private String[] sDate;
    private String[] sFile;
    private String[] sDesp;
    ArrayList<SyllabusNames> arraylist = new ArrayList<SyllabusNames>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_syllabus);

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
        tvToolbarTitle.setText(getString(R.string.academic_syllabus_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SyllabusActivity.this, StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        lvSyllabus = (ListView) findViewById(R.id.lvSyllabus);

        final Bundle bundle = this.getIntent().getExtras();
        for (int i = 0; i < bundle.getStringArray("title").length; i++) {
            SyllabusNames mSyllabusNames = new SyllabusNames(bundle.getStringArray("title")[i],
                    bundle.getStringArray("subject_name")[i],
                    bundle.getStringArray("uploader_type")[i],
                    bundle.getStringArray("year")[i],
                    bundle.getStringArray("file_name")[i],
                    bundle.getStringArray("description")[i]
            );

            arraylist.add(mSyllabusNames);
        }

        mStudentSyllabusListAdapter = new StudentSyllabusListAdapter(SyllabusActivity.this, arraylist);
        lvSyllabus.setAdapter(mStudentSyllabusListAdapter);
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mStudentSyllabusListAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(getApplicationContext(), StudentHomeActivity.class);
        startActivity(setIntent);
        finish();
    }
}


