package thm.com.gr2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thm.com.gr2.R;
import thm.com.gr2.fragment.QuizFragment;
import thm.com.gr2.model.Quiz;
import thm.com.gr2.model.QuizResponse;
import thm.com.gr2.retrofit.AppServiceClient;
import thm.com.gr2.util.Constants;

public class QuizActivity extends AppCompatActivity implements QuizFragment.OnAnswerClickListener {

    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;
    private List<Quiz> mQuizList = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Map<String, Integer> mPointMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar_quiz);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, Context.MODE_PRIVATE);
        mPointMap = new HashMap<>();
        setNewPoint();
        setupViews();
        fetchData();
    }

    private void setNewPoint() {
        mPointMap.put("C", 0);
        mPointMap.put("A", 0);
        mPointMap.put("N", 0);
        mPointMap.put("O", 0);
        mPointMap.put("E", 0);
    }

    private void setupViews() {
        mProgressBar = findViewById(R.id.pr_quiz);
        mViewPager = findViewById(R.id.vp_quiz);
        mPagerAdapter = new QuizPagerAdapter(getSupportFragmentManager(), mQuizList);
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onAnswerClicked(String type, int point, int index) {
        if (index < mQuizList.size() - 1) {
            int cur = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(cur + 1);
            savePoint(type, point);
            System.out.println(mPointMap.toString());
        } else if (index == mQuizList.size() - 1) {
            savePoint(type, point);
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra(Constants.EXTRA_RESULT, (Serializable) mPointMap);
            QuizActivity.this.startActivity(intent);
            QuizActivity.this.finish();
        }
    }

    public void fetchData() {
        String auth = mSharedPreferences.getString(Constants.USER_PREF_AUTH, "nothing");
        AppServiceClient.getMyApiInstance(this)
                .getQuizzes(auth)
                .enqueue(new Callback<QuizResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<QuizResponse> call,
                            @NonNull Response<QuizResponse> response) {
                        mProgressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mQuizList = response.body().getQuizzes();
                                ((QuizPagerAdapter) mPagerAdapter).setQuizList(mQuizList);
                            }
                        } else {
                            Toast.makeText(QuizActivity.this, R.string.msg_unknow_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuizResponse> call, Throwable t) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(QuizActivity.this, R.string.msg_connection_timeout,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class QuizPagerAdapter extends FragmentStatePagerAdapter {

        private List<Quiz> mQuizList;

        QuizPagerAdapter(FragmentManager fm, List<Quiz> quizList) {
            super(fm);
            mQuizList = quizList;
        }

        public void setQuizList(List<Quiz> quizList) {
            mQuizList = quizList;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return QuizFragment.newInstance(mQuizList.get(position), position);
        }

        @Override
        public int getCount() {
            return mQuizList.size();
        }
    }

    public void savePoint(String type, int point) {
        int curPoint = 0;
        switch (type) {
            case "C":
                curPoint = mPointMap.get("C");
                mPointMap.put("C", curPoint + point);
                break;
            case "A":
                curPoint = mPointMap.get("A");
                mPointMap.put("A", curPoint + point);
                break;
            case "N":
                curPoint = mPointMap.get("N");
                mPointMap.put("N", curPoint + point);
                break;
            case "O":
                curPoint = mPointMap.get("O");
                mPointMap.put("O", curPoint + point);
                break;
            case "E":
                curPoint = mPointMap.get("E");
                mPointMap.put("E", curPoint + point);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.item_refresh:
                fetchData();
                setNewPoint();
                mViewPager.setCurrentItem(0);
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlert() {
        // TODO: 27/10/2018  
    }
}
