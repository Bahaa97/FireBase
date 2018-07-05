package com.task.senior.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.task.senior.Model.Item;
import com.task.senior.R;
import com.task.senior.api.Networking;
import com.task.senior.callback.OnFirebaseOperationListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.description_ET)
    EditText descriptionET;
    @BindView(R.id.price_ET)
    EditText priceET;
    @BindView(R.id.rate_ET)
    EditText rateET;
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                addItem();
                break;
        }
    }

    private void addItem() {

        progressBar.setVisibility(View.VISIBLE);

        String name = nameET.getText().toString().trim();
        String description = descriptionET.getText().toString().trim();
        double rate = Double.valueOf(rateET.getText().toString().trim());
        int price = Integer.valueOf(priceET.getText().toString().trim());

        Item item = new Item()
                .setName(name)
                .setDescription(description)
                .setRate(rate)
                .setPrice(price);

        Networking.addItem(item, new OnFirebaseOperationListener() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                finish();
            }

            @Override
            public void onFailed(String errorMessage) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
