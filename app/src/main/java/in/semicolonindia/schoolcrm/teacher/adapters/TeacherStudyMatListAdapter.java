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
import in.semicolonindia.schoolcrm.teacher.beans.TeacherStudyMatNames;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.DownloadTaskStudyMaterial;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherStudyMatListAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    private List<TeacherStudyMatNames> teacherStudyMatNamesList = null;
    private ArrayList<TeacherStudyMatNames> arraylist;

    public TeacherStudyMatListAdapter(Activity activity, List<TeacherStudyMatNames> teacherStudyMatNamesList) {
        this.activity = activity;
        this.teacherStudyMatNamesList = teacherStudyMatNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<TeacherStudyMatNames>();
        this.arraylist.addAll(teacherStudyMatNamesList);
    }

    @Override
    public int getCount() {
        return teacherStudyMatNamesList.size();
    }

    @Override
    public TeacherStudyMatNames getItem(int position) {
        return teacherStudyMatNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.teacher_study_mat_list_item, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.btnDownload = (Button) convertView.findViewById(R.id.btnDownload);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            holder.tvClass = (TextView) convertView.findViewById(R.id.tvClass);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvFile = (TextView) convertView.findViewById(R.id.tvFile);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvTitle.setText(teacherStudyMatNamesList.get(position).getsTitle());
        holder.tvSubject.setText(teacherStudyMatNamesList.get(position).getsSubject());
        holder.tvClass.setText(teacherStudyMatNamesList.get(position).getsClass());
        holder.tvDate.setText(teacherStudyMatNamesList.get(position).getsDate());
        holder.tvFile.setText(teacherStudyMatNamesList.get(position).getsFile());
        holder.tvDesp.setText(teacherStudyMatNamesList.get(position).getsDesp());
        holder.tvTitle.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tvSubject.setTypeface(appFontLight);
        holder.tvClass.setTypeface(appFontLight);
        holder.tvDate.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (new ConnectionDetector(activity).isConnectingToInternet()){
                    new DownloadTaskStudyMaterial(activity, teacherStudyMatNamesList.get(position).getsFile())
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else

                {
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
        teacherStudyMatNamesList.clear();
        if (charText.length() == 0) {
            teacherStudyMatNamesList.addAll(arraylist);
        } else {
            for (TeacherStudyMatNames wp : arraylist) {
                if (wp.getsTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    teacherStudyMatNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvTitle;
        Button btnDownload;
        TextView tvSubject;
        TextView tvClass;
        TextView tvDate;
        TextView tvFile;
        TextView tvDesp;
    }
}
