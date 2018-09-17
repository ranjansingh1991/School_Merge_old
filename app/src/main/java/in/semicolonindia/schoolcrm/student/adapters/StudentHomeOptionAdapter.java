package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by RANJAN SINGH on 11/24/2017.
 */
@SuppressWarnings("all")

public class StudentHomeOptionAdapter extends BaseAdapter {

    private Context context;
    private int[] nImages;
    private String[] sTitles;
    private LayoutInflater layoutInflater;

    //private List<FirstName> firstNamesList = null;
    //private ArrayList<FirstName> arraylist;

    public StudentHomeOptionAdapter(Context context, String[] sTitles) {
        this.context = context;
        this.sTitles = sTitles;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.nImages = new int[]{
                R.drawable.ic_photo_black_24dp,
                R.drawable.ic_news_vector,
                R.drawable.ic_feedback_24px,
                R.drawable.ic_leaderboard,
                R.drawable.ic_word_from_bench,
                R.drawable.ic_scholarship,
                R.drawable.ic_extra_curriculam,
                R.drawable.support,


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
        convertView = layoutInflater.inflate(R.layout.student_second_list_item, null);
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
