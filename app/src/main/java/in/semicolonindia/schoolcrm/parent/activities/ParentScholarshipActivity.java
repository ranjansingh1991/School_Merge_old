package in.semicolonindia.schoolcrm.parent.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.parent.ParentHomeActivity;
import in.semicolonindia.schoolcrm.student.activities.ScholarshipActivity;
import in.semicolonindia.schoolcrm.student.adapters.ScholarshipListAdapter;
import in.semicolonindia.schoolcrm.student.beans.ScholarNames;
@SuppressWarnings("ALL")
public class ParentScholarshipActivity extends AppCompatActivity {
    ListView lvScholar;
    ScholarshipListAdapter scholarshipListAdapter;
    ArrayList<ScholarNames> arraylist = new ArrayList<ScholarNames>();
    private String[] sDate;
    private String[] sClass;
    private String[] sDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_scholarship);

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
        tvToolbarTitle.setText(getString(R.string.Scholarship));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ParentScholarshipActivity.this, ParentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        sDate = new String[]{"06/28/2017", "06/28/2017", "06/28/2017",
                "06/28/2017", "06/28/2017","06/28/2017", "06/28/2017", "06/28/2017" };

        sClass = new String[]{"Class 1", "Class 2", "Class 3",
                "Class 4", "Class 5", "Class 6", "Class 7", "Class 8"};

        sDesp = new String[]{"Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",};

        lvScholar = (ListView) findViewById(R.id.lvScholarship);

        for (int i = 0; i < sClass.length; i++) {
            ScholarNames scholarNames = new ScholarNames(sDate[i],
                    sClass[i], sDesp[i]);
            arraylist.add(scholarNames);
        }
        scholarshipListAdapter = new ScholarshipListAdapter(getApplicationContext(), arraylist);
        lvScholar.setAdapter(scholarshipListAdapter);
    }
}
