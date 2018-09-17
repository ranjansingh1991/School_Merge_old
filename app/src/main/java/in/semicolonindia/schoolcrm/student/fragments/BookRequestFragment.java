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
import in.semicolonindia.schoolcrm.student.adapters.BookRequestListAdapter;
import in.semicolonindia.schoolcrm.student.beans.BookRequestNames;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
@SuppressWarnings("ALL")
public class BookRequestFragment extends Fragment {

    private ArrayList<BookRequestNames> arraylist = new ArrayList<BookRequestNames>();
    private BookRequestListAdapter mBookRequestListAdapter;

    //1. Create Constructer and pass Model parameter...
    public BookRequestFragment(List<BookRequestNames> bookRequestNamesList) {
        this.arraylist = new ArrayList<BookRequestNames>();
        this.arraylist.addAll(bookRequestNamesList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_fragment_book_request, container, false);
        final ListView lvBookRequestList = (ListView) rootView.findViewById(R.id.lvBookRequestList);
        //2. Take one Adapter and set adapter
        final BookRequestListAdapter mBookRequestListAdapter = new BookRequestListAdapter(getActivity(), arraylist);
        lvBookRequestList.setAdapter(mBookRequestListAdapter);
        final EditText etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                if (mBookRequestListAdapter != null) {
                    mBookRequestListAdapter.filter(text);
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