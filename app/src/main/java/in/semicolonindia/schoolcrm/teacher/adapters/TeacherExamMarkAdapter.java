package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherExamMarksActivity;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherExamMarkNames;

/**
 * Created by Rupesh on 09-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherExamMarkAdapter extends RecyclerView.Adapter<TeacherExamMarkAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    private List<TeacherExamMarkNames> markNamesList = null;
    private ArrayList<TeacherExamMarkNames> arraylist;

    public TeacherExamMarkAdapter(Context context, List<TeacherExamMarkNames> markNamesList, RecyclerView rv_examMark) {
        this.context = context;
        this.markNamesList = markNamesList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<TeacherExamMarkNames>();

        for (int i = 0; i < markNamesList.size(); i++) {
            TeacherExamMarksActivity.alExmMrks.add(new TeacherExamMarkNames());
        }
        this.arraylist.addAll(markNamesList);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_exam_marks_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        // Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        Typeface appFontLight = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.ttf");

        holder.tv_rollNumber.setText(markNamesList.get(position).getRollNumber());
        holder.tvStudID.setText(markNamesList.get(position).getRollNumber());
        // holder.tvStudentName.setText(markNamesList.get(position).getName());
        if (markNamesList.get(position).getName() == null ||
                markNamesList.get(position).getName().equalsIgnoreCase("null")) {
            holder.tvStudentName.setText("N/A");
        } else {
            holder.tvStudentName.setText(markNamesList.get(position).getName());
        }
        holder.etStudentMark.setText(markNamesList.get(position).getObtainedmarks());
        holder.etComment.setText(markNamesList.get(position).getComment());
        holder.tvExmID.setText(markNamesList.get(position).getExmID());
        holder.tvTotlMrks.setText(markNamesList.get(position).getTotalMrks());
        // if anywhere is null,,we will use N/A...........
        //holder.tvStudentName.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        // holder.tvStudentName.setText("N/A");

        holder.tv_rollNumber.setTypeface(appFontLight);
        holder.tvStudentName.setTypeface(appFontLight);
        holder.etStudentMark.setTypeface(appFontLight);
        holder.etComment.setTypeface(appFontLight);

        holder.tvSerialNo.setText(String.valueOf(position + 1));

        holder.etStudentMark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TeacherExamMarksActivity.alExmMrks.set(position, new TeacherExamMarkNames(holder.tv_rollNumber.getText().toString(),
                        holder.tvStudentName.getText().toString(), holder.etStudentMark.getText().toString(),
                        holder.etComment.getText().toString(),
                        holder.tvStudID.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TeacherExamMarksActivity.alExmMrks.set(position, new TeacherExamMarkNames(holder.tv_rollNumber.getText().toString(),
                        holder.tvStudentName.getText().toString(), holder.etStudentMark.getText().toString(),
                        holder.etComment.getText().toString(),
                        holder.tvStudID.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return markNamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_rollNumber;
        TextView tvSerialNo;
        TextView tvStudID;
        TextView tvStudentName;
        EditText etStudentMark;
        EditText etComment;
        TextView tvExmID;
        TextView tvTotlMrks;



        public ViewHolder(View itemView) {
            super(itemView);

            tv_rollNumber = itemView.findViewById(R.id.tv_rollNumber);
            tvSerialNo = itemView.findViewById(R.id.tvSerialNo);
            tvStudID = itemView.findViewById(R.id.tvStudID);
            tvStudentName = itemView.findViewById(R.id.tv_studentName);
            etStudentMark = itemView.findViewById(R.id.et_studentMark);
            etComment = itemView.findViewById(R.id.et_comment);
            tvExmID = itemView.findViewById(R.id.tvExmID);
            tvTotlMrks = itemView.findViewById(R.id.tvTotalMarks);

        }
    }
}
