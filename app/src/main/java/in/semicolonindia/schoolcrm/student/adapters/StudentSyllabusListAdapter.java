package in.semicolonindia.schoolcrm.student.adapters;

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
import in.semicolonindia.schoolcrm.student.beans.SyllabusNames;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.DownloadTaskSyllabus;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class StudentSyllabusListAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    private List<SyllabusNames> syllabusNamesList = null;
    private ArrayList<SyllabusNames> arraylist;

    public StudentSyllabusListAdapter(Activity activity, List<SyllabusNames> syllabusNamesList) {
        this.activity = activity;
        this.syllabusNamesList = syllabusNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<SyllabusNames>();
        this.arraylist.addAll(syllabusNamesList);
    }

    @Override
    public int getCount() {
        return syllabusNamesList.size();
    }

    @Override
    public SyllabusNames getItem(int position) {
        return syllabusNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.student_syllabus_list_item, null);
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            holder.btnDownload = (Button) view.findViewById(R.id.btnDownload);
            holder.tvSubject = (TextView) view.findViewById(R.id.tvSubject);
            //holder.tvUploader = (TextView) view.findViewById(R.id.tvUploader);
            holder.tvFile = (TextView) view.findViewById(R.id.tvFile);
            holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
            holder.tvDesp = (TextView) view.findViewById(R.id.tvDesp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvTitle.setText(syllabusNamesList.get(position).getTitle());
        holder.tvSubject.setText(syllabusNamesList.get(position).getSubject());
        //holder.tvUploader.setText(syllabusNamesList.get(position).getUploader());
        holder.tvDate.setText(syllabusNamesList.get(position).getDate());
        holder.tvFile.setText(syllabusNamesList.get(position).getFile().split("\\.")[0]);
        holder.tvDesp.setText(syllabusNamesList.get(position).getDesp());

        holder.tvTitle.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tvSubject.setTypeface(appFontLight);
        //holder.tvUploader.setTypeface(appFontLight);
        holder.tvDate.setTypeface(appFontLight);
        holder.tvFile.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (new ConnectionDetector(activity).isConnectingToInternet()){
                    new DownloadTaskSyllabus(activity, syllabusNamesList.get(position).getFile())
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

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        syllabusNamesList.clear();
        if (charText.length() == 0) {
            syllabusNamesList.addAll(arraylist);
        } else {
            for (SyllabusNames wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    syllabusNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvTitle;
        Button btnDownload;
        TextView tvSubject;
        //TextView tvUploader;
        TextView tvDate;
        TextView tvFile;
        TextView tvDesp;
    }
}