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
import java.util.List;
import thm.com.gr2.R;
import thm.com.gr2.adapter.SuggestExpandableListAdapter;
import thm.com.gr2.model.Suggest;
import thm.com.gr2.util.Constants;

public class SuggestActivity extends AppCompatActivity {

    private List<Suggest> mAdviceList;
    private ExpandableListView mExpandableListView;
    private BaseExpandableListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        Toolbar toolbar = findViewById(R.id.toolbar_suggesst);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        mAdviceList = (List<Suggest>) getIntent().getSerializableExtra("advice");
        setupViews();
    }

    private void setupViews() {
        mExpandableListView = findViewById(R.id.lv_suggest);
        mAdapter = new SuggestExpandableListAdapter(this, mAdviceList);
        mExpandableListView.setAdapter(mAdapter);
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
