package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherDayNamesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] sDayNames;
    private int nHeight;

    public TeacherDayNamesListAdapter(Context context, int nHeight) {
        this.context = context;
        this.nHeight = nHeight;
        sDayNames = new String[]{"", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
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
        convertView = layoutInflater.inflate(R.layout.teacher_day_names_list_item, null);
        final TextView tvDayNames = (TextView) convertView.findViewById(R.id.tvDayNames);
        final LinearLayout llHeader = (LinearLayout) convertView.findViewById(R.id.llHeader);
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        tvDayNames.setTypeface(appFontRegular);
        if (position == 0) {
            tvDayNames.setVisibility(View.GONE);
            Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
            final TextView tvSubjectHeadingMarker = (TextView) convertView.findViewById(R.id.tvSubjectHeadingMarker);
            final TextView tvDaysHeadingMarker = (TextView) convertView.findViewById(R.id.tvDaysHeadingMarker);
            tvSubjectHeadingMarker.setTypeface(appFontLight);
            tvDaysHeadingMarker.setTypeface(appFontLight);
        } else {
            llHeader.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (nHeight) -
                context.getResources().getInteger(R.integer.class_routing_list_height_adjuster_value));
        llHeader.setLayoutParams(layoutParams);
        tvDayNames.setLayoutParams(layoutParams);
        tvDayNames.setText(sDayNames[position]);
        return convertView;
    }
}
