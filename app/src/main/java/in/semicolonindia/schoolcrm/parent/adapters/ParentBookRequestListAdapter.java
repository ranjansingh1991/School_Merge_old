package in.semicolonindia.schoolcrm.parent.adapters;

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
import in.semicolonindia.schoolcrm.parent.beans.ParentBookRequestNames;

/**
 * Created by Rupesh on 14-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentBookRequestListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<ParentBookRequestNames> bookRequestNamesList = null;
    private ArrayList<ParentBookRequestNames> arraylist;

    public ParentBookRequestListAdapter(Context context, List<ParentBookRequestNames> bookRequestNamesList) {
        this.context = context;
        this.bookRequestNamesList = bookRequestNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ParentBookRequestNames>();
        this.arraylist.addAll(bookRequestNamesList);
    }

    @Override
    public int getCount() {
        return bookRequestNamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookRequestNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.student_book_request_list_item, null);
            holder.tvBookName = (TextView) convertView.findViewById(R.id.tvBookName);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
            holder.tvIssueStartTag = (TextView) convertView.findViewById(R.id.tvIssueStartTag);
            holder.tvIssueStartDate = (TextView) convertView.findViewById(R.id.tvIssueStartDate);
            holder.tvIssueEndTag = (TextView) convertView.findViewById(R.id.tvIssueEndTag);
            holder.tvIssueEndDate = (TextView) convertView.findViewById(R.id.tvIssueEndDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");
        holder.tvBookName.setText(bookRequestNamesList.get(position).getBookName());
        if ((bookRequestNamesList.get(position).getStatus()).equalsIgnoreCase("approved"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.tvStatus.setBackground(context.getDrawable(R.drawable.button_background_status_success));
            }
        if ((bookRequestNamesList.get(position).getStatus()).equalsIgnoreCase("pending"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.tvStatus.setBackground(context.getDrawable(R.drawable.button_background_primary));
            }
        if ((bookRequestNamesList.get(position).getStatus()).equalsIgnoreCase("rejected"))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.tvStatus.setBackground(context.getDrawable(R.drawable.button_background_status_failure));
            }
        holder.tvStatus.setText(bookRequestNamesList.get(position).getStatus());
        holder.tvIssueStartDate.setText(bookRequestNamesList.get(position).getIssueStartDate());
        holder.tvIssueEndDate.setText(bookRequestNamesList.get(position).getIssueEndDate());
        holder.tvBookName.setTypeface(appFontRegular);
        holder.tvStatus.setTypeface(appFontRegular);
        holder.tvIssueStartTag.setTypeface(appFontLight);
        holder.tvIssueStartDate.setTypeface(appFontLight);
        holder.tvIssueEndTag.setTypeface(appFontLight);
        holder.tvIssueEndDate.setTypeface(appFontLight);

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bookRequestNamesList.clear();
        if (charText.length() == 0) {
            bookRequestNamesList.addAll(arraylist);
        } else {
            for (ParentBookRequestNames wp : arraylist) {
                if (wp.getBookName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bookRequestNamesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvBookName;
        TextView tvStatus;
        TextView tvIssueStartTag;
        TextView tvIssueStartDate;
        TextView tvIssueEndTag;
        TextView tvIssueEndDate;
    }
}
