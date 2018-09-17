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
import in.semicolonindia.schoolcrm.teacher.beans.TeacherTeacherNames;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherTeacherListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<TeacherTeacherNames> teacherTeacherNamesList = null;
    private ArrayList<TeacherTeacherNames> arraylist;

    public TeacherTeacherListAdapter(Context context, List<TeacherTeacherNames> teacherTeacherNamesList) {
        this.context = context;
        this.teacherTeacherNamesList = teacherTeacherNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherTeacherNames>();
        this.arraylist.addAll(teacherTeacherNamesList);
    }

    @Override
    public int getCount() {
        return teacherTeacherNamesList.size();
    }

    @Override
    public TeacherTeacherNames getItem(int position) {
        return teacherTeacherNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.teacher_teachers_list_item, null);
            holder.tvItemHeading = (TextView) view.findViewById(R.id.tvItemHeading);
            holder.tvItemText = (TextView) view.findViewById(R.id.tvItemText);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvItemHeading.setText(teacherTeacherNamesList.get(position).getName());
        holder.tvItemText.setText(teacherTeacherNamesList.get(position).getEmail());
        holder.tvItemHeading.setTypeface(appFontRegular);
        holder.tvItemText.setTypeface(appFontLight);

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherTeacherNamesList.clear();
        if (charText.length() == 0) {
            teacherTeacherNamesList.addAll(arraylist);
        } else {
            for (TeacherTeacherNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherTeacherNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvItemHeading;
        TextView tvItemText;
    }
}