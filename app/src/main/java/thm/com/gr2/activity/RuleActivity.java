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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thm.com.gr2.R;
import thm.com.gr2.model.Point;
import thm.com.gr2.model.PointResponse;
import thm.com.gr2.retrofit.AppServiceClient;
import thm.com.gr2.util.Constants;

public class RuleActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private TextView mTextLastResult;
    private ProgressBar mProgressRule;
    private Point mPoint;

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
        fetchPoint();
    }

    private void fetchPoint() {
        String auth = mSharedPreferences.getString(Constants.USER_PREF_AUTH, "nothing");
        AppServiceClient.getMyApiInstance(this)
                .getPoint(auth)
                .enqueue(new Callback<PointResponse>() {
                    @Override
                    public void onResponse(Call<PointResponse> call,
                            Response<PointResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("Point available")) {
                                mTextLastResult.setVisibility(View.VISIBLE);
                                mPoint = response.body().getPoint();
                            }
                        } else {
                            try {
                                JSONObject jObjError =
                                        new JSONObject(response.errorBody().string());
                                Toast.makeText(RuleActivity.this, jObjError.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(RuleActivity.this, e.getMessage(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                        mProgressRule.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<PointResponse> call, Throwable t) {
                        mProgressRule.setVisibility(View.GONE);
                        Toast.makeText(RuleActivity.this, R.string.msg_connection_timeout,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupView() {
        String name = mSharedPreferences.getString(Constants.USER_PREF_NAME, "Nil");
        ((TextView) findViewById(R.id.tv_prompt)).setText(
                getResources().getString(R.string.prompt_hello, name));
        findViewById(R.id.bt_begin).setOnClickListener(this);
        mTextLastResult = findViewById(R.id.tv_last_result);
        mTextLastResult.setOnClickListener(this);
        mProgressRule = findViewById(R.id.pr_rule);
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
            case R.id.tv_last_result:
                Map<String, Integer> pointMap = new HashMap<>();
                pointMap.put("A", mPoint.getPointA());
                pointMap.put("C", mPoint.getPointC());
                pointMap.put("O", mPoint.getPointO());
                pointMap.put("N", mPoint.getPointN());
                pointMap.put("E", mPoint.getPointE());
                Intent resultIntent = new Intent(RuleActivity.this, ResultActivity.class);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultIntent.putExtra(Constants.EXTRA_RESULT, (Serializable) pointMap);
                startActivity(resultIntent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressRule.setVisibility(View.VISIBLE);
        fetchPoint();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
