package in.semicolonindia.schoolcrm.student.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by RANJAN SINGH on 11/29/2017.
 */
@SuppressWarnings("ALL")
public class TermsNConditionsFragment extends Fragment {
    private WebView webView_TermsCnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_fragment_terms_n_web_view, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        webView_TermsCnd = (WebView) rootView.findViewById(R.id.webView_TermsCnd);
        String url = "http://semicolonindia.in/";
        webView_TermsCnd.loadUrl(url);
    }
}