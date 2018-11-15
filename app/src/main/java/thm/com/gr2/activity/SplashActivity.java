package thm.com.gr2.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thm.com.gr2.R;
import thm.com.gr2.model.LoginResponse;
import thm.com.gr2.retrofit.AppServiceClient;
import thm.com.gr2.util.Constants;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPreferences = getSharedPreferences(Constants.PREF_NAME_USER, MODE_PRIVATE);
        int remember = mSharedPreferences.getInt(Constants.USER_PREF_REMEMBER, 0);
        System.out.println("Remember val: " + remember);
        if (remember == 0) {
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        } else {
            String email = mSharedPreferences.getString(Constants.USER_PREF_MAIL, "");
            String password = mSharedPreferences.getString(Constants.USER_PREF_PASSWORD, "");
            AppServiceClient.getMyApiInstance(this)
                    .login(email, password)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call,
                                Response<LoginResponse> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(SplashActivity.this, "Failed to login, please try again",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent =
                                        new Intent(SplashActivity.this, SigninActivity.class);
                                startActivity(intent);
                                SplashActivity.this.finish();
                            } else {
                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                String authToken = response.body().getAuthToken();
                                editor.putString(Constants.USER_PREF_AUTH, authToken);
                                editor.apply();
                                Intent intent = new Intent(SplashActivity.this, RuleActivity.class);
                                SplashActivity.this.startActivity(intent);
                                SplashActivity.this.finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            mSharedPreferences.edit().clear().apply();
                            Intent intent = new Intent(SplashActivity.this, SigninActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    });
        }
    }
}
