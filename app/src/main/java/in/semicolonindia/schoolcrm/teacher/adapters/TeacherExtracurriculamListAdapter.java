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
import in.semicolonindia.schoolcrm.teacher.beans.TeacherExtraCurriculamNames;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherExtracurriculamListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<TeacherExtraCurriculamNames> teacherExtraCurriculamNamesList = null;
    private ArrayList<TeacherExtraCurriculamNames> arraylist;

    public TeacherExtracurriculamListAdapter(Context context, List<TeacherExtraCurriculamNames> teacherExtraCurriculamNamesList) {
        this.context = context;
        this.teacherExtraCurriculamNamesList = teacherExtraCurriculamNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherExtraCurriculamNames>();
        this.arraylist.addAll(teacherExtraCurriculamNamesList);
    }

    @Override
    public int getCount() {
        return teacherExtraCurriculamNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherExtraCurriculamNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.teacher_extracurriculam_list_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_Kid);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_Date);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDesp);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.imageView.setImageResource(Integer.parseInt(String.valueOf(teacherExtraCurriculamNamesList.get(position).getImage_url())));
        holder.tvDate.setText(teacherExtraCurriculamNamesList.get(position).getsDate());
        holder.tvTime.setText(teacherExtraCurriculamNamesList.get(position).getsTime());
        holder.tvDescription.setText(teacherExtraCurriculamNamesList.get(position).getsDesp());


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherExtraCurriculamNamesList.clear();
        if (charText.length() == 0) {
            teacherExtraCurriculamNamesList.addAll(arraylist);
        } else {
            for (TeacherExtraCurriculamNames wp : arraylist) {
                if (wp.getsDesp().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherExtraCurriculamNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView tvDate;
        TextView tvTime;
        TextView tvDescription;

    }

}


