package thm.com.gr2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thm.com.gr2.R;
import thm.com.gr2.adapter.ResultRecyclerAdapter;
import thm.com.gr2.model.Explain;
import thm.com.gr2.model.Result;
import thm.com.gr2.model.ResultResponse;
import thm.com.gr2.model.Suggest;
import thm.com.gr2.retrofit.AppServiceClient;
import thm.com.gr2.util.Constants;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Map<String, Integer> mPointMap;
    private SharedPreferences mSharedPreferences;
    private int mGender;
    private TextView mTextA;
    private TextView mTextC;
    private TextView mTextO;
    private TextView mTextN;
    private TextView mTextE;
    private List<Result> mResultList;
    private List<Suggest> mAdviceList;
    private List<Explain> mExplainList;
    private ResultRecyclerAdapter mAdapter;
    private RecyclerView mRecycleResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar_result);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, MODE_PRIVATE);
        mGender = mSharedPreferences.getInt(Constants.USER_PREF_GENDER, -1);
        mPointMap = (Map<String, Integer>) getIntent().getSerializableExtra(Constants.EXTRA_RESULT);
        mExplainList = new ArrayList<>();

        setupViews();
        String auth = mSharedPreferences.getString(Constants.USER_PREF_AUTH, "nothing");
        AppServiceClient.getMyApiInstance(this)
                .getResults(auth)
                .enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call,
                            Response<ResultResponse> response) {
                        findViewById(R.id.pr_result).setVisibility(View.GONE);
                        if (!response.isSuccessful()) {
                            Toast.makeText(ResultActivity.this, R.string.msg_unknow_error,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mResultList = response.body().getResults();
                            mAdviceList = new ArrayList<>();
                            for (Result r : mResultList) {
                                mAdviceList.add(new Suggest(r.getName(), r.getAdvice()));
                            }
                            showPoint();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        findViewById(R.id.pr_result).setVisibility(View.GONE);
                        Toast.makeText(ResultActivity.this, R.string.msg_connection_timeout,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupViews() {
        mRecycleResult = findViewById(R.id.rv_result);
        mRecycleResult.setHasFixedSize(true);
        mRecycleResult.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ResultRecyclerAdapter(mExplainList);
        mRecycleResult.setAdapter(mAdapter);
        mTextA = findViewById(R.id.tv_a);
        mTextC = findViewById(R.id.tv_c);
        mTextO = findViewById(R.id.tv_o);
        mTextN = findViewById(R.id.tv_n);
        mTextE = findViewById(R.id.tv_e);
        findViewById(R.id.bt_suggest).setOnClickListener(this);
    }

    private void showPoint() {
        Explain explainA = new Explain();
        Explain explainC = new Explain();
        Explain explainO = new Explain();
        Explain explainN = new Explain();
        Explain explainE = new Explain();
        int a = mPointMap.get("A");
        int c = mPointMap.get("C");
        int o = mPointMap.get("O");
        int n = mPointMap.get("N");
        int e = mPointMap.get("E");
        explainA.setName(mResultList.get(3).getName());
        explainC.setName(mResultList.get(4).getName());
        explainO.setName(mResultList.get(2).getName());
        explainN.setName(mResultList.get(0).getName());
        explainE.setName(mResultList.get(1).getName());

        if (mGender == 0) {
            if (a < 18.1) {
                mTextA.setText(R.string.prompt_low);
                explainA.setContent(mResultList.get(3).getLow());
            } else if (a >= 18.1 && a <= 35.4) {
                mTextA.setText(R.string.prompt_medium);
                explainA.setContent(mResultList.get(3).getMedium());
            } else {
                mTextA.setText(R.string.prompt_high);
                explainA.setContent(mResultList.get(3).getHigh());
            }

            if (c < 20.3) {
                mTextC.setText(R.string.prompt_low);
                explainC.setContent(mResultList.get(4).getLow());
            } else if (c >= 20.3 && c <= 33.7) {
                mTextC.setText(R.string.prompt_medium);
                explainC.setContent(mResultList.get(4).getMedium());
            } else {
                mTextC.setText(R.string.prompt_high);
                explainC.setContent(mResultList.get(4).getHigh());
            }

            if (o < 23.5) {
                mTextO.setText(R.string.prompt_low);
                explainO.setContent(mResultList.get(2).getLow());
            } else if (o >= 23.5 && o <= 33.7) {
                mTextO.setText(R.string.prompt_medium);
                explainO.setContent(mResultList.get(2).getMedium());
            } else {
                mTextO.setText(R.string.prompt_high);
                explainO.setContent(mResultList.get(2).getHigh());
            }

            if (n < 19.8) {
                mTextN.setText(R.string.prompt_low);
                explainN.setContent(mResultList.get(0).getLow());
            } else if (n >= 19.8 && n <= 34.8) {
                mTextN.setText(R.string.prompt_medium);
                explainN.setContent(mResultList.get(0).getMedium());
            } else {
                mTextN.setText(R.string.prompt_high);
                explainN.setContent(mResultList.get(0).getHigh());
            }

            if (e < 23.5) {
                mTextE.setText(R.string.prompt_low);
                explainE.setContent(mResultList.get(1).getLow());
            } else if (e >= 23.5 && e <= 37.8) {
                mTextE.setText(R.string.prompt_medium);
                explainE.setContent(mResultList.get(1).getMedium());
            } else {
                mTextE.setText(R.string.prompt_high);
                explainE.setContent(mResultList.get(1).getHigh());
            }
        } else if (mGender == 1) {
            if (a < 19.1) {
                mTextA.setText(R.string.prompt_low);
                explainA.setContent(mResultList.get(3).getLow());
            } else if (a >= 19.1 && a <= 35.4) {
                mTextA.setText(R.string.prompt_medium);
                explainA.setContent(mResultList.get(3).getMedium());
            } else {
                mTextA.setText(R.string.prompt_high);
                explainA.setContent(mResultList.get(3).getHigh());
            }

            if (c < 22.4) {
                mTextC.setText(R.string.prompt_low);
                explainC.setContent(mResultList.get(4).getLow());
            } else if (c >= 22.4 && c <= 32.5) {
                mTextC.setText(R.string.prompt_medium);
                explainC.setContent(mResultList.get(4).getMedium());
            } else {
                mTextC.setText(R.string.prompt_high);
                explainC.setContent(mResultList.get(4).getHigh());
            }

            if (o < 22.4) {
                mTextO.setText(R.string.prompt_low);
                explainO.setContent(mResultList.get(2).getLow());
            } else if (o >= 22.4 && o <= 33.8) {
                mTextO.setText(R.string.prompt_medium);
                explainO.setContent(mResultList.get(2).getMedium());
            } else {
                mTextO.setText(R.string.prompt_high);
                explainO.setContent(mResultList.get(2).getHigh());
            }

            if (n < 22.6) {
                mTextN.setText(R.string.prompt_low);
                explainN.setContent(mResultList.get(0).getLow());
            } else if (n >= 22.6 && n <= 38.2) {
                mTextN.setText(R.string.prompt_medium);
                explainN.setContent(mResultList.get(0).getMedium());
            } else {
                mTextN.setText(R.string.prompt_high);
                explainN.setContent(mResultList.get(0).getHigh());
            }

            if (e < 25.7) {
                mTextE.setText(R.string.prompt_low);
                explainE.setContent(mResultList.get(1).getLow());
            } else if (e >= 25.7 && e <= 39.8) {
                mTextE.setText(R.string.prompt_medium);
                explainE.setContent(mResultList.get(1).getMedium());
            } else {
                mTextE.setText(R.string.prompt_high);
                explainE.setContent(mResultList.get(1).getHigh());
            }
        }
        mExplainList.add(explainA);
        mExplainList.add(explainC);
        mExplainList.add(explainO);
        mExplainList.add(explainN);
        mExplainList.add(explainE);
        mAdapter.setExplainList(mExplainList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_suggest:
                Intent intent = new Intent(ResultActivity.this, SuggestActivity.class);
                intent.putExtra("advice", (Serializable) mAdviceList);
                ResultActivity.this.startActivity(intent);
                break;
        }
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
                Intent intent = new Intent(ResultActivity.this, SigninActivity.class);
                ResultActivity.this.startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}