package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherAttendanceActivity;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherAttendanceMark;

/**
 * Created by Rupesh on 07-02-2018.
 */

@SuppressWarnings("ALL")
public class TeacherAttendanceAdapter extends RecyclerView.Adapter<TeacherAttendanceAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    private List<TeacherAttendanceMark> attendanceNamesList = null;
    private ArrayList<TeacherAttendanceMark> arrayList;

    public TeacherAttendanceAdapter(Context context, List<TeacherAttendanceMark>
            attendanceNamesList, RecyclerView rv_Attendence) {
        this.context = context;
        this.attendanceNamesList = attendanceNamesList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<TeacherAttendanceMark>();
        this.arrayList.addAll(attendanceNamesList);
        TeacherAttendanceActivity.alAttendancelist = new ArrayList<TeacherAttendanceMark>();
        TeacherAttendanceActivity.alAttendancelist.addAll(attendanceNamesList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView;
        TeacherAttendanceAdapter.ViewHolder mViewHolder = null;
        if (viewType == attendanceNamesList.size()) {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_attendance_marks_all, parent, false);
            mViewHolder = new TeacherAttendanceAdapter.ViewHolder(rootView, viewType);
        } else {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_attendance_list_item, parent, false);
            mViewHolder = new TeacherAttendanceAdapter.ViewHolder(rootView, viewType);
        }
        return mViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position == attendanceNamesList.size()) {
            holder.btnPresent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // For Mark all present button click listnner
                    for (int i = 0; i < attendanceNamesList.size() - 1; i++) {
                        TeacherAttendanceMark mAttendanceMark = new TeacherAttendanceMark(
                                attendanceNamesList.get(i).getRollNumber(),
                                attendanceNamesList.get(i).getName(),
                                attendanceNamesList.get(i).getStudentId(),
                                "P");
                        TeacherAttendanceActivity.alAttendancelist.set(i, mAttendanceMark);
                    }
                    holder.btnPresent.setText("All PRESENT");
                    Toast.makeText(context, "ALL MARKED AS PRESENT! PRESS SUBMIT TO UPLOAD", Toast.LENGTH_LONG).show();
                }
            });

            holder.btnAbsent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //For Mark all absent button click listnner
                    for (int i = 0; i < attendanceNamesList.size(); i++) {
                        TeacherAttendanceMark mAttendanceMark = new TeacherAttendanceMark(
                                attendanceNamesList.get(i).getRollNumber(),
                                attendanceNamesList.get(i).getName(),
                                attendanceNamesList.get(i).getStudentId(),
                                "A");
                        TeacherAttendanceActivity.alAttendancelist.set(i, mAttendanceMark);
                    }
                    holder.btnAbsent.setText("All ABSENT");
                    Toast.makeText(context, "ALL MARKED AS ABSENT! PRESS SUBMIT TO UPLOAD", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            holder.tvrRollNumber.setText(attendanceNamesList.get(position).getRollNumber());
            if (attendanceNamesList.get(position).getName() == null ||
                    attendanceNamesList.get(position).getName().equalsIgnoreCase("null")) {
                holder.tvStudentName.setText("N/A");
            } else {
                holder.tvStudentName.setText(attendanceNamesList.get(position).getName());
            }
            holder.tvSerialNo.setText(String.valueOf(position + 1));
            holder.arbPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.arbPresent.isChecked()) {
                        TeacherAttendanceMark mAttendanceMark = new TeacherAttendanceMark(
                                attendanceNamesList.get(position).getRollNumber(),
                                attendanceNamesList.get(position).getName(),
                                attendanceNamesList.get(position).getStudentId(),
                                "P");
                        TeacherAttendanceActivity.alAttendancelist.set(position, mAttendanceMark);
                    }
                }
            });
            holder.arbAbsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.arbAbsent.isChecked()) {
                        TeacherAttendanceMark mAttendanceMark = new TeacherAttendanceMark(
                                attendanceNamesList.get(position).getRollNumber(),
                                attendanceNamesList.get(position).getName(),
                                attendanceNamesList.get(position).getStudentId(),
                                "A");
                        TeacherAttendanceActivity.alAttendancelist.set(position, mAttendanceMark);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return attendanceNamesList.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvrRollNumber;
        TextView tvSerialNo;
        TextView tvStudentName;
        AppCompatRadioButton arbPresent;
        AppCompatRadioButton arbAbsent;
        Button btnPresent, btnAbsent;


        public ViewHolder(View itemView, int nPosition) {
            super(itemView);
            if (nPosition == attendanceNamesList.size()) {
                btnPresent = (Button) itemView.findViewById(R.id.btnPresent);
                btnAbsent = (Button) itemView.findViewById(R.id.btnAbsent);
            } else {
                tvrRollNumber = (TextView) itemView.findViewById(R.id.tv_rollNumber);
                tvSerialNo = (TextView) itemView.findViewById(R.id.tvSerialNo);
                tvStudentName = (TextView) itemView.findViewById(R.id.tv_studentName);
                arbPresent = (AppCompatRadioButton) itemView.findViewById(R.id.arbPresent);
                arbAbsent = (AppCompatRadioButton) itemView.findViewById(R.id.arbAbsent);
            }
        }
    }
}