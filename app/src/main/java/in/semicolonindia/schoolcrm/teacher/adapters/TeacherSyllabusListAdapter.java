package in.semicolonindia.schoolcrm.teacher.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherSyllabusNames;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.DownloadTaskSyllabus;

/**
 * Created by Rupesh on 08-02-2018.
 */

@SuppressWarnings("ALL")
public class TeacherSyllabusListAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    private List<TeacherSyllabusNames> teacherSyllabusNamesList = null;
    private ArrayList<TeacherSyllabusNames> arraylist;

    public TeacherSyllabusListAdapter(Activity activity, List<TeacherSyllabusNames> teacherSyllabusNamesList) {
        this.activity = activity;
        this.teacherSyllabusNamesList = teacherSyllabusNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<TeacherSyllabusNames>();
        this.arraylist.addAll(teacherSyllabusNamesList);
    }

    @Override
    public int getCount() {
        return teacherSyllabusNamesList.size();
    }

    @Override
    public TeacherSyllabusNames getItem(int position) {
        return teacherSyllabusNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.teacher_syllabus_list_item, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.btnDownload = (Button) convertView.findViewById(R.id.btnDownload);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            holder.tvFile = (TextView) convertView.findViewById(R.id.tvFile);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvTitle.setText(teacherSyllabusNamesList.get(position).getTitle());
        holder.tvSubject.setText(teacherSyllabusNamesList.get(position).getSubject());
        holder.tvDate.setText(teacherSyllabusNamesList.get(position).getDate());
        holder.tvFile.setText(teacherSyllabusNamesList.get(position).getFile());
        holder.tvDesp.setText(teacherSyllabusNamesList.get(position).getDesp());
        holder.tvTitle.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tvSubject.setTypeface(appFontLight);
        holder.tvDate.setTypeface(appFontLight);
        holder.tvFile.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (new ConnectionDetector(activity).isConnectingToInternet()){
                    new DownloadTaskSyllabus(activity, teacherSyllabusNamesList.get(position).getFile())
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(activity,
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        teacherSyllabusNamesList.clear();
        if (charText.length() == 0) {
            teacherSyllabusNamesList.addAll(arraylist);
        } else {
            for (TeacherSyllabusNames wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherSyllabusNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvTitle;
        Button btnDownload;
        TextView tvSubject;
        TextView tvDate;
        TextView tvFile;
        TextView tvDesp;
    }
}