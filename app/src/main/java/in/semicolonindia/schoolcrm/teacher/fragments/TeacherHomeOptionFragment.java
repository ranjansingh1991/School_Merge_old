package in.semicolonindia.schoolcrm.teacher.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.student.adapters.StudentHomeOptionAdapter;
import in.semicolonindia.schoolcrm.student.beans.FirstName;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherBenchActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherExtraCurriculumActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherFeedbackActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherGalleryActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherLeaderboardActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherNewsActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherScholarshipActivity;
import in.semicolonindia.schoolcrm.teacher.activities.TeacherSupportActivity;
import in.semicolonindia.schoolcrm.teacher.adapters.TeacherHomeOptionAdapter;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;

/**
 * Created by Rupesh on 03-02-2018.
 */
@SuppressWarnings("all")

public class TeacherHomeOptionFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private TeacherHomeOptionAdapter teacherHomeOptionAdapter;
    ListView lv_Second;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.teacher_fragment_home_option, container, false);
        lv_Second = rootView.findViewById(R.id.lv_Second);

        StudentHomeOptionAdapter studentHomeOptionAdapter = new StudentHomeOptionAdapter(getContext(),
                getResources().getStringArray(R.array.teacher_home_option_item_fragment));
        lv_Second.setAdapter(studentHomeOptionAdapter);

        lv_Second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {
                        case 0:
                            getActivity().startActivity(new Intent(getActivity(), TeacherGalleryActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 1:
                            getActivity().startActivity(new Intent(getActivity(), TeacherNewsActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 2:
                            getActivity().startActivity(new Intent(getActivity(), TeacherFeedbackActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 3:
                            getActivity().startActivity(new Intent(getActivity(), TeacherLeaderboardActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 4:
                            getActivity().startActivity(new Intent(getActivity(), TeacherBenchActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 5:
                            getActivity().startActivity(new Intent(getActivity(), TeacherScholarshipActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 6:
                            getActivity().startActivity(new Intent(getActivity(), TeacherExtraCurriculumActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 7:
                            getActivity().startActivity(new Intent(getActivity(), TeacherSupportActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                    }

                } else {
                    final NoConnectionDialog mNoConnectionDialog = new NoConnectionDialog(getActivity(),
                            R.style.DialogSlideAnim);
                    mNoConnectionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mNoConnectionDialog.setCancelable(false);
                    mNoConnectionDialog.setCanceledOnTouchOutside(false);
                    mNoConnectionDialog.getWindow().setGravity(Gravity.BOTTOM);
                    mNoConnectionDialog.show();
                }
            }
        });


        return rootView;
    }
}