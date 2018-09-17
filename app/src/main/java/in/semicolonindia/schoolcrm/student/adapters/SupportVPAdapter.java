package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.semicolonindia.schoolcrm.student.fragments.AboutUsFragment;
import in.semicolonindia.schoolcrm.student.fragments.ContactUsFragment;
import in.semicolonindia.schoolcrm.student.fragments.FAQFragment;
import in.semicolonindia.schoolcrm.student.fragments.PrivacyPolicyFragment;
import in.semicolonindia.schoolcrm.student.fragments.TermsNConditionsFragment;
import in.semicolonindia.schoolcrm.student.fragments.UserGuideFragment;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class SupportVPAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] sTitle = {"FAQ's", "CONTACT US", "T&C", "PRIVACY & POLICY", "ABOUT US", "USER GUIDE"};

    public SupportVPAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FAQFragment();
                // used to initialise QuestionsTab
                break;
            case 1:
                fragment = new ContactUsFragment();
                break;
            case 2:
                fragment = new TermsNConditionsFragment();
                break;
            case 3:
                fragment = new PrivacyPolicyFragment();
                break;
            case 4:
                fragment = new AboutUsFragment();
                break;
            case 5:
                fragment = new UserGuideFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return sTitle.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sTitle[position];
    }
}