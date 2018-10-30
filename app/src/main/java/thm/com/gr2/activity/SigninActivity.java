package thm.com.gr2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import thm.com.gr2.R;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.bt_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    private void startActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        this.startActivity(intent);
    }
}
