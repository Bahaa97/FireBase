package com.task.senior.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.senior.R;
import com.task.senior.api.FirebaseHelper;
import com.task.senior.api.Networking;
import com.task.senior.callback.OnFirebaseOperationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_ET)
    EditText emailET;
    @BindView(R.id.pass_ET)
    EditText passET;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;
    @BindView(R.id.forget_pass_TV)
    TextView forgetPassTV;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseHelper.getAuth().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        forgetPassTV.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;

            case R.id.sign_up_btn:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.forget_pass_TV:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    private void login() {
        String email = emailET.getText().toString().trim();
        String password = passET.getText().toString();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.VISIBLE);
            Networking.login(email, password, new OnFirebaseOperationListener() {
                @Override
                public void onSuccess() {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailed(String errorMessage) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Fill", Toast.LENGTH_SHORT).show();
        }
    }
}
