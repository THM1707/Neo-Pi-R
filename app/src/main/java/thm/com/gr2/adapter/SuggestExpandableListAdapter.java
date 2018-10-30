package thm.com.gr2.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.List;
import thm.com.gr2.R;
import thm.com.gr2.model.Suggest;

public class SuggestExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Suggest> mSuggestList;

    public SuggestExpandableListAdapter(Context context, List<Suggest> suggestList) {
        mContext = context;
        mSuggestList = suggestList;
    }

    @Override
    public int getGroupCount() {
        return mSuggestList == null ? 0 : mSuggestList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mSuggestList.get(i).getContent().split("\\|").length;
    }

    @Override
    public Object getGroup(int i) {
        return mSuggestList.get(i).getName();
    }

    @Override
    public Object getChild(int i, int i1) {
        return mSuggestList.get(i).getContent().split("\\|")[i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup group) {
        String listTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.group_suggest, null);
        }
        TextView listTitleTextView = view.findViewById(R.id.tv_suggest_group_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View childView, ViewGroup group) {
        final String itemText = (String) getChild(i, i1);
        if (childView == null) {
            LayoutInflater layoutInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childView = layoutInflater.inflate(R.layout.item_suggest, null);
        }
        TextView suggestItem = childView.findViewById(R.id.tv_suggest_item_content);
        suggestItem.setText(itemText);
        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
