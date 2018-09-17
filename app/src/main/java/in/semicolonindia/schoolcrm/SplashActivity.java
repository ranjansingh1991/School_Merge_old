package in.semicolonindia.schoolcrm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.activities.StudentHomeActivity;
import in.semicolonindia.schoolcrm.activities.LoginActivity;
import in.semicolonindia.schoolcrm.parent.ParentHomeActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherHomeActivity;
import in.semicolonindia.schoolcrm.util.PreferencesManager;
import in.semicolonindia.schoolcrm.widgets.AVLoadingIndicatorViewLight;

@SuppressWarnings("ALL")
public class SplashActivity extends AppCompatActivity {

    private final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private final int REQUEST_CHECK_SETTINGS = 0x1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_splash);
        checkPermissions();
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        final AVLoadingIndicatorViewLight loadingIndicator = (AVLoadingIndicatorViewLight) findViewById(R.id.loadingIndicator);
        final TextView tvAppName = (TextView) findViewById(R.id.tvAppName);
        final TextView tvProgressTitle = (TextView) findViewById(R.id.tvProgressTitle);
        tvAppName.setTypeface(appFontBold);
        tvProgressTitle.setTypeface(appFontBold);
        loadingIndicator.setIndicator("SquareSpinIndicator");
        loadingIndicator.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else {
            showSettingDialog();
        }
    }

    /* Show Location Access Dialog */
    private void showSettingDialog() {
        LocationDetector mLocationDetector = new LocationDetector(SplashActivity.this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationDetector.showSettingDialog();
        } else {
            openNextScreen();
        }
    }

    /*  Show Popup to access User Permission  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        } else {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }



    //On Request permission method to check the permisison is granted or not for Marshmallow+ Devices
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //If permission granted show location dialog if APIClient is not null
                    showSettingDialog();
                } else {
                    Log.e("About GPS", "Location Permission denied.");
                    finish();
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.e("Settings", "Result OK");
                        openNextScreen();
                        break;
                    case RESULT_CANCELED:
                        Log.e("Settings", "Result Cancel");
                        finish();
                        System.exit(0);
                        break;
                }

                break;
        }
    }
    private void openNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               //1.here getLogedIn first check app is login or not if login it will come to StudentHomeActivity,
                // 2 TeacherHomeActivity,ParentHomeActivity. if getLogedIn is not login it will come first LoginActivity.
                if (new PreferencesManager(getApplicationContext()).getLogedIn()) {
                    Intent mIntent = new Intent();
                    if (new PreferencesManager(getApplicationContext()).getLogintype().equalsIgnoreCase("student")) {
                        mIntent = new Intent(getApplicationContext(), StudentHomeActivity.class);
                    } else if (new PreferencesManager(getApplicationContext()).getLogintype().equalsIgnoreCase("teacher")) {
                        mIntent = new Intent(getApplicationContext(), TeacherHomeActivity.class);
                    } else if (new PreferencesManager(getApplicationContext()).getLogintype().equalsIgnoreCase("parent")) {
                        mIntent = new Intent(getApplicationContext(), ParentHomeActivity.class);
                    }
                    startActivity(mIntent);
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                }
                finish();
            }
        }, 3000);
    }
}
