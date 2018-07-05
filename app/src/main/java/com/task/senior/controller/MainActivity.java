package com.task.senior.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.task.senior.Model.Item;
import com.task.senior.R;
import com.task.senior.adapter.ItemAdapter;
import com.task.senior.api.Networking;
import com.task.senior.callback.OnFirebaseDataListener;
import com.task.senior.callback.OnFirebaseOperationListener;
import com.task.senior.callback.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnItemLongClickListener {

    @BindView(R.id.reycler_feeds)
    RecyclerView recyclerFeeds;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.add_fab_btn)
    FloatingActionButton addFabBtn;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private RecyclerView.LayoutManager mLayoutManager;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        addFabBtn.setOnClickListener(this);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerFeeds.setLayoutManager(mLayoutManager);
        swipeLayout.setOnRefreshListener(this);
        adapter = new ItemAdapter(MainActivity.this, new ArrayList<Item>(), this);
        recyclerFeeds.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);

        getAllItems();


    }

    private void getAllItems() {
        Networking.getAllItems(new OnFirebaseDataListener() {
            @Override
            public void onSuccess(List<Item> items) {
                adapter.update(items);
                progressBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                swipeLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_fab_btn:
                startActivity(new Intent(this, DetailActivity.class));
                break;
        }
    }


    @Override
    public void onRefresh() {
        getAllItems();
    }

    @Override
    public void onLongClicked(final Item item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setCancelable(false)
                .setMessage("Are you sure you want delete this item?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(item);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        builder.show();
    }

    private void deleteItem(Item item) {
        Networking.deleteItem(item, new OnFirebaseOperationListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(String errorMessage) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
