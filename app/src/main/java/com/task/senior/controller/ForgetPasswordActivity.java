package com.task.senior.controller;

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

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_ET)
    EditText emailET;
    @BindView(R.id.reset_btn)
    Button resetBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        resetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reset_btn:
                resetPassword();
                break;
        }
    }

    private void resetPassword() {
        String email = emailET.getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            progressBar.setVisibility(View.VISIBLE);
            Networking.resetPassword(email, new OnFirebaseOperationListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(ForgetPasswordActivity.this, "Check Your Email!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailed(String errorMessage) {
                    Toast.makeText(ForgetPasswordActivity.this, errorMessage
                            , Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            emailET.setError("Required!");
            emailET.requestFocus();

            YoYo.with(Techniques.Shake)
                    .duration(500)
                    .playOn(emailET);
        }
    }
}
