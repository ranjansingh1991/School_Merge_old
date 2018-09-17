package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.semicolonindia.schoolcrm.student.StudentHomeMenuFragment;
import in.semicolonindia.schoolcrm.student.StudentHomeOptionFragment;

/**
 * Created by Rupesh on 01-02-2018.
 */

@SuppressWarnings("ALL")
public class StudentHomeViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] sTitle = {"Menu", "Options"};

    public StudentHomeViewPagerAdapter(FragmentManager fm, Context contex) {
        super(fm);
        this.context=contex;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                // getBookList();
                fragment = new StudentHomeMenuFragment();
                // used to initialise QuestionsTab
                break;
            case 1:
                fragment = new StudentHomeOptionFragment();        // used to initialise QueryTab
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

