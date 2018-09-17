package in.semicolonindia.schoolcrm.student.activities;

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
import in.semicolonindia.schoolcrm.student.adapters.NewsAdapter;
import in.semicolonindia.schoolcrm.student.beans.NewsName;
@SuppressWarnings("ALL")
public class NewsActivity extends AppCompatActivity {
    // 1. Declear All Variable....
    private ListView lv_News;
    private ListView lvStudyMat;
    NewsAdapter newsAdapter;
    //String[] sTitle;
    private int[] sImages;
    private String[] sDate;
    private  String[] sTime;
    private  String[] sAdvertisement;
    private String[] sDes;
    ArrayList<NewsName> arraylist = new ArrayList<NewsName>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_news);
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
        tvToolbarTitle.setText(getString(R.string.news));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sImages = new int[]
                {
                    R.drawable.dummy_face_1,
                    R.drawable.dummy_face_icon_1,
                    R.drawable.dummy_icon_2,
                    R.drawable.dummy_face_icon,
                    R.drawable.dummy_face_2,
                    R.drawable.ic_user_female,
                    R.drawable.dummy_face_icon,
                 };

        sDate = new String[]
                {
                        "20/10/2017",
                        "21/10/2017",
                        "22/10/2017",
                        "23/10/2017",
                        "24/10/2017",
                        "25/10/2017",
                        "26/10/2017"};

        sTime = new String[]{
                "01:09:20",
                "05:010:21",
                "06:11:28",
                "11:02:30",
                "01:08:20",
                "21:08:20",
                "05:09:29"
        };

        sAdvertisement = new String[]
                {
                "Toothpaste",
                "Soap",
                "Cloth",
                "Oil",
                "Cream",
                "Shoes",
                "Pot"
        };

        sDes = new String[]{
                "your life. I am sure you are excited. There are few days in human life when one is truly elated.",
                "The first day in college is one of them.  When you were getting ready today, you felt a tingling in your stomach",
                "What would the auditorium be like, what would the teachers be like, who are my new classmates",
                "there is so much to be curious about. I call this excitement, the spark within you that makes you feel truly alive today",
                "Today I am going to talk about keeping the spark shining. Or to put it another way, how to be happy most, if not all the time.",
                "Where do these sparks start? I think we are born with them. My 3-year old twin boys have a million sparks.",
                "A little Spiderman toy can make them jump on the bed.",
        };

        //2. Intializing All Variable...
        lv_News= (ListView) findViewById(R.id.lv_News);

        for (int i=0; i<sDate.length; i++){
            NewsName newsName = new NewsName(sImages[i],sDate[i],sTime[i],sAdvertisement[i],sDes[i]);
            arraylist.add(newsName);
        }
        newsAdapter=new NewsAdapter(getApplicationContext(),arraylist);
        lv_News.setAdapter(newsAdapter);

    }

}
