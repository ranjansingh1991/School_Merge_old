package in.semicolonindia.schoolcrm.teacher.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.TeacherDialogAssignment;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherAssignmentName;

/**
 * Created by Rupesh on 06-02-2018.
 */

@SuppressWarnings("ALL")
public class TeacherAssignmentDetailsAdapter extends RecyclerView.Adapter<TeacherAssignmentDetailsAdapter.ViewHolder> {

    Activity activity;
    LayoutInflater inflater;
    private List<TeacherAssignmentName> teacherAssignmentNamesList = null;
    private ArrayList<TeacherAssignmentName> arraylist;
    private RecyclerView rvAssignmenttName;

    public TeacherAssignmentDetailsAdapter(Activity activity, List<TeacherAssignmentName> teacherAssignmentNamesList) {
        this.activity = activity;
        this.teacherAssignmentNamesList = teacherAssignmentNamesList;
        inflater = LayoutInflater.from(activity);
        this.arraylist = new ArrayList<TeacherAssignmentName>();
        this.arraylist.addAll(teacherAssignmentNamesList);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.teacher_assignment_details_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TeacherAssignmentName itemDetails = arraylist.get(position);
        // Picasso.with(activity).load(itemDetails.getImage_url()).into(civ_image);
        holder.tvAssignment_Title.setText(itemDetails.getTitle());
        holder.tvAssignDate.setText(itemDetails.getUploadDate());
        holder.tvSubmitDate.setText(itemDetails.getSubmitDate());
        holder.tvDescription.setText(itemDetails.getDescription());

        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeacherDialogAssignment mViewDialog = new TeacherDialogAssignment(activity, R.style.DialogSlideAnim,
                        holder.tvAssignment_Title.getText().toString(),
                        holder.tvAssignDate.getText().toString(),holder.tvSubmitDate.getText().toString(),
                        holder.tvDescription.getText().toString());
                mViewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mViewDialog.getWindow().setGravity(Gravity.BOTTOM);
                mViewDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return teacherAssignmentNamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssignment_Title;
        TextView btnView;
        TextView tvAssignDate;
        TextView tvSubmitDate;
        TextView tvDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            tvAssignment_Title = itemView.findViewById(R.id.tvTitleName);
            btnView = itemView.findViewById(R.id.btnView);
            tvAssignDate = itemView.findViewById(R.id.tv_uploadDate);
            tvSubmitDate = itemView.findViewById(R.id.tv_SubmitDate);
            tvDescription = itemView.findViewById(R.id.tv_Desp);

        }
    }
}
