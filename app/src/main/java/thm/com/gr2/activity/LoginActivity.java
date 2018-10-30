package thm.com.gr2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thm.com.gr2.R;
import thm.com.gr2.model.LoginResponse;
import thm.com.gr2.retrofit.AppServiceClient;
import thm.com.gr2.util.Constants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTextEmail;
    private EditText mTextPassword;
    private ProgressBar mProgressBar;
    private SharedPreferences mSharedPreferences;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
        }
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, Context.MODE_PRIVATE);
        setupView();
    }

    private void setupView() {
        findViewById(R.id.bt_login).setOnClickListener(this);
        mTextEmail = findViewById(R.id.et_login_email);
        mTextPassword = findViewById(R.id.et_login_password);
        mProgressBar = findViewById(R.id.pr_login);
        mCheckBox = findViewById(R.id.cb_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                mProgressBar.setVisibility(View.VISIBLE);
                String email = mTextEmail.getText().toString();
                String password = mTextPassword.getText().toString();
                AppServiceClient.getMyApiInstance(this)
                        .login(email, password)
                        .enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call,
                                    Response<LoginResponse> response) {
                                mProgressBar.setVisibility(View.GONE);

                                if (!response.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, R.string.msg_login_fail,
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                                    if (mCheckBox.isChecked()) {
                                        editor.putString(Constants.USER_PREF_MAIL, email);
                                        editor.putString(Constants.USER_PREF_PASSWORD, password);
                                        editor.putInt(Constants.USER_PREF_REMEMBER, 1);
                                    }
                                    String authToken = response.body().getAuthToken();
                                    editor.putString(Constants.USER_PREF_AUTH, authToken);
                                    int gender = response.body().getUser().getGender();
                                    String name = response.body().getUser().getName();
                                    editor.putInt(Constants.USER_PREF_GENDER, gender);
                                    editor.putString(Constants.USER_PREF_NAME, name);
                                    editor.apply();
                                    Intent intent =
                                            new Intent(LoginActivity.this, RuleActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                    LoginActivity.this.finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                mProgressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, R.string.msg_connection_timeout,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            default:
                break;
        }
    }
}
