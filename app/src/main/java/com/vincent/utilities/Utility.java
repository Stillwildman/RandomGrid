package com.vincent.utilities;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.vincent.randomgrid.AppController;

import androidx.annotation.StringRes;

public class Utility {

    public static void forceCloseTask() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void toastShort(String msg) {
        Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(@StringRes int msgResId) {
        toastShort(AppController.getInstance().getApplicationContext().getString(msgResId));
    }

    public static void toastLong(String msg) {
        Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(@StringRes int msgResId) {
        toastLong(AppController.getInstance().getApplicationContext().getString(msgResId));
    }

    public static int getScreenWidth() {
        DisplayMetrics dm = AppController.getInstance().getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics dm = AppController.getInstance().getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getActionbarHeight() {
        Context mContext = AppController.getInstance().getApplicationContext();

        TypedValue tv = new TypedValue();

        int actionBarHeight = 0;
        int statusBarHeight = 0;

        if (mContext.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, mContext.getResources().getDisplayMetrics());
        }

        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
        }

        Log.i("ActionBarHeight", "ActionBarHeight: " + actionBarHeight + " StatusBarHeight: " + statusBarHeight);

        return actionBarHeight + statusBarHeight;
    }
}
