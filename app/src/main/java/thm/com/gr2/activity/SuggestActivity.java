package thm.com.gr2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import java.util.List;
import thm.com.gr2.R;
import thm.com.gr2.adapter.SuggestExpandableListAdapter;
import thm.com.gr2.model.Advice;
import thm.com.gr2.util.Constants;

public class SuggestActivity extends AppCompatActivity {

    private List<Advice> mAdviceList;
    private ExpandableListView mExpandableListView;
    private BaseExpandableListAdapter mAdapter;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        Toolbar toolbar = findViewById(R.id.toolbar_suggest);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        TapTargetView.showFor(this,
                TapTarget.forView(findViewById(R.id.fab_redo), "Tap to do the quiz again")
                        .outerCircleColor(R.color.color_basic_grey)
                        .textColor(R.color.color_white)
                        .targetRadius(60)
                        .cancelable(true)
                        .tintTarget(false)
                        .transparentTarget(false));
        mAdviceList = (List<Advice>) getIntent().getSerializableExtra("advice");
        setupViews();
    }

    private void setupViews() {
        mExpandableListView = findViewById(R.id.lv_suggest);
        mAdapter = new SuggestExpandableListAdapter(this, mAdviceList);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setOnGroupExpandListener(i -> {
            if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                mExpandableListView.collapseGroup(lastExpandedPosition);
            }
            lastExpandedPosition = i;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                SharedPreferences.Editor editor =
                        getSharedPreferences(Constants.PREF_NAME_USER, Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(SuggestActivity.this, SigninActivity.class);
                SuggestActivity.this.startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void redo(View view) {
        Intent intent = new Intent(this, RuleActivity.class);
        startActivity(intent);
        finish();
    }
}
