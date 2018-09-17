package in.semicolonindia.schoolcrm.parent.adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import in.semicolonindia.schoolcrm.parent.beans.ParentStudentNames;

/**
 * Created by Rupesh on 13-02-2018.
 */

@SuppressWarnings("ALL")
public class AssignmentFirstAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<ParentStudentNames> parentStudentNamesList = null;
    private ArrayList<ParentStudentNames> arraylist;
    private ListView lvStudentName;

    public AssignmentFirstAdapter(Context context, List<ParentStudentNames> parentStudentNamesList, ListView lvStudentName) {
        this.context = context;
        this.parentStudentNamesList = parentStudentNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ParentStudentNames>();
        this.arraylist.addAll(parentStudentNamesList);
        this.lvStudentName = lvStudentName;
    }

    @Override
    public int getCount() {
        return parentStudentNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return parentStudentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final AssignmentFirstAdapter.ViewHolder holder;
        if (view == null) {
            holder = new AssignmentFirstAdapter.ViewHolder();
            view = inflater.inflate(R.layout.student_list_item, null);
            holder.tvStudentNameHeader = (TextView) view.findViewById(R.id.tvStudentNameHeader);
            holder.tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            view.setTag(holder);
        } else {
            holder = (AssignmentFirstAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvStudentNameHeader.setText(parentStudentNamesList.get(position).getName());
        holder.tvEmail.setText(parentStudentNamesList.get(position).geteMail());
        holder.tvStudentNameHeader.setTypeface(appFontRegular);
        holder.tvEmail.setTypeface(appFontLight);

        return view;
    }

    public class ViewHolder {
        TextView tvStudentNameHeader;
        TextView tvEmail;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        parentStudentNamesList.clear();
        if (charText.length() == 0) {
            parentStudentNamesList.addAll(arraylist);
        } else {
            for (ParentStudentNames wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    parentStudentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
