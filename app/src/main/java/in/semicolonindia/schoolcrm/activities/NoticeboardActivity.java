package in.semicolonindia.schoolcrm.activities;

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

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.NoticeboardListAdapter;
import in.semicolonindia.schoolcrm.student.beans.NoticeNames;

public class NoticeboardActivity extends AppCompatActivity {

    ListView lvNoticeBoard;
    NoticeboardListAdapter mNoticeboardListAdapter;
    ArrayList<NoticeNames> arraylist = new ArrayList<NoticeNames>();
    private String[] sNoticeName;
    private String[] sDate;
    private String[] sDesp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_noticeboard);

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
        tvToolbarTitle.setText(getString(R.string.noticeboard_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoticeboardActivity.this,StudentHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });


        lvNoticeBoard = (ListView) findViewById(R.id.lvNoticeBoard);

        final Bundle bundle = this.getIntent().getExtras();
        for (int i = 0; i < bundle.getStringArray("notice_title").length; i++) {
            NoticeNames mNoticeNames = new NoticeNames(bundle.getStringArray("notice_title")[i],
                    bundle.getStringArray("date_added")[i],
                    bundle.getStringArray("notice")[i]);
            arraylist.add(mNoticeNames);
        }


        mNoticeboardListAdapter = new NoticeboardListAdapter(NoticeboardActivity.this, arraylist);
        lvNoticeBoard.setAdapter(mNoticeboardListAdapter);
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                mNoticeboardListAdapter.filter(text);
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
