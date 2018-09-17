package in.semicolonindia.schoolcrm.student.fragments;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import in.semicolonindia.schoolcrm.R;


/**
 * Created by RANJAN SINGH on 11/29/2017.
 */
@SuppressWarnings("ALL")
public class FAQFragment extends Fragment {

    private ExpandableListView elvQuestionList;
    private List<String> lGroupList; // used for storing questions
    private List<String> lChildList; // used for storing answers
    private Map<String, List<String>> lQuestionCollection;
    private int[] nGroupImage;
    private int nLastExpandedPosition = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_fragment_faq, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        elvQuestionList = (ExpandableListView) rootView.findViewById(R.id.elvQuestionList);
        createGroupList();
        createCollection();
        ExpandableList expListAdapter = new ExpandableList(lGroupList, lQuestionCollection);
        elvQuestionList.setAdapter(expListAdapter);
        Display _Display = getActivity().getWindowManager().getDefaultDisplay();
        int nWidth = _Display.getWidth();
        elvQuestionList.setIndicatorBounds(nWidth - 150, nWidth - 60);
        setListEvent();
        elvQuestionList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (nLastExpandedPosition != -1 && groupPosition != nLastExpandedPosition) {
                    elvQuestionList.collapseGroup(nLastExpandedPosition);
                }
                nLastExpandedPosition = groupPosition;
            }
        });
    }

    private void setListEvent() {
        elvQuestionList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int arg0) {
                nGroupImage[arg0] = 1;
            }
        });

        elvQuestionList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int arg0) {
                nGroupImage[arg0] = 0;
            }
        });
    }

    private void createGroupList() {
        lGroupList = new ArrayList<String>();
        lGroupList.add("1. Question number 1?");
        lGroupList.add("2. Question number 2?");
        lGroupList.add("3. Question number 3?");
        lGroupList.add("4. Question number 4?");
        lGroupList.add("5. Question number 5?");
        lGroupList.add("6. Question number 6?");
        lGroupList.add("7. Question number 7?");
        lGroupList.add("8. Question number 8?");
        lGroupList.add("9. Question number 9?");
        lGroupList.add("10. Question number 10?");
        nGroupImage = new int[(lGroupList.size())];
    }

    private void createCollection() {
        String[] sAns1 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns2 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns3 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns4 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns5 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns6 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns7 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns8 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns9 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s " +
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s Lorem Ipsum " +
                "is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s Lorem Ipsum is simply" +
                " dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s Lorem Ipsum is simply dummy text " +
                "of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};
        String[] sAns10 = {"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s "};

        lQuestionCollection = new LinkedHashMap<String, List<String>>();

        for (String ans : lGroupList) {
            switch (lQuestionCollection.size()) {
                case 0:
                    loadChild(sAns1);
                    break;
                case 1:
                    loadChild(sAns2);
                    break;
                case 2:
                    loadChild(sAns3);
                    break;
                case 3:
                    loadChild(sAns4);
                    break;
                case 4:
                    loadChild(sAns5);
                    break;
                case 5:
                    loadChild(sAns6);
                    break;
                case 6:
                    loadChild(sAns7);
                    break;
                case 7:
                    loadChild(sAns8);
                    break;
                case 8:
                    loadChild(sAns9);
                    break;
                case 9:
                    loadChild(sAns10);
                    break;
            }
            lQuestionCollection.put(ans, lChildList);
        }
    }

    private void loadChild(String[] childAnswer) {
        lChildList = new ArrayList<String>();
        for (String child : childAnswer) lChildList.add(child);
    }

    private class ExpandableList extends BaseExpandableListAdapter {
        private Map<String, List<String>> lQuestionCollections;
        private List<String> lChildList;

        public ExpandableList(List<String> childList,
                              Map<String, List<String>> questionCollections) {
            this.lQuestionCollections = questionCollections;
            this.lChildList = childList;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return lQuestionCollections.get(lChildList.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            final String sText = (String) getChild(groupPosition, childPosition);
            LayoutInflater inflater = getActivity().getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.student_faq_child_item, null);
            }

            TextView tvItem = (TextView) convertView.findViewById(R.id.tvItemChild);
            Typeface appFontMontserrat = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/montserrat_light.ttf");
            tvItem.setTypeface(appFontMontserrat);
            tvItem.setText(sText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return lQuestionCollections.get(lChildList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return lChildList.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return lChildList.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String answers = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = View.inflate(getActivity(), R.layout.student_faq_group_item, null);
            }
            final Typeface appFontBold = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/montserrat_bold.ttf");
            final TextView tvItem = (TextView) convertView.findViewById(R.id.tvItemGroup);
            tvItem.setTypeface(appFontBold, Typeface.BOLD);
            tvItem.setText(answers);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }
    }
}