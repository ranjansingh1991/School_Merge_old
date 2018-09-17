package in.semicolonindia.schoolcrm.student.adapters;

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
import in.semicolonindia.schoolcrm.student.beans.BenchNames;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("all")

public class BenchListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<BenchNames> benchNamesList = null;
    private ArrayList<BenchNames> arraylist;

    public BenchListAdapter(Context context, List<BenchNames> benchNamesList) {
        this.context = context;
        this.benchNamesList = benchNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<BenchNames>();
        this.arraylist.addAll(benchNamesList);
    }

    @Override
    public int getCount() {
        return benchNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return benchNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.student_bench_list_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_Kid);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_Name);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_Title);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_Date);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_Time);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDesp);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.imageView.setImageResource(Integer.parseInt(String.valueOf(benchNamesList.get(position).getImage_url())));
        holder.tvName.setText(benchNamesList.get(position).getsName());
        holder.tvTitle.setText(benchNamesList.get(position).getsTitle());
        holder.tvDate.setText(benchNamesList.get(position).getsDate());
        holder.tvTime.setText(benchNamesList.get(position).getsTime());
        holder.tvDescription.setText(benchNamesList.get(position).getsDesp());
        holder.tvName.setTypeface(appFontLight);
        holder.tvTime.setTypeface(appFontLight);


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        benchNamesList.clear();
        if (charText.length() == 0) {
            benchNamesList.addAll(arraylist);
        } else {
            for (BenchNames wp : arraylist) {
                if (wp.getsName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    benchNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView tvName;
        TextView tvTitle;
        TextView tvDate;
        TextView tvTime;
        TextView tvDescription;

    }


}
