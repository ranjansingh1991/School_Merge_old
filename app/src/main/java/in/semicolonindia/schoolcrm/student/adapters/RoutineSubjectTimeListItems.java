package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.semicolonindia.schoolcrm.R;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class RoutineSubjectTimeListItems extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> sData;
    private int nHeight;

    public RoutineSubjectTimeListItems(Context context, int nHeight, ArrayList<String> sData) {
        this.context = context;
        this.nHeight = nHeight;
        this.sData = sData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.student_routine_subject_list_item, null);
        final TextView tvClassTimes = (TextView) convertView.findViewById(R.id.tvClassTimes);
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        if (position == 0) {
            tvClassTimes.setTypeface(appFontRegular);
            tvClassTimes.setTextSize(13.0f);
        }
        else{
            tvClassTimes.setTypeface(appFontLight);
            tvClassTimes.setTextSize(12.0f);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (nHeight) -
                        context.getResources().getInteger(R.integer.class_routing_list_height_adjuster_value) );
        tvClassTimes.setLayoutParams(layoutParams);
        try {
            tvClassTimes.setText(sData.get(position));
        } catch (Exception e) {
            tvClassTimes.setText("-");
        }
        return convertView;
    }
}