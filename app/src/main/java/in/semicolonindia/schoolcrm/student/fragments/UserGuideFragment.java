package in.semicolonindia.schoolcrm.student.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import in.semicolonindia.schoolcrm.R;


public class UserGuideFragment extends Fragment {

    private WebView webView_User_Guide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.student_fragment_user_guide, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        webView_User_Guide = (WebView) rootView.findViewById(R.id.webView_User_Guide);
        String url = "http://semicolonindia.in/";
        webView_User_Guide.loadUrl(url);
    }
}