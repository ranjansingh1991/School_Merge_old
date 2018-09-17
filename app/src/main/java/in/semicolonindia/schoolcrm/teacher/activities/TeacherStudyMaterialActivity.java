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
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherStudyMatListAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherStudyMatNames;
@SuppressWarnings("ALL")
public class TeacherStudyMaterialActivity extends AppCompatActivity {
    ListView lvStudyMat;
    TeacherStudyMatListAdapter mTeacherStudyMatListAdapter;
    private String[] sTitle;
    private String[] sSubject;
    private String[] sClass;
    private String[] sDate;
    private String[] sDesp;
    ArrayList<TeacherStudyMatNames> arraylist = new ArrayList<TeacherStudyMatNames>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_study_material);
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
        tvToolbarTitle.setText(getString(R.string.study_material_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        lvStudyMat = (ListView) findViewById(R.id.lvStudyMat);

        final Bundle bundle = this.getIntent().getExtras();
        for (int i = 0; i < bundle.getStringArray("title").length; i++) {
            TeacherStudyMatNames mTeacherStudyMatNames = new TeacherStudyMatNames(bundle.getStringArray("title")[i],
                    bundle.getStringArray("subject_name")[i],
                    bundle.getStringArray("class_name")[i],
                    bundle.getStringArray("date_added")[i],
                    bundle.getStringArray("file_name")[i],
                    bundle.getStringArray("description")[i]);
            arraylist.add(mTeacherStudyMatNames);
        }

        mTeacherStudyMatListAdapter = new TeacherStudyMatListAdapter(TeacherStudyMaterialActivity.this, arraylist);
        lvStudyMat.setAdapter(mTeacherStudyMatListAdapter);

        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mTeacherStudyMatListAdapter.filter(text);
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
