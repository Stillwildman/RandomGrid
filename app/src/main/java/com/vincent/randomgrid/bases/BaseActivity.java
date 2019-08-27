package com.vincent.randomgrid.bases;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.vincent.randomgrid.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void onUiHandleMessage(Message msg);

    protected abstract void onScreenSizeChanged();

    protected abstract void onMenuItemClick(int itemId);

    protected Handler uiHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initToolbar();

        init();

        Log.d(TAG, "onCreate!!!");
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    protected LinearLayoutManager getUnScrollableLayoutManager() {
        return new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onMenuItemClick(item.getItemId());
        return true;
    }

    public Handler getUiHandler() {
        if (uiHandler == null)
            uiHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    onUiHandleMessage(msg);
                }
            };
        return uiHandler;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart!!!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume!!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause!!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop!!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy!!!");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged!!! Orientation: " + newConfig.orientation);

        onScreenSizeChanged();
    }
}
