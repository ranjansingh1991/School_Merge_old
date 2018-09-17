package in.semicolonindia.schoolcrm.parent.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.iw.MapWrapperLayout;
import in.semicolonindia.schoolcrm.iw.OnInfoWindowElemTouchListener;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.pGetMapLocation;
@SuppressWarnings("ALL")
public class ParentKidsLocationMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private ProgressDialog mProgressDialog;
    private GoogleMap mMap;
    private ViewGroup infoWindow;
    private double dLatitude;
    private double dLongitude;
    private GoogleApiClient mGoogleApiClient;
    private Marker mCurrLocationMarker;
    private LocationManager location_manager;
    private Location mlocation;
    private double dLat;
    private double dLng;
    private CircleImageView civKidImage;
    private LocationRequest mLocationRequest;
    private MarkerOptions markerOptions = new MarkerOptions();
    private static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int PERMISSIONS_REQUEST_CALL = 98;
    private String sDriverPhone = "";
    private OnInfoWindowElemTouchListener infoButtonListener;
    private String[] sData;
    private ImageView imgBack;
    private RelativeLayout rlMapParent;
    String provider;


    //ArrayList<MapLocation> arrayList;

//    //12.818721, 77.585530
    //12.837533, 77.589143

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_activity_kids_location_map);

        /*final ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        final TextView tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        final Typeface appFontBold = Typeface.createFromAsset(getAssets(), "fonts/montserrat_bold.ttf");
        tvToolbarTitle.setTypeface(appFontBold);*/

        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //  1  one line..........add extra
        provider = location_manager.getBestProvider(new Criteria(), false);
        checkLocationPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        rlMapParent = (RelativeLayout) findViewById(R.id.rlMapParent);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.bringToFront();
        rlMapParent.invalidate();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //getLocationOfKids();
    }

    private void getLocationOfKids() {
        mProgressDialog = new ProgressDialog(ParentKidsLocationMapActivity.this, "Loading KidsLocationMap ...");
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, pGetMapLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        List<Marker> markers = new ArrayList<Marker>();
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray location = jsonObject.getJSONArray("location");
                            for (int i = 0; i < location.length(); i++) {
                                JSONObject locationObject = location.getJSONObject(i);
                                String sData = "";
                                if (locationObject.getString("reader_id").length() > 1) {
                                    sData = "1"
                                            + "####"
                                            + locationObject.getString("date")
                                            + "####"
                                            + locationObject.getString("location")
                                            + "####"
                                            + locationObject.getString("image_url")
                                            + "####"
                                            + locationObject.getString("reader_id")
                                            + "####"
                                            + locationObject.getString("phone");
                                } else {
                                    sData = "0"
                                            + "####"
                                            + locationObject.getString("date")
                                            + "####"
                                            + locationObject.getString("location")
                                            + "####"
                                            + locationObject.getString("image_url");
                                }
                                LatLng latlng = new LatLng(Double.parseDouble(locationObject.getString("latitude")),
                                        Double.parseDouble(locationObject.getString("longitude")));
                                new MarkerInit(latlng, locationObject.getString("name"), sData)
                                        .execute(locationObject.getString("image_url"));
                                mProgressDialog.dismiss();
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                builder.include(latlng);
                                LatLngBounds bounds = builder.build();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                            }
                        } else {
                            Toast.makeText(ParentKidsLocationMapActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Status Response", String.valueOf(error));
                mProgressDialog.dismiss();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tag", "get_kid_location");
                params.put("parent_id", new PreferencesManager(getApplicationContext()).getParentID());
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(strReq);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng latLng = new LatLng(dLatitude, dLongitude);
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                getLocationOfKids();
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setAllGesturesEnabled(true);
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7.0f));
            }
        } else {
            buildGoogleApiClient();
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7.0f));
            getLocationOfKids();
        }
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) this.findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(googleMap, getPixelsFromDp(this, 39 + 20));
        this.infoWindow = (ViewGroup) this.getLayoutInflater().inflate(R.layout.map_info_window, null);
        this.civKidImage = (CircleImageView) infoWindow.findViewById(R.id.civKidImage);
        final TextView tvKidName = (TextView) infoWindow.findViewById(R.id.tvKidName);
        final TextView tvKidLocation = (TextView) infoWindow.findViewById(R.id.tvKidLocation);
        final TextView tvKidBusRegNo = (TextView) infoWindow.findViewById(R.id.tvKidBusRegNo);
        final TextView tvUpDT = (TextView) infoWindow.findViewById(R.id.tvUpDT);
        final Button btnCallDriver = (Button) infoWindow.findViewById(R.id.btnCallDriver);

        infoButtonListener = new OnInfoWindowElemTouchListener(btnCallDriver) //btn_default_pressed_holo_light
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                if (sData[0].equalsIgnoreCase("1")) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + sData[5]));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        sDriverPhone = sData[5];
                        ActivityCompat.requestPermissions(ParentKidsLocationMapActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSIONS_REQUEST_CALL);
                    }
                }
            }
        };
        btnCallDriver.setOnTouchListener(infoButtonListener);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                sData = marker.getSnippet().split("####");
                tvKidName.setText(marker.getTitle());
                tvKidLocation.setText(getApplicationContext().getString(R.string.last_know_location) + "\n" + sData[2]);
                tvUpDT.setText(getApplicationContext().getString(R.string.updated) + "\n" + sData[1]);
                Picasso.with(getApplicationContext()).load(sData[3]).into(civKidImage);
                if (sData[0].equalsIgnoreCase("1")) {
                    tvKidBusRegNo.setText(getApplicationContext().getString(R.string.BusRegNo) + "\n" + sData[4]);
                    Drawable drawable = getApplicationContext().getDrawable(R.drawable.ic_school_bus_colored);
                    drawable.setBounds(0, 0, 40, 40);
                    tvKidBusRegNo.setCompoundDrawables(drawable, null, null, null);
                    tvKidBusRegNo.setVisibility(View.VISIBLE);
                    btnCallDriver.setVisibility(View.VISIBLE);
                } else {
                    tvKidBusRegNo.setVisibility(View.GONE);
                    btnCallDriver.setVisibility(View.GONE);
                }
                infoButtonListener.setMarker(marker);
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });
    }

    private int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        dLat = location.getLatitude();
        dLng = location.getLongitude();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case PERMISSIONS_REQUEST_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + sDriverPhone));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    class MarkerInit extends AsyncTask<String, Void, Bitmap> {
        private LatLng latLng;
        private String sData;
        private String sName;

        MarkerInit(LatLng latLng, String sName, String sData) {
            this.latLng = latLng;
            this.sData = sData;
            this.sName = sName;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                bitmap = Bitmap.createScaledBitmap(bitmap, 96, 96, false);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            markerOptions.position(latLng);
            markerOptions.title(sName);
            markerOptions.snippet(sData);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(addWhiteBorder(bitmap, 6)));
            mCurrLocationMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
        }

        private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
            Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2,
                    bmp.getHeight() + borderSize * 2, bmp.getConfig());
            Canvas canvas = new Canvas(bmpWithBorder);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bmp, borderSize, borderSize, null);
            return bmpWithBorder;
        }
    }

}