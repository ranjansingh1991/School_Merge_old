package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherChats;

/**
 * Created by Rupesh on 07-02-2018.
 */

@SuppressWarnings("ALL")
public class TeacherChatListAdapter extends ArrayAdapter<TeacherChats> {

    private TextView chatText;
    private TextView tvMsgTime;
    private List<TeacherChats> chatMessageList = new ArrayList<TeacherChats>();
    private Context context;

    @Override
    public void add(TeacherChats object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public TeacherChatListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public TeacherChats getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TeacherChats chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.teacher_outgoing_chat_msg, parent, false);
        }else{
            row = inflater.inflate(R.layout.teacher_sender_chat_msg, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.tvMsg);
        tvMsgTime = (TextView) row.findViewById(R.id.tvMsgTime);
        chatText.setText(chatMessageObj.message);
        tvMsgTime.setText(chatMessageObj.dateTime);
        return row;
    }
}