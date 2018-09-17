package in.semicolonindia.schoolcrm.student.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.semicolonindia.schoolcrm.R;
import in.semicolonindia.schoolcrm.student.adapters.BookListListAdapter;
import in.semicolonindia.schoolcrm.student.beans.BookListNames;

/**
 * Created by Rupesh on 02-02-2018.
 */

@SuppressLint("ValidFragment")
public class BookListFragment extends Fragment {

    private ArrayList<BookListNames> arraylist = new ArrayList<BookListNames>();
    //private BookListListAdapter mBookListListAdapter;

    // 1. Create Constructer and pass Model parameter...
    public BookListFragment(List<BookListNames> bookListNamesList) {
        this.arraylist = new ArrayList<BookListNames>();
        this.arraylist.addAll(bookListNamesList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_fragment_book_list, container, false);
        final ListView lvBookList = (ListView) rootView.findViewById(R.id.lvBookList);
        final EditText etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        //2. Take one Adapter and set adapter
        final BookListListAdapter mBookListListAdapter = new BookListListAdapter(getActivity(), arraylist);
        lvBookList.setAdapter(mBookListListAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                if (mBookListListAdapter != null) {
                    mBookListListAdapter.filter(text);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        return rootView;
    }
}