package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by Rupesh on 14/08/2017.
 */
@SuppressWarnings("ALL")
public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private Context context;

    public NotificationListAdapter(Context context) {
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_notification_list_item, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvItemHeading.setTypeface(appFontRegular);
        holder.tvItemText.setTypeface(appFontLight);
    }

    @Override
    public int getItemCount() {
        return 21;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemHeading;
        TextView tvItemText;

        ViewHolder(View itemView) {
            super(itemView);
            tvItemHeading = (TextView) itemView.findViewById(R.id.tvItemText);
            tvItemText = (TextView) itemView.findViewById(R.id.tvItemText);
        }
    }
}