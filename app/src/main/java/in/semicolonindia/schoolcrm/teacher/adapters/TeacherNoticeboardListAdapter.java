package in.semicolonindia.schoolcrm.teacher.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.DialogNoticeBoard;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherNoticeNames;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherNoticeboardListAdapter extends BaseAdapter {

    Activity aActivity;
    LayoutInflater inflater;
    private List<TeacherNoticeNames> teacherNoticeNamesList = null;
    private ArrayList<TeacherNoticeNames> arraylist;

    public TeacherNoticeboardListAdapter(Activity aActivity, List<TeacherNoticeNames> teacherNoticeNamesList) {
        this.aActivity = aActivity;
        this.teacherNoticeNamesList = teacherNoticeNamesList;
        inflater = LayoutInflater.from(aActivity);
        this.arraylist = new ArrayList<TeacherNoticeNames>();
        this.arraylist.addAll(teacherNoticeNamesList);
    }

    @Override
    public int getCount() {
        return teacherNoticeNamesList.size();
    }

    @Override
    public TeacherNoticeNames getItem(int position) {
        return teacherNoticeNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.teacher_noticeboard_list_items, null);
            holder.tvNoticeName = (TextView) convertView.findViewById(R.id.tvNoticeName);
            holder.btnViewNotice = (Button) convertView.findViewById(R.id.btnViewNotice);
            holder.tvNoticeDate = (TextView) convertView.findViewById(R.id.tvNoticeDate);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(aActivity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(aActivity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvNoticeName.setText(teacherNoticeNamesList.get(position).getNoticeName());
        holder.tvNoticeDate.setText(aActivity.getString(R.string.date) + teacherNoticeNamesList.get(position).getDate());
        holder.tvDesp.setText(teacherNoticeNamesList.get(position).getDesp());
        holder.tvNoticeName.setTypeface(appFontRegular);
        holder.btnViewNotice.setTypeface(appFontLight);
        holder.tvNoticeDate.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);

        holder.btnViewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DialogNoticeBoard mNoticeDialog = new DialogNoticeBoard(aActivity, R.style.DialogSlideAnim,
                        holder.tvNoticeName.getText().toString(),
                        holder.tvNoticeDate.getText().toString(),
                        holder.tvDesp.getText().toString());

                //teacherNoticeNamesList.get(position));
                // mNoticeDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                mNoticeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mNoticeDialog.getWindow().setGravity(Gravity.BOTTOM);
                mNoticeDialog.show();
                        /*DialogNoticeBoard mNoticeDialog = new DialogNoticeBoard(context, R.style.DialogSlideAnim,
                                holder.tvNoticeName.getText().toString(),
                                holder.tvNoticeDate.getText().toString(),holder.tvDesp.getText().toString());
                        mNoticeDialog.getWindow().setGravity(Gravity.BOTTOM);*/
                // mNoticeDialog.show();
            }
        });
        //Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherNoticeNamesList.clear();
        if (charText.length() == 0) {
            teacherNoticeNamesList.addAll(arraylist);
        } else {
            for (TeacherNoticeNames wp : arraylist) {
                if (wp.getNoticeName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherNoticeNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvNoticeName;
        Button btnViewNotice;
        TextView tvNoticeDate;
        TextView tvDesp;
    }
}