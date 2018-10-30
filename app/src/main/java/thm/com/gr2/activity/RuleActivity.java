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
import android.widget.TextView;
import thm.com.gr2.R;
import thm.com.gr2.util.Constants;

public class RuleActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        Toolbar toolbar = findViewById(R.id.toolbar_rule);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, Context.MODE_PRIVATE);
        setupView();
    }

    private void setupView() {
        String name = mSharedPreferences.getString(Constants.USER_PREF_NAME, "Nil");
        ((TextView) findViewById(R.id.tv_prompt)).setText(
                getResources().getString(R.string.prompt_hello, name));
        findViewById(R.id.bt_begin).setOnClickListener(this);
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
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(RuleActivity.this, SigninActivity.class);
                RuleActivity.this.startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_begin:
                Intent intent = new Intent(RuleActivity.this, QuizActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
