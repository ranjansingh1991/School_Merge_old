package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.teacher.beans.TeacherClassRoutine;
import in.semicolonindia.schoolcrm.util.PreferencesManager;

/**
 * Created by Rupesh on 08-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherRoutinSubjectsHorzListAdapter extends RecyclerView.Adapter<TeacherRoutinSubjectsHorzListAdapter.ViewHolder> {

    private Context context;
    private char[] cDayNames;
    private int nHeight;
    private ArrayList<ArrayList<TeacherClassRoutine>> alRoutine;
    private String[] sSubjectNames;

    public TeacherRoutinSubjectsHorzListAdapter(Context context, int nHeight, ArrayList<ArrayList<TeacherClassRoutine>> alRoutine) {
        this.context = context;
        this.nHeight = nHeight;
        cDayNames = new char[]{' ', 'S', 'M', 'T', 'W', 'T', 'F', 'S'};
        this.alRoutine = alRoutine;
        this.sSubjectNames = new PreferencesManager(context).getSubjectNames().split(",");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_routine_subject_list_list, parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Typeface appFontRegular = Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.ttf");
        ArrayList<String> sData = new ArrayList<>();
        sData.add(sSubjectNames[position]);
        for (int i = 0; i < alRoutine.size(); i++) {
            for (int j = 0; j < alRoutine.get(i).size(); j++) {
                if (sSubjectNames[position].equalsIgnoreCase(alRoutine.get(i).get(j).getSubjectName())) {
                    sData.add(alRoutine.get(i).get(j).getStartEndTime());
                }
            }
        }
        TeacherRoutineSubjectTimeListItems mTeacherRoutineSubjectTimeListItems = new TeacherRoutineSubjectTimeListItems(context, nHeight, sData);
        holder.lvSubjectsList.setAdapter(mTeacherRoutineSubjectTimeListItems);
    }

    @Override
    public int getItemCount() {
        return sSubjectNames.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ListView lvSubjectsList;

        public ViewHolder(View itemView) {
            super(itemView);
            lvSubjectsList = (ListView) itemView.findViewById(R.id.lvSubjectsList);
        }
    }
}