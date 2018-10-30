package thm.com.gr2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.List;
import thm.com.gr2.R;
import thm.com.gr2.adapter.SuggestExpandableListAdapter;
import thm.com.gr2.model.Suggest;

public class SuggestActivity extends AppCompatActivity {

    private List<Suggest> mAdviceList;
    private ExpandableListView mExpandableListView;
    BaseExpandableListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        mAdviceList = (List<Suggest>) getIntent().getSerializableExtra("advice");
        setupViews();
    }

    private void setupViews() {
        mExpandableListView = findViewById(R.id.lv_suggest);
        mAdapter = new SuggestExpandableListAdapter(this, mAdviceList);
        mExpandableListView.setAdapter(mAdapter);
    }
}
