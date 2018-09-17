package in.semicolonindia.schoolcrm.student;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.activities.BenchActivity;
import in.semicolonindia.schoolcrm.student.activities.ExtraCurriculamActivity;
import in.semicolonindia.schoolcrm.student.activities.FeedbackActivity;
import in.semicolonindia.schoolcrm.student.activities.GalleryActivity;
import in.semicolonindia.schoolcrm.student.activities.LeaderBoardActivity;
import in.semicolonindia.schoolcrm.student.activities.NewsActivity;
import in.semicolonindia.schoolcrm.student.activities.ScholarshipActivity;
import in.semicolonindia.schoolcrm.student.activities.SupportActivity;
import in.semicolonindia.schoolcrm.student.adapters.StudentHomeOptionAdapter;
import in.semicolonindia.schoolcrm.student.beans.FirstName;
import in.semicolonindia.schoolcrm.dialogs.NoConnectionDialog;
import in.semicolonindia.schoolcrm.dialogs.ProgressDialog;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;

/**
 * Created by Rupesh on 01-02-2018.
 */
@SuppressWarnings("ALL")
public class StudentHomeOptionFragment extends Fragment {
    ProgressDialog mProgressDialog;
    private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private StudentHomeOptionAdapter studentHomeOptionAdapter;
    ListView lv_Second;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_home_list_option, container, false);
        lv_Second = rootView.findViewById(R.id.lv_Second);

        StudentHomeOptionAdapter studentHomeOptionAdapter = new StudentHomeOptionAdapter(getContext(),
                getResources().getStringArray(R.array.student_home_option_item_fragment));
        lv_Second.setAdapter(studentHomeOptionAdapter);

        lv_Second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {
                        case 0:
                            getActivity().startActivity(new Intent(getActivity(), GalleryActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 1:
                            getActivity().startActivity(new Intent(getActivity(), NewsActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 2:
                            getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 3:
                            getActivity().startActivity(new Intent(getActivity(), LeaderBoardActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 4:
                            getActivity().startActivity(new Intent(getActivity(), BenchActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 5:
                            getActivity().startActivity(new Intent(getActivity(), ScholarshipActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 6:
                            getActivity().startActivity(new Intent(getActivity(), ExtraCurriculamActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 7:
                            getActivity().startActivity(new Intent(getActivity(), SupportActivity.class));
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