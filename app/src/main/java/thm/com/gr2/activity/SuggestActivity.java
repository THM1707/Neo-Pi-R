package thm.com.gr2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.List;
import thm.com.gr2.R;

public class SuggestActivity extends AppCompatActivity {

    private TextView mTextA;
    private TextView mTextC;
    private TextView mTextN;
    private TextView mTextO;
    private TextView mTextE;
    private List<String> mAdviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        mAdviceList = (List<String>) getIntent().getSerializableExtra("advice");
        setupViews();
    }

    private void setupViews() {
        mTextA = findViewById(R.id.tv_advice_a);
        mTextC = findViewById(R.id.tv_advice_c);
        mTextN = findViewById(R.id.tv_advice_n);
        mTextE = findViewById(R.id.tv_advice_e);
        mTextO = findViewById(R.id.tv_advice_o);
        mTextA.setText(mAdviceList.get(3));
        mTextC.setText(mAdviceList.get(4));
        mTextO.setText(mAdviceList.get(2));
        mTextN.setText(mAdviceList.get(0));
        mTextE.setText(mAdviceList.get(1));

    }
}
