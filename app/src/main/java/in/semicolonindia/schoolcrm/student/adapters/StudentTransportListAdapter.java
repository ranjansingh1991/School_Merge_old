package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.StudentTransportNames;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

import static in.semicolonindia.schoolcrm.rest.BaseUrl.sGetTransportRequestURL;

/**
 * Created by Rupesh on 01-02-2018.
 */
@SuppressWarnings("ALL")
public class StudentTransportListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<StudentTransportNames> studentTransportNamesList = null;
    private ArrayList<StudentTransportNames> arraylist;

    public StudentTransportListAdapter(Context context, List<StudentTransportNames> studentTransportNamesList, ListView lvTransport) {
        this.context = context;
        this.studentTransportNamesList = studentTransportNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<StudentTransportNames>();
        this.arraylist.addAll(studentTransportNamesList);
    }

    @Override
    public int getCount() {
        return studentTransportNamesList.size();
    }

    @Override
    public StudentTransportNames getItem(int position) {
        return studentTransportNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.student_transport_list_items, null);
            holder.tvRouteName = (TextView) convertView.findViewById(R.id.tvRouteName);
            holder.btnRequestTransport = (Button) convertView.findViewById(R.id.btnRequestTransport);
            holder.tvNoOfVehicles = (TextView) convertView.findViewById(R.id.tvNoOfVehicles);
            holder.tvRoutFare = (TextView) convertView.findViewById(R.id.tvRoutFare);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvRouteName.setText(studentTransportNamesList.get(position).getRouteName());
        holder.tvNoOfVehicles.setText(context.getString(R.string.no_of_vehicles) + studentTransportNamesList.get(position).getNumberOfVehicle());
        holder.tvRoutFare.setText(context.getString(R.string.rupee_symbol) + studentTransportNamesList.get(position).getRoutFare());
        holder.tvDesp.setText(studentTransportNamesList.get(position).getDesp());
        holder.tvRouteName.setTypeface(appFontRegular);
        holder.btnRequestTransport.setTypeface(appFontLight);
        holder.tvNoOfVehicles.setTypeface(appFontLight);
        holder.tvRoutFare.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);


        holder.btnRequestTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getTransport(studentTransportNamesList.get(position).getTransport_id());
            }
        });
        if ((studentTransportNamesList.get(position).getTransport_id()).equalsIgnoreCase("1")){
            holder.btnRequestTransport.setBackground(context.getDrawable(R.drawable.button_background_status_disable));
            holder.btnRequestTransport.setEnabled(false);
            holder.btnRequestTransport.setText(context.getResources().getString(R.string.requested));
        }else{
            holder.btnRequestTransport.setBackground(context.getDrawable(R.drawable.button_background_primary));
            holder.btnRequestTransport.setEnabled(true);
            holder.btnRequestTransport.setText(context.getResources().getString(R.string.request));
        }

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        studentTransportNamesList.clear();
        if (charText.length() == 0) {
            studentTransportNamesList.addAll(arraylist);
        } else {
            for (StudentTransportNames wp : arraylist) {
                if (wp.getRouteName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    studentTransportNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvRouteName;
        Button btnRequestTransport;
        TextView tvNoOfVehicles;
        TextView tvRoutFare;
        TextView tvDesp;
    }

    // Create a  getSubmitRequest Methods. to write Json......using Volley....

    private void getTransport(final String transID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sGetTransportRequestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sResult) {
                        if (sResult != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(sResult);
                                if (!jsonObject.getBoolean("error")) {
                                    Toast.makeText(context, "Requested successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Oops! Something went wrong. Try again later.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                Map<String, String> params = new HashMap<>();
                params.put("tag", "submit_transport_request");
                params.put("transport_id", transID);
                params.put("user_type", "student");
                params.put("user_id", new PreferencesManager(context).getUserID());
                params.put("authenticate", "false");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}






