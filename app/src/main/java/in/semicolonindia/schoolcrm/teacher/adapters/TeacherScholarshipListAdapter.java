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
import in.semicolonindia.schoolcrm.teacher.beans.TeacherScholarNames;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherScholarshipListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<TeacherScholarNames> teacherScholarNamesList = null;
    private ArrayList<TeacherScholarNames> arraylist;

    public TeacherScholarshipListAdapter(Context context, List<TeacherScholarNames> teacherScholarNamesList) {
        this.context = context;
        this.teacherScholarNamesList = teacherScholarNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherScholarNames>();
        this.arraylist.addAll(teacherScholarNamesList);
    }

    @Override
    public int getCount() {
        return teacherScholarNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherScholarNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.teacher_scholarship_list_item, null);

            holder.tvDate = (TextView) convertView.findViewById(R.id.tvScholarDate);
            holder.tvClass = (TextView) convertView.findViewById(R.id.tvSClass);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDesp);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvDate.setText(teacherScholarNamesList.get(position).getsDate());
        holder.tvClass.setText(teacherScholarNamesList.get(position).getsClass());
        holder.tvDescription.setText(teacherScholarNamesList.get(position).getsDesp());


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherScholarNamesList.clear();
        if (charText.length() == 0) {
            teacherScholarNamesList.addAll(arraylist);
        } else {
            for (TeacherScholarNames wp : arraylist) {
                if (wp.getsClass().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherScholarNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tvDate;
        TextView tvClass;
        TextView tvDescription;
    }
}
