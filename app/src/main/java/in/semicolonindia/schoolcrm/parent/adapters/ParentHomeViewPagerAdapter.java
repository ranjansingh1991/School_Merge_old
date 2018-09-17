package in.semicolonindia.schoolcrm.parent.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.semicolonindia.schoolcrm.parent.fragments.ParentHomeMenuFragment;
import in.semicolonindia.schoolcrm.parent.fragments.ParentHomeOptionFragment;

/**
 * Created by Rupesh on 12-02-2018.
 */
@SuppressWarnings("ALL")
public class ParentHomeViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] sTitle = {"Menu", "Options"};

    public ParentHomeViewPagerAdapter(FragmentManager fm, Context contex) {
        super(fm);
        this.context=contex;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                // getBookList();
                fragment = new ParentHomeMenuFragment();
                // used to initialise QuestionsTab
                break;
            case 1:
                fragment = new ParentHomeOptionFragment();        // used to initialise QueryTab
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
