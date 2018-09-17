package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.SubjectNames;

/**
 * Created by Rupesh on 02-02-2018.
 */

public class SubjectListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<SubjectNames> subjectNamesList = null;
    private ArrayList<SubjectNames> arraylist;

    public SubjectListAdapter(Context context, List<SubjectNames> subjectNamesList) {
        this.context = context;
        this.subjectNamesList = subjectNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<SubjectNames>();
        this.arraylist.addAll(subjectNamesList);
    }

    @Override
    public int getCount() {
        return subjectNamesList.size();
    }

    @Override
    public SubjectNames getItem(int position) {
        return subjectNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.student_subject_list_item, null);
            holder.tvItemHeading = (TextView) view.findViewById(R.id.tvItemHeading);
            holder.tvItemText = (TextView) view.findViewById(R.id.tvItemText);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvItemHeading.setText(subjectNamesList.get(position).getName());
        holder.tvItemText.setText(subjectNamesList.get(position).getStream());
        holder.tvItemHeading.setTypeface(appFontRegular);
        holder.tvItemText.setTypeface(appFontLight);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        subjectNamesList.clear();
        if (charText.length() == 0) {
            subjectNamesList.addAll(arraylist);
        } else {
            for (SubjectNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    subjectNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvItemHeading;
        TextView tvItemText;
    }
}
