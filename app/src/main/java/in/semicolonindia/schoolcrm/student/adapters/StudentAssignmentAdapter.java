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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.AssignmentName;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.DownloadTaskAssignment;


/**
 * Created by Rupesh on 10/18/2017.
 */
@SuppressWarnings("ALL")
public class StudentAssignmentAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    private List<AssignmentName> assignmentNamesList = null;
    private ArrayList<AssignmentName> arraylist;
    private ListView lvAssignmenttName;

    public StudentAssignmentAdapter(Activity activity, List<AssignmentName> assignmentNamesList) {
        this.activity = activity;
        this.assignmentNamesList = assignmentNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<AssignmentName>();
        this.arraylist.addAll(assignmentNamesList);
        //this.lvAssignmenttName = lvAssignmenttName;
    }

    @Override
    public int getCount() {
        return assignmentNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignmentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final StudentAssignmentAdapter.ViewHolder holder;
        if (view == null) {
            holder = new StudentAssignmentAdapter.ViewHolder();
            view = inflater.inflate(R.layout.student_assignment_details_list, null);
            holder.tvAssignment_Title = (TextView) view.findViewById(R.id.tvAssignmentTitle);
            holder.btnDownload = (TextView) view.findViewById(R.id.btnDownload);
            holder.tv_Question = (TextView) view.findViewById(R.id.tv_Question);
            holder.tv_Upload = (TextView) view.findViewById(R.id.tv_Upload);
            holder.tv_Submit = (TextView) view.findViewById(R.id.tv_Submit);
            holder.tv_Marks = (TextView) view.findViewById(R.id.  tv_Marks);
            holder.tv_Report_to = (TextView) view.findViewById(R.id.tv_Report_to);
            holder.tvFile = (TextView) view.findViewById(R.id.tvFile);
            view.setTag(holder);
        } else {
            holder = (StudentAssignmentAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvAssignment_Title.setText(assignmentNamesList.get(position).getaTitle());
        holder.tv_Question.setText(assignmentNamesList.get(position).getaQuestion());
        holder.tv_Upload.setText(assignmentNamesList.get(position).getaUpload_Date());
        holder.tv_Submit.setText(assignmentNamesList.get(position).getA_Submit_Date());
        holder.tv_Marks.setText(assignmentNamesList.get(position).getaMarks());
        holder.tv_Report_to.setText(assignmentNamesList.get(position).getReport());
       // holder.tvFile.setText(assignmentNamesList.get(position).getFileName().split("\\.")[0]);
        holder.tvFile.setText(assignmentNamesList.get(position).getFileName());



     holder.tvAssignment_Title.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tv_Question.setTypeface(appFontLight);
        holder.tv_Upload.setTypeface(appFontLight);
        holder.tv_Submit.setTypeface(appFontLight);
        holder.tv_Marks.setTypeface(appFontLight);
        holder.tv_Report_to.setTypeface(appFontLight);
        holder.tvFile.setTypeface(appFontLight);

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Check for Devices Emulator Error ...
                if (new ConnectionDetector(activity).isConnectingToInternet()){
                    new DownloadTaskAssignment(activity, assignmentNamesList.get(position).getFileName())
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    //Toast.makeText(activity, "Download started", Toast.LENGTH_SHORT).show();

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

    public static class ViewHolder {
        TextView tvAssignment_Title;
        TextView btnDownload;
        TextView tv_Question;
        TextView tv_Upload;
        TextView tv_Submit;
        TextView tv_Marks;
        TextView tv_Report_to;
        TextView tvFile;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        assignmentNamesList.clear();
        if (charText.length() == 0) {
            assignmentNamesList.addAll(arraylist);
        } else {
            for (AssignmentName wp : arraylist) {
                if (wp.getaTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    assignmentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
