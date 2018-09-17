package in.semicolonindia.schoolcrm.teacher.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherGalleryRVAdapterDemo;

@SuppressWarnings("ALL")
public class TeacherGalleryActivity extends AppCompatActivity {
    //1. Declear All Variable....
    private WebView webView;
    private RecyclerView rvImageList;
    private ProgressBar pbImageLoading;
    private String[] images = {
            "http://semicolonindia.in/TestNDemo/1.jpg",
            "http://semicolonindia.in/TestNDemo/2.jpg",
            "http://semicolonindia.in/TestNDemo/3.jpg",
            "http://semicolonindia.in/TestNDemo/4.jpg",
            "http://semicolonindia.in/TestNDemo/5.jpg",
            "http://semicolonindia.in/TestNDemo/6.jpg",
            "http://semicolonindia.in/TestNDemo/7.jpg",
            "http://semicolonindia.in/TestNDemo/1.jpg",
            "http://semicolonindia.in/TestNDemo/2.jpg",
            "http://semicolonindia.in/TestNDemo/3.jpg",
            "http://semicolonindia.in/TestNDemo/4.jpg",
            "http://semicolonindia.in/TestNDemo/5.jpg",
            "http://semicolonindia.in/TestNDemo/6.jpg",
            "http://semicolonindia.in/TestNDemo/7.jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_activity_gallery);
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
        tvToolbarTitle.setText(getString(R.string.gallery_));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherGalleryActivity.this, TeacherHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });
        // 2. Intializing ALL Variable.....
        webView = (WebView) findViewById(R.id.webView);
        rvImageList = (RecyclerView) findViewById(R.id.rvImageList);
        pbImageLoading = (ProgressBar) findViewById(R.id.pbImageLoading);
        // 3. Intilizing....... Relative and pass id for image to show button on screen...
        final RelativeLayout rlGalleryParent = (RelativeLayout) findViewById(R.id.rL_Gallery_Parent);
        rvImageList.bringToFront();
        rlGalleryParent.invalidate();
        // 4. Creating TeacherGalleryRVAdapterDemo Adeapter to pass images...
        final TeacherGalleryRVAdapterDemo teacherGalleryRVAdapterDemo = new TeacherGalleryRVAdapterDemo(getApplicationContext(),
                images, webView);
        //5. Here we uesd setLayoutManager in (HORIZONTAL) formate to show images...
        rvImageList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false));
        rvImageList.setAdapter(teacherGalleryRVAdapterDemo);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String html = "<style>img{max-width: 100%;} body{background-color:#000000;position: absolute;top: 0;bottom: 0;left: 0;" +
                "right: 0;display: flex;justify-content: center;align-items: center;}</style> <html><head></head><body><center><img src=\""
                + images[0] + "\"></center></body></html>";
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...

            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pbImageLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(TeacherGalleryActivity.this, TeacherHomeActivity.class);
        startActivity(intent);
        finish();
    }
}