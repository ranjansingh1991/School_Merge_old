package in.semicolonindia.schoolcrm.teacher.activities;

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

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherSubjectListAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherSubjectNames;
@SuppressWarnings("ALL")
public class TeacherSubjectActivity extends AppCompatActivity {
    ListView lvSubjects;
    TeacherSubjectListAdapter mTeacherSubjectListAdapter;
    ArrayList<TeacherSubjectNames> arraylist = new ArrayList<TeacherSubjectNames>();
    private  String[] sNames;
    private  String[] sStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_subject);
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("");
        final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);
        tvToolbarTitle.setText(getString(R.string.subjects));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvSubjects = (ListView) findViewById(R.id.lvSubjects);

        final Bundle bundle = this.getIntent().getExtras();
        for (int i = 0; i < bundle.getStringArray("name").length; i++) {
            TeacherSubjectNames mTeacherSubjectNames = new TeacherSubjectNames(bundle.getStringArray("name")[i],
                    bundle.getStringArray("year")[i]);
            arraylist.add(mTeacherSubjectNames);
        }

        mTeacherSubjectListAdapter = new TeacherSubjectListAdapter(getApplicationContext(), arraylist);
        lvSubjects.setAdapter(mTeacherSubjectListAdapter);
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mTeacherSubjectListAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

    }
}
