package thm.com.gr2.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thm.com.gr2.R;
import thm.com.gr2.model.RegisterResponse;
import thm.com.gr2.retrofit.AppServiceClient;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTextEmail;
    private EditText mTextPassword;
    private EditText mTextName;
    private EditText mTextConfirm;
    private RadioButton mRadioMale;
    private RadioButton mRadioFemale;
    private RadioGroup mRadioGroup;
    private SharedPreferences mSharedPreferences;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar_register);
        setSupportActionBar(toolbar);
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        setupView();
    }

    private void setupView() {
        findViewById(R.id.bt_register).setOnClickListener(this);
        mTextEmail = findViewById(R.id.et_res_email);
        mTextConfirm = findViewById(R.id.et_res_re_password);
        mTextPassword = findViewById(R.id.et_res_password);
        mTextName = findViewById(R.id.et_res_name);
        mRadioFemale = findViewById(R.id.rb_female);
        mRadioMale = findViewById(R.id.rb_male);
        mRadioGroup = findViewById(R.id.rg_gender);
        mProgressBar = findViewById(R.id.pr_register);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:
                mProgressBar.setVisibility(View.VISIBLE);
                if (mRadioGroup.getCheckedRadioButtonId() == -1) {
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Need to chose your gender", Toast.LENGTH_SHORT).show();
                } else {
                    String name = mTextName.getText().toString();
                    String email = mTextEmail.getText().toString();
                    String password = mTextPassword.getText().toString();
                    String password_confirm = mTextConfirm.getText().toString();
                    int gender =
                            mRadioGroup.getCheckedRadioButtonId() == mRadioMale.getId() ? 0 : 1;

                    AppServiceClient.getMyApiInstance(this)
                            .signup(name, email, password, password_confirm, gender)
                            .enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call,
                                        Response<RegisterResponse> response) {
                                    mProgressBar.setVisibility(View.GONE);
                                    if (response.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,
                                                R.string.msg_create_success, Toast.LENGTH_SHORT)
                                                .show();
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this,
                                                R.string.msg_cant_create, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                                    mProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, R.string.msg_try_again,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }
}
