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
import in.semicolonindia.schoolcrm.parent.beans.ParentSubjectLast;

/**
 * Created by Rupesh on 21-02-2018.
 */

@SuppressWarnings("ALL")
public class ParentSubjectListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<ParentSubjectLast> parentSubjectLastList = null;
    private ArrayList<ParentSubjectLast> arraylist;
    //private ListView lvStudentName;

    public ParentSubjectListAdapter(Context context, List<ParentSubjectLast> parentSubjectLastList, ListView lvStudentName) {
        this.context = context;
        this.parentSubjectLastList = parentSubjectLastList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ParentSubjectLast>();
        this.arraylist.addAll(parentSubjectLastList);
        //this.lvStudentName = lvStudentName;
    }
    @Override
    public int getCount() {
        return parentSubjectLastList.size();
    }

    @Override
    public Object getItem(int position) {
        return parentSubjectLastList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ParentSubjectListAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ParentSubjectListAdapter.ViewHolder();
            view = inflater.inflate(R.layout.parent_subject_last_list_item, null);
            holder.tv_Subject_Name = (TextView) view.findViewById(R.id.tv_Subject_Name);
            holder.tvYear = (TextView) view.findViewById(R.id.tv_Year);
            view.setTag(holder);
        } else {
            holder = (ParentSubjectListAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tv_Subject_Name.setText(parentSubjectLastList.get(position).getName());
        holder.tvYear.setText(parentSubjectLastList.get(position).getYear());
        holder.tv_Subject_Name.setTypeface(appFontRegular);
        holder.tvYear.setTypeface(appFontLight);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                switch (position) {
                    case 0:
                        //context.startActivity(new Intent(context, MessageNextActivity.class));
                        break;
                    case 1:
                       // context.startActivity(new Intent(context, MessageNextActivity.class));
                        break;
                    case 2:
                       // context.startActivity(new Intent(context, MessageNextActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });*/

        return view;
    }
    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        parentSubjectLastList.clear();
        if (charText.length() == 0)

        {
            parentSubjectLastList.addAll(arraylist);
        }
        else
        {
            for (ParentSubjectLast wp : arraylist)
            {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    parentSubjectLastList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder
    {
        TextView tv_Subject_Name;
        TextView tvYear;
    }
}

