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
import in.semicolonindia.schoolcrm.student.beans.AttendanceFirstModel;

/**
 * Created by Rupesh on 13-02-2018.
 */
@SuppressWarnings("ALL")
public class AttendanceFirstAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<AttendanceFirstModel> studentNamesList = null;
    private ArrayList<AttendanceFirstModel> arraylist;
    private ListView lvStudentName;


    public AttendanceFirstAdapter(Context context, List<AttendanceFirstModel> studentNamesList, ListView lvStudentName) {
        this.context = context;
        this.studentNamesList = studentNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<AttendanceFirstModel>();
        this.arraylist.addAll(studentNamesList);
        this.lvStudentName = lvStudentName;
    }

    @Override
    public int getCount() {
        return studentNamesList.size();

    }

    @Override
    public Object getItem(int position) {
        return studentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final AttendanceFirstAdapter.ViewHolder holder;
        if (view == null) {
            holder = new AttendanceFirstAdapter.ViewHolder();
            view = inflater.inflate(R.layout.student_list_item, null);
            holder.tvStudentNameHeader = (TextView) view.findViewById(R.id.tvStudentNameHeader);
            holder.tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            view.setTag(holder);
        } else {
            holder = (AttendanceFirstAdapter.ViewHolder) view.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvStudentNameHeader.setText(studentNamesList.get(position).getName());
        holder.tvEmail.setText(studentNamesList.get(position).geteMail());
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
        studentNamesList.clear();
        if (charText.length() == 0) {
            studentNamesList.addAll(arraylist);
        } else {
            for (AttendanceFirstModel wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    studentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
