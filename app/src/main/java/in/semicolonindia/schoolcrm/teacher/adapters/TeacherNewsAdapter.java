package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherNewsName;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherNewsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<TeacherNewsName> assignmentNamesList = null;
    private ArrayList<TeacherNewsName> arraylist;

    public TeacherNewsAdapter(Context context, List<TeacherNewsName> assignmentNamesList) {
        this.context = context;
        this.assignmentNamesList = assignmentNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherNewsName>();
        this.arraylist.addAll(assignmentNamesList);
    }

    @Override
    public int getCount() {
        return assignmentNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignmentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final TeacherNewsAdapter.ViewHolder holder;
        if (view == null) {
            holder = new TeacherNewsAdapter.ViewHolder();
            view = inflater.inflate(R.layout.teacher_news_details_list, null);
            holder.tv_NewsTitle = (TextView) view.findViewById(R.id.tv_NewsTitle);
            holder.tv_NewsDate = (TextView) view.findViewById(R.id.tv_NewsDate);
            holder.tv_NewsTime = (TextView) view.findViewById(R.id.tv_NewsTime);
            holder.tv_NewsAdver = (TextView) view.findViewById(R.id.tv_NewsAdver);
            holder.tv_NewsDesp = (TextView) view.findViewById(R.id. tv_NewsDesp);
            holder.img_News = (CircleImageView) view.findViewById(R.id.img_News);
            view.setTag(holder);
        } else {
            holder = (TeacherNewsAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tv_NewsTitle.setText(assignmentNamesList.get(position).getsTitle());
        holder.tv_NewsDate.setText(assignmentNamesList.get(position).getsDate());
        holder.tv_NewsTime.setText(assignmentNamesList.get(position).getsTime());
        holder.tv_NewsAdver.setText(assignmentNamesList.get(position).getsAdvertisement());
        holder.tv_NewsDesp.setText(assignmentNamesList.get(position).getsDes());
        // Here set image ................
        holder.img_News.setImageResource(Integer.parseInt(String.valueOf(assignmentNamesList.get(position).getsImages())));

        holder.tv_NewsTitle.setTypeface(appFontRegular);
        holder.tv_NewsDate.setTypeface(appFontLight);
        holder.tv_NewsTime.setTypeface(appFontLight);
        holder.tv_NewsAdver.setTypeface(appFontLight);
        holder.tv_NewsDesp.setTypeface(appFontLight);



        return view;
    }

    public static class ViewHolder {
        TextView tv_NewsTitle;
        CircleImageView img_News;
        TextView tv_NewsDate;
        TextView tv_NewsTime;
        TextView tv_NewsAdver;
        TextView tv_NewsDesp;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        assignmentNamesList.clear();
        if (charText.length() == 0) {
            assignmentNamesList.addAll(arraylist);
        } else {
            for (TeacherNewsName wp : arraylist) {
                if (wp.getsTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    assignmentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
