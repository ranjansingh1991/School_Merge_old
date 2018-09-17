package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.AttendanceData;

/**
 * Created by RANJAN SINGH on 11/16/2017.
 */
@SuppressWarnings("ALL")
public class StudentAttendanceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<AttendanceData>[] mAttendanceData;
    private String[] subjects;
    private String[] sAtndPer;
    private int nMonth;

    //Here create a Constructer...
    public StudentAttendanceAdapter(Context context, List<AttendanceData>[] mAttendanceData, String[] subjects,
                                    int nMonth) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mAttendanceData = mAttendanceData;
        this.subjects = subjects;
        this.nMonth = nMonth;
        this.sAtndPer = new String[mAttendanceData.length];
        for (int i = 0; i < mAttendanceData.length; i++) {
            List<AttendanceData> temp = mAttendanceData[i];
            int nPersent = 0;
            for (int j = 0; j < temp.size(); j++) {
                if (temp.get(j).getStatus().equalsIgnoreCase("1")) {
                    nPersent++;
                }
            }
            try {
                this.sAtndPer[i] = String.valueOf((nPersent * 100) / temp.size()) + "%";
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getCount() {
        return subjects.length;
    }

    @Override
    public Object getItem(int position) {
        return mAttendanceData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.student_attendance_list_item, null);
            holder.tvSubName = (TextView) view.findViewById(R.id.tvSubName);
            holder.tvAtndPer = (TextView) view.findViewById(R.id.tvAtndPer);
            holder.rvAttendanceData = (RecyclerView) view.findViewById(R.id.rvAttendanceData);
            holder.rvAttendanceData.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.rvAttendanceData.setAdapter(new AttendanceDataRVAdapter(context, mAttendanceData[position], nMonth));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        holder.tvSubName.setTypeface(appFontRegular);
        holder.tvSubName.setText(subjects[position]);
        holder.tvAtndPer.setText(sAtndPer[position]);
        holder.tvSubName.setTypeface(appFontRegular);
        holder.tvAtndPer.setTypeface(appFontRegular);


        return view;
    }

    public class ViewHolder {
        TextView tvSubName;
        TextView tvAtndPer;
        RecyclerView rvAttendanceData;
    }
}

