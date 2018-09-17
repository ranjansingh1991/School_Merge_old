package in.semicolonindia.schoolcrm.student.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.PaymentNames;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class PaymentsListAdapter  extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<PaymentNames> paymentNamesList = null;
    private ArrayList<PaymentNames> arraylist;

    public PaymentsListAdapter(Context context, List<PaymentNames> paymentNamesList) {
        this.context = context;
        this.paymentNamesList = paymentNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<PaymentNames>();
        this.arraylist.addAll(paymentNamesList);
    }

    @Override
    public int getCount() {
        return paymentNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.student_payment_list_items, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvDesp = (TextView) convertView.findViewById(R.id.tvDesp);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvTitle.setText(paymentNamesList.get(position).getTitle());
        if ((paymentNamesList.get(position).getStatus()).equalsIgnoreCase("paid"))
            holder.tvStatus.setBackground(context.getDrawable(R.drawable.button_background_status_success));
        if ((paymentNamesList.get(position).getStatus()).equalsIgnoreCase("pending"))
            holder.tvStatus.setBackground(context.getDrawable(R.drawable.button_background_primary));
        if ((paymentNamesList.get(position).getStatus()).equalsIgnoreCase("due"))
            holder.tvStatus.setBackground(context.getDrawable(R.drawable.button_background_status_failure));
        holder.tvStatus.setText(paymentNamesList.get(position).getStatus());
        holder.tvAmount.setText(context.getString(R.string.rupee_symbol) + paymentNamesList.get(position).getAmount() + "/-");
        holder.tvDate.setText(paymentNamesList.get(position).getDate());
        holder.tvDesp.setText(paymentNamesList.get(position).getDesp());
        holder.tvTitle.setTypeface(appFontRegular);
        holder.tvStatus.setTypeface(appFontLight);
        holder.tvAmount.setTypeface(appFontLight);
        holder.tvDate.setTypeface(appFontLight);

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        paymentNamesList.clear();
        if (charText.length() == 0) {
            paymentNamesList.addAll(arraylist);
        } else {
            for (PaymentNames wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    paymentNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvTitle;
        TextView tvStatus;
        TextView tvAmount;
        TextView tvDate;
        TextView tvDesp;
    }
}