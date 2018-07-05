package com.task.senior.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.task.senior.R;
import com.task.senior.api.Networking;
import com.task.senior.callback.OnFirebaseOperationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_ET)
    EditText emailET;
    @BindView(R.id.pass_ET)
    EditText passET;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_btn:
                signUp();
                break;
        }
    }

    private void signUp() {
        String email = emailET.getText().toString().trim();
        String password = passET.getText().toString();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressBar.setVisibility(View.VISIBLE);
            Networking.signUp(email, password, new OnFirebaseOperationListener() {
                @Override
                public void onSuccess() {
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finishAffinity();
                }

                @Override
                public void onFailed(String errorMessage) {
                    Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            emailET.setError("Required!");
            emailET.requestFocus();


            YoYo.with(Techniques.BounceInUp)
                    .duration(500)
                    .playOn(emailET);

            passET.setError("Required!");
            passET.requestFocus();

            YoYo.with(Techniques.BounceInDown)
                    .duration(500)
                    .playOn(passET);


        }
    }
}
