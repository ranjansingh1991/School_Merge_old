package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by Faraz on 06/25/2017.
 */
@SuppressWarnings("ALL")
public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private int[] nImages;
    private String[] sTitles;
    private LayoutInflater layoutInflater;

    public HomeListAdapter(Context context, String[] sTitles) {
        this.context = context;
        this.sTitles = sTitles;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.nImages = new int[]{

                R.drawable.ic_assignment_dark_holo,
                R.drawable.ic_teacher_dark_holo,
                R.drawable.ic_sllyabus_dark_holo,
                R.drawable.ic_subject_dark_holo,
                R.drawable.ic_routine_dark_holo,
                R.drawable.ic_study_mat_dark_holo,
                R.drawable.ic_exam_marks_dark_holo,
                R.drawable.ic_payment_dark_holo,
                R.drawable.ic_library_dark_holo,
                R.drawable.ic_transport_dark_holo,
                R.drawable.ic_pin_dark_holo,
                R.drawable.ic_message_dark_holo,
                R.drawable.ic_date_range_24px,
                R.drawable.ic_account_dark_holo,

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
        convertView = layoutInflater.inflate(R.layout.student_home_recyler_adapter, null);
        final ImageView imgHomeListItem = (ImageView) convertView.findViewById(R.id.imgHomeListItem);
        final TextView tvHomeListItem = (TextView) convertView.findViewById(R.id.tvHomeListItem);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgHomeListItem.setImageDrawable(context.getDrawable(nImages[position]));
        }
       //imgHomeListItem.setImageDrawable(context.getDrawable(nImages[position]));
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