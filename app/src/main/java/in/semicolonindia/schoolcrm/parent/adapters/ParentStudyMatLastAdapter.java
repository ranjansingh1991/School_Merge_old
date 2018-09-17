package in.semicolonindia.schoolcrm.parent.adapters;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.parent.beans.ParentStudyMaterialLast;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;
import in.semicolonindia.schoolcrm.util.DownloadTaskStudyMaterial;

/**
 * Created by Rupesh on 21-02-2018.
 */

@SuppressWarnings("ALL")
public class ParentStudyMatLastAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    private List<ParentStudyMaterialLast> parentStudyMaterialLastList = null;
    private ArrayList<ParentStudyMaterialLast> arraylist;
    ListView lvStudyMat;

    public ParentStudyMatLastAdapter(Activity activity, List<ParentStudyMaterialLast> parentStudyMaterialLastList, ListView lvStudyMat) {
        this.activity = activity;
        this.parentStudyMaterialLastList = parentStudyMaterialLastList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<ParentStudyMaterialLast>();
        this.arraylist.addAll(parentStudyMaterialLastList);
        this.lvStudyMat = lvStudyMat;
    }

    @Override
    public int getCount() {
        return parentStudyMaterialLastList.size();
    }

    @Override
    public ParentStudyMaterialLast getItem(int position) {
        return parentStudyMaterialLastList.get(position);
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
            convertView = inflater.inflate(R.layout.parent_study_material_list_item, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.btnDownload = (Button) convertView.findViewById(R.id.btnDownload);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            holder.tvClass = (TextView) convertView.findViewById(R.id.tvClass);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvFile = (TextView) convertView.findViewById(R.id.tv_File_Name);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvTitle.setText(parentStudyMaterialLastList.get(position).getsTitle());
        holder.tvSubject.setText(parentStudyMaterialLastList.get(position).getsSubject());
        holder.tvClass.setText(parentStudyMaterialLastList.get(position).getsClass());
        holder.tvDate.setText(parentStudyMaterialLastList.get(position).getsDate());
        holder.tvFile.setText(parentStudyMaterialLastList.get(position).getsFile());
        holder.tvDesp.setText(parentStudyMaterialLastList.get(position).getsDesp());


        holder.tvTitle.setTypeface(appFontRegular);
        holder.btnDownload.setTypeface(appFontLight);
        holder.tvSubject.setTypeface(appFontLight);
        holder.tvClass.setTypeface(appFontLight);
        holder.tvDate.setTypeface(appFontLight);
        holder.tvFile.setTypeface(appFontLight);
        holder.tvDesp.setTypeface(appFontLight);

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 1. Here we create a method to check Internet avalale or not.
                if (new ConnectionDetector(activity).isConnectingToInternet()) {

                    new DownloadTaskStudyMaterial(activity, parentStudyMaterialLastList.get(position).getsFile())
                            .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);;

                    // 2. Here we create ConnectionDetector class to check internet connection .
                    // if connection is not available then dailog box will open
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
        parentStudyMaterialLastList.clear();
        if (charText.length() == 0) {
            parentStudyMaterialLastList.addAll(arraylist);
        } else {
            for (ParentStudyMaterialLast wp : arraylist) {
                if (wp.getsTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    parentStudyMaterialLastList.add(wp);
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