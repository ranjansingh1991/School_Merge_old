package in.semicolonindia.schoolcrm.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetNewMsg;

/**
 * Created by Rupesh on 01-02-2018.
 */

public class MessageService extends Service {

    private static final String TAG = "MessageService";
    private final IBinder mBinder = new MessageServiceBinder();
    private NotificationManager notifyMgr;
    private NotificationCompat.Builder nBuilder;
    private NotificationCompat.InboxStyle inboxStyle;

    public class MessageServiceBinder extends Binder {
        public MessageService getService() {
            return MessageService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        checkNewMessage();
        new CountDownTimer(900000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                checkNewMessage();
                start();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(getApplicationContext(), MessageService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand Called");
        return START_STICKY; //START_REDELIVER_INTENT;
    }

    private void checkNewMessage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetNewMsg, new Response.Listener<String>() {
            @Override
            public void onResponse(String sResult) {
                if (sResult != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(sResult);
                        JSONArray jsonArray = jsonObject.getJSONArray("message");
                        if (jsonArray.length() > 0) {
                            String data = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                if (data.length() < 1) {
                                    data = jsonArray.getJSONObject(i).getString("sender") + " @" +
                                            jsonArray.getJSONObject(i).getString("time");
                                } else {
                                    data = data + "\n" + jsonArray.getJSONObject(i).getString("sender") + " @" +
                                            jsonArray.getJSONObject(i).getString("time");
                                }
                            }
                            notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            Intent attractionintent = new Intent(MessageService.this,
                                    in.semicolonindia.schoolcrm.student.activities.MessageActivity.class);
                            PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                    1, attractionintent, PendingIntent.FLAG_UPDATE_CURRENT);
                            nBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(MessageService.
                                    this).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .setDefaults(Notification.DEFAULT_ALL)
                                    .setVibrate(new long[]{1000})
                                    .setLights(Color.RED, 3000, 3000)
                                    .setContentTitle(getString(R.string.app_name)
                                            + " - " + getString(R.string.notify_msg_text))
                                    .setContentText(data)
                                    .setOnlyAlertOnce(true)
                                    .setPriority(Notification.PRIORITY_MAX);
                            // Allows notification to be cancelled when user clicks it
                            nBuilder.setAutoCancel(true);
                            nBuilder.setStyle(inboxStyle);
                            nBuilder.setContentIntent(resultPendingIntent);
                            notifyMgr.notify(1, nBuilder.build());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Dismissing the progress dialog
                Log.e("status Response", String.valueOf(volleyError));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Creating parameters
                Map<String, String> params = new HashMap<>();
                params.put("reciever_id", new PreferencesManager(getApplicationContext()).getUserID());
                params.put("reciever_type", "student");
                params.put("authenticate", "false");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}