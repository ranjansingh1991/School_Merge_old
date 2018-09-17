package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.civ.CircleImageView;
import in.semicolonindia.schoolcrm.student.beans.StudentTeacherNames;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherMessageListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<StudentTeacherNames> studentTeacherNamesList = null;
    private ArrayList<StudentTeacherNames> arraylist;

    public TeacherMessageListAdapter(Context context, List<StudentTeacherNames> studentTeacherNamesList) {
        this.context = context;
        this.studentTeacherNamesList = studentTeacherNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<StudentTeacherNames>();
        this.arraylist.addAll(studentTeacherNamesList);
    }

    @Override
    public int getCount() {
        return studentTeacherNamesList.size();
    }

    @Override
    public StudentTeacherNames getItem(int position) {
        return studentTeacherNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.teacher_message_list_item, null);
            holder.llMsgListParent = (LinearLayout) view.findViewById(R.id.llMsgListParent);
            holder.tvItemHeading = (TextView) view.findViewById(R.id.tvItemHeading);
            holder.tvItemText = (TextView) view.findViewById(R.id.tvItemText);
            holder.tvMsgCount = (TextView) view.findViewById(R.id.tvMsgCount);
            holder.imgComponent = (CircleImageView) view.findViewById(R.id.imgComponent);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        if (studentTeacherNamesList.get(position).getID().equalsIgnoreCase("###")) {
            holder.llMsgListParent.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.tvItemHeading.setText(studentTeacherNamesList.get(position).getName());
            holder.tvItemHeading.setTypeface(appFontRegular);
            holder.tvItemHeading.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.tvItemText.setVisibility(View.GONE);
            holder.tvMsgCount.setVisibility(View.GONE);
            holder.imgComponent.setVisibility(View.GONE);
        } else {
            holder.llMsgListParent.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            holder.tvItemHeading.setTextColor(context.getResources().getColor(android.R.color.background_dark));
            holder.tvItemHeading.setText(studentTeacherNamesList.get(position).getName());
            holder.tvItemText.setText(studentTeacherNamesList.get(position).getEmail());
            holder.tvItemHeading.setTypeface(appFontRegular);
            holder.tvItemText.setTypeface(appFontLight);
            holder.tvItemText.setVisibility(View.VISIBLE);
            holder.tvMsgCount.setVisibility(View.VISIBLE);
            holder.imgComponent.setVisibility(View.VISIBLE);
            Picasso.with(context).load(studentTeacherNamesList.get(position).getImgURL()).into(holder.imgComponent);
            if (studentTeacherNamesList.get(position).getMsgCount() > 0) {
                holder.tvMsgCount.setVisibility(View.VISIBLE);
                holder.tvMsgCount.setText(String.valueOf(studentTeacherNamesList.get(position).getMsgCount()));
            } else {
                holder.tvMsgCount.setVisibility(View.GONE);
            }
        }
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        studentTeacherNamesList.clear();
        if (charText.length() == 0) {
            studentTeacherNamesList.addAll(arraylist);
        } else {
            for (StudentTeacherNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    studentTeacherNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        LinearLayout llMsgListParent;
        TextView tvItemHeading;
        TextView tvItemText;
        TextView tvMsgCount;
        CircleImageView imgComponent;
    }
}