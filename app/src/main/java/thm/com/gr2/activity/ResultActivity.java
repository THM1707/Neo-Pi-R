package thm.com.gr2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import thm.com.gr2.model.Result;
import thm.com.gr2.model.ResultResponse;
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
    private TextView mTextResultA;
    private TextView mTextResultC;
    private TextView mTextResultO;
    private TextView mTextResultN;
    private TextView mTextResultE;
    private TextView mTextNameA;
    private TextView mTextNameC;
    private TextView mTextNameO;
    private TextView mTextNameN;
    private TextView mTextNameE;
    private List<Result> mResultList;
    private List<String> mAdviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, MODE_PRIVATE);
        mGender = mSharedPreferences.getInt(Constants.USER_PREF_GENDER, -1);
        mPointMap = (Map<String, Integer>) getIntent().getSerializableExtra("result");
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
                            // TODO: 17/10/2018 change toast
                            Toast.makeText(ResultActivity.this, "Something happened",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mResultList = response.body().getResults();
                            mAdviceList = new ArrayList<>();
                            for (Result r: mResultList){
                                mAdviceList.add(r.getAdvice());
                            }
                            showPoint();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        findViewById(R.id.pr_result).setVisibility(View.GONE);
                        Toast.makeText(ResultActivity.this, "Something happened",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupViews() {
        mTextA = findViewById(R.id.tv_a);
        mTextC = findViewById(R.id.tv_c);
        mTextO = findViewById(R.id.tv_o);
        mTextN = findViewById(R.id.tv_n);
        mTextE = findViewById(R.id.tv_e);
        mTextResultA = findViewById(R.id.tv_result_a);
        mTextResultC = findViewById(R.id.tv_result_c);
        mTextResultO = findViewById(R.id.tv_result_o);
        mTextResultN = findViewById(R.id.tv_result_n);
        mTextResultE = findViewById(R.id.tv_result_e);
        mTextNameA = findViewById(R.id.tv_name_a);
        mTextNameC = findViewById(R.id.tv_name_c);
        mTextNameO = findViewById(R.id.tv_name_o);
        mTextNameN = findViewById(R.id.tv_name_n);
        mTextNameE = findViewById(R.id.tv_name_e);
        findViewById(R.id.bt_suggest).setOnClickListener(this);
    }

    private void showPoint() {
        int a = mPointMap.get("A");
        int c = mPointMap.get("C");
        int o = mPointMap.get("O");
        int n = mPointMap.get("N");
        int e = mPointMap.get("E");
        mTextNameA.setText(mResultList.get(3).getName());
        mTextNameC.setText(mResultList.get(4).getName());
        mTextNameO.setText(mResultList.get(2).getName());
        mTextNameN.setText(mResultList.get(0).getName());
        mTextNameE.setText(mResultList.get(1).getName());

        if (mGender == 0) {
            if (a < 18.1) {
                mTextA.setText(R.string.prompt_low);
                mTextResultA.setText(mResultList.get(3).getLow());
            } else if (a >= 18.1 && a <= 35.4){
                mTextA.setText(R.string.prompt_medium);
                mTextResultA.setText(mResultList.get(3).getMedium());
            } else {
                mTextA.setText(R.string.prompt_high);
                mTextResultA.setText(mResultList.get(3).getHigh());
            }

            if (c < 20.3) {
                mTextC.setText(R.string.prompt_low);
                mTextResultC.setText(mResultList.get(4).getLow());
            } else if (c >= 20.3 && c <= 33.7){
                mTextC.setText(R.string.prompt_medium);
                mTextResultC.setText(mResultList.get(4).getMedium());
            } else {
                mTextC.setText(R.string.prompt_high);
                mTextResultC.setText(mResultList.get(4).getHigh());
            }

            if (o < 23.5) {
                mTextO.setText(R.string.prompt_low);
                mTextResultO.setText(mResultList.get(2).getLow());
            } else if (o >= 23.5 && o <= 33.7){
                mTextO.setText(R.string.prompt_medium);
                mTextResultO.setText(mResultList.get(2).getMedium());
            } else {
                mTextO.setText(R.string.prompt_high);
                mTextResultO.setText(mResultList.get(2).getHigh());
            }

            if (n < 19.8) {
                mTextN.setText(R.string.prompt_low);
                mTextResultN.setText(mResultList.get(0).getLow());
            } else if (n >= 19.8 && n <= 34.8){
                mTextN.setText(R.string.prompt_medium);
                mTextResultN.setText(mResultList.get(0).getMedium());
            } else {
                mTextN.setText(R.string.prompt_high);
                mTextResultN.setText(mResultList.get(0).getHigh());
            }

            if (e < 23.5) {
                mTextE.setText(R.string.prompt_low);
                mTextResultE.setText(mResultList.get(1).getLow());
            } else if (e >= 23.5 && e <= 37.8){
                mTextE.setText(R.string.prompt_medium);
                mTextResultE.setText(mResultList.get(1).getMedium());
            } else {
                mTextE.setText(R.string.prompt_high);
                mTextResultE.setText(mResultList.get(1).getHigh());
            }

        } else if (mGender == 1) {
            if (a < 19.1) {
                mTextA.setText(R.string.prompt_low);
                mTextResultA.setText(mResultList.get(3).getLow());
            } else if (a >= 19.1 && a <= 35.4){
                mTextA.setText(R.string.prompt_medium);
                mTextResultA.setText(mResultList.get(3).getMedium());
            } else {
                mTextA.setText(R.string.prompt_high);
                mTextResultA.setText(mResultList.get(3).getHigh());
            }

            if (c < 22.4) {
                mTextC.setText(R.string.prompt_low);
                mTextResultC.setText(mResultList.get(4).getLow());
            } else if (c >= 22.4 && c <= 32.5){
                mTextC.setText(R.string.prompt_medium);
                mTextResultC.setText(mResultList.get(4).getMedium());
            } else {
                mTextC.setText(R.string.prompt_high);
                mTextResultC.setText(mResultList.get(4).getHigh());
            }

            if (o < 22.4) {
                mTextO.setText(R.string.prompt_low);
                mTextResultO.setText(mResultList.get(2).getLow());
            } else if (o >= 22.4 && o <= 33.8){
                mTextO.setText(R.string.prompt_medium);
                mTextResultO.setText(mResultList.get(2).getMedium());
            } else {
                mTextO.setText(R.string.prompt_high);
                mTextResultO.setText(mResultList.get(2).getHigh());
            }

            if (n < 22.6) {
                mTextN.setText(R.string.prompt_low);
                mTextResultN.setText(mResultList.get(0).getLow());
            } else if (n >= 22.6 && n <= 38.2){
                mTextN.setText(R.string.prompt_medium);
                mTextResultN.setText(mResultList.get(0).getMedium());
            } else {
                mTextN.setText(R.string.prompt_high);
                mTextResultN.setText(mResultList.get(0).getHigh());
            }

            if (e < 25.7) {
                mTextE.setText(R.string.prompt_low);
                mTextResultE.setText(mResultList.get(1).getLow());
            } else if (e >= 25.7 && e <= 39.8){
                mTextE.setText(R.string.prompt_medium);
                mTextResultE.setText(mResultList.get(1).getMedium());
            } else {
                mTextE.setText(R.string.prompt_high);
                mTextResultE.setText(mResultList.get(1).getHigh());
            }

        }

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
}
