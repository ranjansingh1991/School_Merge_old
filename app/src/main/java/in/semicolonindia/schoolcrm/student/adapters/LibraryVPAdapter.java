package in.semicolonindia.schoolcrm.student.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import in.semicolonindia.schoolcrm.student.beans.BookListNames;
import in.semicolonindia.schoolcrm.student.beans.BookRequestNames;
import in.semicolonindia.schoolcrm.student.fragments.BookListFragment;
import in.semicolonindia.schoolcrm.student.fragments.BookRequestFragment;

/**
 * Created by Rupesh on 02-02-2018.
 */
@SuppressWarnings("ALL")
public class LibraryVPAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] sTitle = {"book list", "book requests"};
    private List<BookListNames> bookListNamesList;
    private List<BookRequestNames> bookRequestNamesList;

    public LibraryVPAdapter(FragmentManager fm, Context context, List<BookListNames> bookListNamesList,
                            List<BookRequestNames> bookRequestNamesList) {
        super(fm);
        this.context = context;
        this.bookListNamesList = bookListNamesList;
        this.bookRequestNamesList = bookRequestNamesList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BookListFragment(bookListNamesList);
                break;
            case 1:
                fragment = new BookRequestFragment(bookRequestNamesList);
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