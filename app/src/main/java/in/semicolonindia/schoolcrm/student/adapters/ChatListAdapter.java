package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.beans.Chats;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class ChatListAdapter extends ArrayAdapter<Chats> {

    private TextView tvMsg;
    private TextView tvMsgTime;
    private List<Chats> chatMessageList = new ArrayList<Chats>();
    private Context context;

    @Override
    public void add(Chats object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public Chats getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Chats chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.student_outgoing_chat_msg, parent, false);
        }else{
            row = inflater.inflate(R.layout.student_sender_chat_msg, parent, false);
        }
        tvMsg = (TextView) row.findViewById(R.id.tvMsg);
        tvMsgTime = (TextView) row.findViewById(R.id.tvMsgTime);
        tvMsg.setText(Html.fromHtml(chatMessageObj.message));
        tvMsgTime.setText(chatMessageObj.dateTime);
        return row;
    }
}