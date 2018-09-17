package in.semicolonindia.schoolcrm.parent.fragments;

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
import in.semicolonindia.schoolcrm.parent.activities.ParentBenchActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentExtraCurriculumActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentFeedbackActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentGalleryActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentLeaderBoardActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentNewsActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentScholarshipActivity;
import in.semicolonindia.schoolcrm.parent.activities.ParentSupportActivity;
import in.semicolonindia.schoolcrm.parent.adapters.ParentHomeOptionAdapter;
import in.semicolonindia.schoolcrm.student.beans.FirstName;
import in.semicolonindia.schoolcrm.util.ConnectionDetector;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentHomeOptionFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    private ArrayList<FirstName> arraylist = new ArrayList<FirstName>();
    private ParentHomeOptionAdapter parentHomeOptionAdapter;
    ListView lv_Second;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.teacher_fragment_home_option, container, false);
        lv_Second = rootView.findViewById(R.id.lv_Second);

        ParentHomeOptionAdapter parentHomeOptionAdapter = new ParentHomeOptionAdapter(getContext(),
                getResources().getStringArray(R.array.parent_home_option_item_fragment));
        lv_Second.setAdapter(parentHomeOptionAdapter);

        lv_Second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    switch (position) {
                        case 0:
                            getActivity().startActivity(new Intent(getActivity(), ParentGalleryActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 1:
                            getActivity().startActivity(new Intent(getActivity(), ParentNewsActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 2:
                            getActivity().startActivity(new Intent(getActivity(), ParentFeedbackActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 3:
                            getActivity().startActivity(new Intent(getActivity(), ParentLeaderBoardActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;

                        case 4:
                            getActivity().startActivity(new Intent(getActivity(), ParentBenchActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 5:
                            getActivity().startActivity(new Intent(getActivity(), ParentScholarshipActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 6:
                            getActivity().startActivity(new Intent(getActivity(), ParentExtraCurriculumActivity.class));
                            getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
                            break;
                        case 7:
                            getActivity().startActivity(new Intent(getActivity(), ParentSupportActivity.class));
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
