package in.semicolonindia.schoolcrm.student.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by RANJAN SINGH on 11/29/2017.
 ndia.studentcrm*/
@SuppressWarnings("ALL")
public class ContactUsFragment extends Fragment {

    private TextView tvTitle;
    private TextView tvCompanyNo;
    private TextView tvCompanyNoText;
    private TextView tvCompanyNoWA;
    private TextView tvCompanyEmail;
    private TextView tvCompanyAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_fragment_contact_us, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        Typeface appFontMontserrat = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat_light.ttf");
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvCompanyNo = (TextView) rootView.findViewById(R.id.tvCompanyNo);
        tvCompanyNoText = (TextView) rootView.findViewById(R.id.tvCompanyNoText);
        tvCompanyNoWA = (TextView) rootView.findViewById(R.id.tvCompanyNoWA);
        tvCompanyEmail = (TextView) rootView.findViewById(R.id.tvCompanyEmail);
        tvCompanyAddress = (TextView) rootView.findViewById(R.id.tvCompanyAddress);
        tvTitle.setTypeface(appFontMontserrat);
        tvCompanyNo.setTypeface(appFontMontserrat);
        tvCompanyNoText.setTypeface(appFontMontserrat);
        tvCompanyNoWA.setTypeface(appFontMontserrat);
        tvCompanyEmail.setTypeface(appFontMontserrat);
        tvCompanyAddress.setTypeface(appFontMontserrat);
    }
}