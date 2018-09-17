package in.semicolonindia.schoolcrm.teacher.activities;

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
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherExtracurriculamListAdapter;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherExtraCurriculamNames;

public class TeacherExtraCurriculumActivity extends AppCompatActivity {
    ListView lvExtracurrilam;
    TeacherExtracurriculamListAdapter teacherExtracurriculamListAdapter;
    ArrayList<TeacherExtraCurriculamNames> arraylist = new ArrayList<>();

    private int[] sImg;
    private String[] sDate;
    private String[] sTime;
    private String[] sDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_extra_curriculum);
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
        tvToolbarTitle.setText(getString(R.string.ExtraCurriculam));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TeacherExtraCurriculumActivity.this, TeacherHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        sImg = new int[]{R.drawable.dummy_face_icon_1,
                R.drawable.dummy_face_icon,
                R.drawable.dummy_face_1,
                R.drawable.dummy_icon_2,
                R.drawable.dummy_face_2,
                R.drawable.ic_user_female,
                R.drawable.dummy_face_2,};

        sDate = new String[]{"06/28/2017", "06/20/2017", "06/27/2017",
                "06/24/2017", "06/22/2017", "06/21/2017",
                "04/29/2010",};

        sTime = new String[]{"01:12:00", "01:15:00", "01:18:00",
                "01:30:00", "01:11:00", "01:12:00",
                "01:12:06",};

        sDesp = new String[]{"Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",};

        lvExtracurrilam = (ListView) findViewById(R.id.lvExtracurrilam);

        for (int i = 0; i < sDate.length; i++) {
            TeacherExtraCurriculamNames teacherExtraCurriculamNames = new TeacherExtraCurriculamNames(sImg[i],
                    sDate[i], sTime[i], sDesp[i]);
            arraylist.add(teacherExtraCurriculamNames);
        }

        teacherExtracurriculamListAdapter = new TeacherExtracurriculamListAdapter(getApplicationContext(), arraylist);
        lvExtracurrilam.setAdapter(teacherExtracurriculamListAdapter);

    }
}
