package thm.com.gr2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import thm.com.gr2.R;
import thm.com.gr2.fragment.QuizFragment;
import thm.com.gr2.model.Quiz;
import thm.com.gr2.util.Constants;
import thm.com.gr2.widget.ZoomOutPageTransformer;

public class QuizActivity extends AppCompatActivity implements QuizFragment.OnAnswerClickListener {

    private SharedPreferences mSharedPreferences;
    private List<Quiz> mQuizList;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private Map<String, Integer> mPointMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, Context.MODE_PRIVATE);
        mQuizList = (List<Quiz>) getIntent().getSerializableExtra("list");
        mPointMap = new HashMap<>();
        mPointMap.put("C", 0);
        mPointMap.put("A", 0);
        mPointMap.put("N", 0);
        mPointMap.put("O", 0);
        mPointMap.put("E", 0);
        setupViews();
    }

    private void setupViews() {
        mViewPager = findViewById(R.id.vp_quiz);
        mPagerAdapter = new QuizPagerAdapter(getSupportFragmentManager(), mQuizList);
        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
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
            // TODO: 17/10/2018 change this
            intent.putExtra("result", (Serializable) mPointMap);
            QuizActivity.this.startActivity(intent);
            QuizActivity.this.finish();
        }
    }

    private class QuizPagerAdapter extends FragmentStatePagerAdapter {

        private List<Quiz> mQuizList;

        QuizPagerAdapter(FragmentManager fm, List<Quiz> quizList) {
            super(fm);
            mQuizList = quizList;
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
}
