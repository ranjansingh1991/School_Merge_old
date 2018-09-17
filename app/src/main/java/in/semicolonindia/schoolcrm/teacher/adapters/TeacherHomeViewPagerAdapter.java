package in.semicolonindia.schoolcrm.teacher.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.semicolonindia.schoolcrm.teacher.fragments.TeacherHomeMenuFragment;
import in.semicolonindia.schoolcrm.teacher.fragments.TeacherHomeOptionFragment;

/**
 * Created by Rupesh on 09-02-2018.
 */
@SuppressWarnings("ALL")
public class TeacherHomeViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] sTitle = {"Menu", "Options"};

    public TeacherHomeViewPagerAdapter(FragmentManager fm, Context contex) {
        super(fm);
        this.context = contex;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                // getBookList();
                fragment = new TeacherHomeMenuFragment();
                // used to initialise QuestionsTab
                break;
            case 1:
                fragment = new TeacherHomeOptionFragment();        // used to initialise QueryTab
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

