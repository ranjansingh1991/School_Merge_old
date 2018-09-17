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
import in.semicolonindia.schoolcrm.student.beans.ExtraCurriculamNames;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class ExtracurriculamListAdapter  extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<ExtraCurriculamNames> extraCurriculamNamesList = null;
    private ArrayList<ExtraCurriculamNames> arraylist;

    public ExtracurriculamListAdapter(Context context, List<ExtraCurriculamNames> extraCurriculamNamesList) {
        this.context = context;
        this.extraCurriculamNamesList = extraCurriculamNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ExtraCurriculamNames>();
        this.arraylist.addAll(extraCurriculamNamesList);
    }

    @Override
    public int getCount() {
        return extraCurriculamNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return extraCurriculamNamesList.get(position);
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
            convertView = inflater.inflate(R.layout.student_extracurriculam_list_item, null);
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
        holder.imageView.setImageResource(Integer.parseInt(String.valueOf(extraCurriculamNamesList.get(position).getImage_url())));
        holder.tvDate.setText(extraCurriculamNamesList.get(position).getsDate());
        holder.tvTime.setText(extraCurriculamNamesList.get(position).getsTime());
        holder.tvDescription.setText(extraCurriculamNamesList.get(position).getsDesp());


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        extraCurriculamNamesList.clear();
        if (charText.length() == 0) {
            extraCurriculamNamesList.addAll(arraylist);
        } else {
            for (ExtraCurriculamNames wp : arraylist) {
                if (wp.getsDesp().toLowerCase(Locale.getDefault()).contains(charText)) {
                    extraCurriculamNamesList.add(wp);
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


