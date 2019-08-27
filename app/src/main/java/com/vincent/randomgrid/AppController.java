package com.vincent.randomgrid;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.vincent.randomgrid.utilities.DialogHelper;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class AppController extends MultiDexApplication {

    private static AppController appInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized AppController getInstance() {
        return appInstance;
    }

    public void showKeyboardAndDismissDialogWithDelay(boolean isShow, View view) {
        showKeyboardByGivenView(isShow, view);

        view.postDelayed(DialogHelper::dismissDialog, 500);
    }

    public void showKeyboardByGivenView(boolean isShow, View view) {
        final InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            if (isShow) {
                view.requestFocus();
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            else {
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
