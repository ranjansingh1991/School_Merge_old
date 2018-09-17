package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherBenchNames;

/**
 * Created by Rupesh on 07-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherBenchListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<TeacherBenchNames> teacherBenchNamesList = null;
    private ArrayList<TeacherBenchNames> arraylist;

    public TeacherBenchListAdapter(Context context, List<TeacherBenchNames> teacherBenchNamesList) {
        this.context = context;
        this.teacherBenchNamesList = teacherBenchNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherBenchNames>();
        this.arraylist.addAll(teacherBenchNamesList);
    }

    @Override
    public int getCount() {
        return teacherBenchNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherBenchNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.teacher_bench_list_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_Kid);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_Name);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_Title);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_Date);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_Time);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDesp);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.imageView.setImageResource(Integer.parseInt(String.valueOf(teacherBenchNamesList.get(position).getImage_url())));
        holder.tvName.setText(teacherBenchNamesList.get(position).getsName());
        holder.tvTitle.setText(teacherBenchNamesList.get(position).getsTitle());
        holder.tvDate.setText(teacherBenchNamesList.get(position).getsDate());
        holder.tvTime.setText(teacherBenchNamesList.get(position).getsTime());
        holder.tvDescription.setText(teacherBenchNamesList.get(position).getsDesp());
        holder.tvName.setTypeface(appFontLight);
        holder.tvTime.setTypeface(appFontLight);

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherBenchNamesList.clear();
        if (charText.length() == 0) {
            teacherBenchNamesList.addAll(arraylist);
        } else {
            for (TeacherBenchNames wp : arraylist) {
                if (wp.getsName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherBenchNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvTitle;
        TextView tvDate;
        TextView tvTime;
        TextView tvDescription;

    }

}
