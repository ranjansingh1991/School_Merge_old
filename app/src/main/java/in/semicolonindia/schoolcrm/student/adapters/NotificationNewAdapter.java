package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.NotificationModel;


/**
 * Created by Rupesh on 11-10-2017.
 */
@SuppressWarnings("ALL")
public class NotificationNewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<NotificationModel> noticeNamesList = null;
    private ArrayList<NotificationModel> arraylist;

    public NotificationNewAdapter (Context context, List<NotificationModel> noticeNamesList) {
        this.context = context;
        this.noticeNamesList = noticeNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<NotificationModel>();
        this.arraylist.addAll(noticeNamesList);
    }

    @Override
    public int getCount() {
        return noticeNamesList.size();
    }

    @Override
    public NotificationModel getItem(int position) {
        return noticeNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final NotificationNewAdapter.ViewHolder holder;
        if (convertView == null) {
            holder = new NotificationNewAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.student_holiday_list_item, null);
            holder.tvHolidayTitle = (TextView) convertView.findViewById(R.id.tvHolidayTitle);
            holder.tvFromDate = (TextView) convertView.findViewById(R.id.tvFromDate);
            holder.tvToDate = (TextView) convertView.findViewById(R.id.tvToDate);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (NotificationNewAdapter.ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");

        holder.tvHolidayTitle.setText(noticeNamesList.get(position).getTitle());
        holder.tvFromDate.setText(noticeNamesList.get(position).getFromDate());
        holder.tvToDate.setText(noticeNamesList.get(position).getToDate());
        holder.tvDesp.setText(noticeNamesList.get(position).getDescription());

        holder.tvHolidayTitle.setTypeface(appFontRegular);
        holder.tvFromDate.setTypeface(appFontLight);
        holder.tvToDate.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);

        return convertView;
    }

    public class ViewHolder {
        TextView tvHolidayTitle;
        TextView tvFromDate;
        TextView tvToDate;
        TextView tvDesp;
    }
}
