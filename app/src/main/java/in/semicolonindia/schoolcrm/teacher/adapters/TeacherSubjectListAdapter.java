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
import in.semicolonindia.schoolcrm.teacher.beans.TeacherSubjectNames;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherSubjectListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<TeacherSubjectNames> teacherSubjectNamesList = null;
    private ArrayList<TeacherSubjectNames> arraylist;

    public TeacherSubjectListAdapter(Context context, List<TeacherSubjectNames> teacherSubjectNamesList) {
        this.context = context;
        this.teacherSubjectNamesList = teacherSubjectNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherSubjectNames>();
        this.arraylist.addAll(teacherSubjectNamesList);
    }

    @Override
    public int getCount() {
        return teacherSubjectNamesList.size();
    }

    @Override
    public TeacherSubjectNames getItem(int position) {
        return teacherSubjectNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.teacher_subject_list_item, null);
            holder.tvItemHeading = (TextView) view.findViewById(R.id.tvItemHeading);
            holder.tvItemText = (TextView) view.findViewById(R.id.tvItemText);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvItemHeading.setText(teacherSubjectNamesList.get(position).getName());
        holder.tvItemText.setText(teacherSubjectNamesList.get(position).getStream());
        holder.tvItemHeading.setTypeface(appFontRegular);
        holder.tvItemText.setTypeface(appFontLight);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherSubjectNamesList.clear();
        if (charText.length() == 0) {
            teacherSubjectNamesList.addAll(arraylist);
        } else {
            for (TeacherSubjectNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherSubjectNamesList.add(wp);
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