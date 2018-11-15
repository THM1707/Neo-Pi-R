package thm.com.gr2.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;
    private List<Quiz> mQuizList = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Map<String, Integer> mPointMap;
    private int currentPosition = 0;
    private ProgressBar mProgressQuiz;

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
        CardView cardZero = findViewById(R.id.cv_zero);
        CardView cardOne = findViewById(R.id.cv_one);
        CardView cardTwo = findViewById(R.id.cv_two);
        CardView cardThree = findViewById(R.id.cv_three);
        CardView cardFour = findViewById(R.id.cv_four);
        cardZero.setOnClickListener(this);
        cardOne.setOnClickListener(this);
        cardTwo.setOnClickListener(this);
        cardThree.setOnClickListener(this);
        cardFour.setOnClickListener(this);
        mProgressQuiz = findViewById(R.id.pr_tab);
    }

    public void onAnswerClicked(String type, int point) {
        if (currentPosition < mQuizList.size() - 1) {
            mViewPager.setCurrentItem(currentPosition + 1);
            mProgressQuiz.setProgress(currentPosition + 2);
            savePoint(type, point);
            currentPosition++;
            System.out.println(mPointMap.toString());
        } else if (currentPosition == mQuizList.size() - 1) {
            savePoint(type, point);
            String auth = mSharedPreferences.getString(Constants.USER_PREF_AUTH, "nothing");
            AppServiceClient.getMyApiInstance(this)
                    .savePoint(auth, mPointMap.get("A"), mPointMap.get("C"), mPointMap.get("O"),
                            mPointMap.get("N"), mPointMap.get("E"))
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
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
                                mProgressQuiz.setProgress(currentPosition + 1);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_zero:
                Quiz currentQuiz = mQuizList.get(currentPosition);
                onAnswerClicked(currentQuiz.getQuizType(), getPoint(currentQuiz.getMode(), 0));
                break;
            case R.id.cv_one:
                currentQuiz = mQuizList.get(currentPosition);
                onAnswerClicked(currentQuiz.getQuizType(), getPoint(currentQuiz.getMode(), 1));
                break;
            case R.id.cv_two:
                currentQuiz = mQuizList.get(currentPosition);
                onAnswerClicked(currentQuiz.getQuizType(), getPoint(currentQuiz.getMode(), 2));
                break;
            case R.id.cv_three:
                currentQuiz = mQuizList.get(currentPosition);
                onAnswerClicked(currentQuiz.getQuizType(), getPoint(currentQuiz.getMode(), 3));
                break;
            case R.id.cv_four:
                currentQuiz = mQuizList.get(currentPosition);
                onAnswerClicked(currentQuiz.getQuizType(), getPoint(currentQuiz.getMode(), 4));
                break;
            default:
                break;
        }
    }

    private class QuizPagerAdapter extends FragmentStatePagerAdapter {

        private List<Quiz> mQuizList;

        QuizPagerAdapter(FragmentManager fm, List<Quiz> quizList) {
            super(fm);
            mQuizList = quizList;
        }

        void setQuizList(List<Quiz> quizList) {
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
        int curPoint;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.msg_logout)
                        .setTitle(R.string.msg_logout_title)
                        .setPositiveButton(R.string.action_ok, (anInterface, i) -> {
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(this, SigninActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .setNegativeButton(R.string.action_cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.item_refresh:
                AlertDialog.Builder rBuilder = new AlertDialog.Builder(this);
                rBuilder.setMessage(R.string.msg_logout)
                        .setTitle(R.string.msg_logout_title)
                        .setPositiveButton(R.string.action_ok, (anInterface, i) -> {
                            fetchData();
                            setNewPoint();
                            currentPosition = 0;
                            mProgressQuiz.setProgress(1);
                            mViewPager.setCurrentItem(0);
                        })
                        .setNegativeButton(R.string.action_cancel, null);
                AlertDialog rDialog = rBuilder.create();
                rDialog.show();

                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.msg_logout)
                .setTitle(R.string.msg_logout_title)
                .setPositiveButton(R.string.action_ok, (anInterface, i) -> {
                    super.onBackPressed();
                    finish();
                })
                .setNegativeButton(R.string.action_cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getPoint(int mode, int answerNo) {
        switch (answerNo) {
            case 0:
                return mode == 0 ? 0 : 4;
            case 1:
                return mode == 0 ? 1 : 3;
            case 2:
                return 2;
            case 3:
                return mode == 0 ? 3 : 1;
            case 4:
                return mode == 0 ? 4 : 0;
            default:
                return -1;
        }
    }
}
