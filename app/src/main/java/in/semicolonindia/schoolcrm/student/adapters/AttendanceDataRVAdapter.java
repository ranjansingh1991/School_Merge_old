package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.AttendanceData;


/**
 * Created by RANJAN SINGH on 11/16/2017.
 */
@SuppressWarnings("ALL")
public class AttendanceDataRVAdapter extends RecyclerView.Adapter<AttendanceDataRVAdapter.ViewHolder> {

    private Context context;
    private List<Integer> listStatus = new ArrayList<>();
    private String[] sDays;
    private int nMonth;

    public AttendanceDataRVAdapter(Context context, List<AttendanceData> mAttendanceData, int nMonth) {
        this.context = context;
        this.nMonth = nMonth;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        sDays = new String[numberOfDaysInMonth(nMonth, Integer.parseInt(sdf.format(new Date())))];
        for (int i = 0; i < sDays.length; i++) {
            sDays[i] = String.valueOf(i + 1);
            listStatus.add(0);
        }

        for (int i = 0; i < mAttendanceData.size(); i++) {
            for (int j = 0; j < this.listStatus.size(); j++) {
                String[] sDate = mAttendanceData.get(i).getDate().split("-");
                if (sDate[0].equals(String.valueOf(j))) {
                    this.listStatus.set((j - 1), Integer.parseInt(mAttendanceData.get(i).getStatus()));
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attandance_list_item_data, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        holder.tvDays.setTypeface(appFontRegular);
        holder.tvStatus.setTypeface(appFontRegular);
        holder.tvDays.setText(String.valueOf(position + 1));
        switch (listStatus.get(position)) {
            case 1:
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorButtonBGStatusSuccess));
                holder.tvStatus.setText("P");
                break;
            case 2:
                holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorButtonBGStatusFailure));
                holder.tvStatus.setText("A");
                break;
            default:
                holder.tvStatus.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
                holder.tvStatus.setText("N/A");
                break;
        }
        holder.tvDays.setText(sDays[position]);
    }

    @Override
    public int getItemCount() {
        return sDays.length;
    }

    private int numberOfDaysInMonth(int month, int year) {
        int iMonth = month - 1;
        int iDay = 1;

// Create a calendar object and set year and month
        Calendar cal = new GregorianCalendar(year, iMonth, iDay);
// Get the number of days in that month
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDays;
        TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDays = (TextView) itemView.findViewById(R.id.tvDays);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        }
    }
}