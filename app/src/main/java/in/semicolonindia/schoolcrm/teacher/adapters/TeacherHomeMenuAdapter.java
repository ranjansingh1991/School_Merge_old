package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherHomeMenuAdapter extends BaseAdapter {

    private Context context;
    private int[] nImages;
    private String[] sTitles;
    private LayoutInflater layoutInflater;

    public TeacherHomeMenuAdapter(Context context, String[] sTitles) {
        this.context = context;
        this.sTitles = sTitles;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.nImages = new int[]{
                R.drawable.ic_assignment_dark_holo,
                R.drawable.ic_dashboard_dark_holo,
                R.drawable.ic_subject_dark_holo,
                R.drawable.ic_routine_dark_holo,
                R.drawable.ic_study_mat_dark_holo,
                R.drawable.ic_sllyabus_dark_holo,
                R.drawable.ic_exam_marks_dark_holo,
                R.drawable.ic_library_dark_holo,
                R.drawable.ic_pin_dark_holo,
                R.drawable.ic_message_dark_holo,
                R.drawable.ic_date_range_24px,
                R.drawable.ic_account_dark_holo
        };
    }

    @Override
    public int getCount() {
        return sTitles.length;
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
        convertView = layoutInflater.inflate(R.layout.teacher_home_menu_adapter_list, null);
        final ImageView imgHomeListItem = (ImageView) convertView.findViewById(R.id.imgHomeListItem);
        final TextView tvHomeListItem = (TextView) convertView.findViewById(R.id.tvHomeListItem);
        Drawable drawable = context.getResources().getDrawable(nImages[position]);
        drawable.setBounds(0, 0, 60, 60);
        imgHomeListItem.setImageDrawable(drawable);
        tvHomeListItem.setText(sTitles[position]);
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        tvHomeListItem.setTypeface(appFontLight);
        return convertView;
    }

    public class ViewHolder {
        public ImageView imgHomeListItem;
        public TextView tvHomeListItem;
    }
}
