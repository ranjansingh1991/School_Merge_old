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
import in.semicolonindia.schoolcrm.teacher.beans.LeaderBoardNames;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherLeaderBoardAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<LeaderBoardNames> leaderBoardNamesList = null;
    private ArrayList<LeaderBoardNames> arraylist;

    public TeacherLeaderBoardAdapter(Context context, List<LeaderBoardNames> leaderBoardNamesList) {
        this.context = context;
        this.leaderBoardNamesList = leaderBoardNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<LeaderBoardNames>();
        this.arraylist.addAll(leaderBoardNamesList);
    }

    @Override
    public int getCount() {
        return leaderBoardNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return leaderBoardNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.teacher_leaderboard_list_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_Kid);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_Name);
            holder.tvClass = (TextView) convertView.findViewById(R.id.tvClass);
            holder.tvSection = (TextView) convertView.findViewById(R.id.tvSection);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_Date);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvAchivment = (TextView) convertView.findViewById(R.id.tvAchivment);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDesp);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.imageView.setImageResource(Integer.parseInt(String.valueOf(leaderBoardNamesList.get(position).getImage_url())));
        holder.tvName.setText(leaderBoardNamesList.get(position).getsName());
        holder.tvClass.setText(leaderBoardNamesList.get(position).getsClass());
        holder.tvSection.setText(leaderBoardNamesList.get(position).getsSection());
        holder.tvDate.setText(leaderBoardNamesList.get(position).getsDate());
        holder.tvTime.setText(leaderBoardNamesList.get(position).getsTime());
        holder.tvAchivment.setText(leaderBoardNamesList.get(position).getsAchivments());
        holder.tvDescription.setText(leaderBoardNamesList.get(position).getsDesp());

        holder.tvName.setTypeface(appFontLight);
        holder.tvClass.setTypeface(appFontLight);

        holder.tvTime.setTypeface(appFontLight);
        holder.tvAchivment.setTypeface(appFontLight);
        holder.tvSection.setTypeface(appFontLight);


        //noticeNamesList.get(position));
        // mNoticeDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        leaderBoardNamesList.clear();
        if (charText.length() == 0) {
            leaderBoardNamesList.addAll(arraylist);
        } else {
            for (LeaderBoardNames wp : arraylist) {
                if (wp.getsName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    leaderBoardNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvDate;
        TextView tvClass;
        TextView tvSection;
        TextView tvTime;
        TextView tvAchivment;
        TextView tvDescription;

    }


}
