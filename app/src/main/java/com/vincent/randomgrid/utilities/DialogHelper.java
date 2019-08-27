package com.vincent.randomgrid.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.vincent.randomgrid.AppController;
import com.vincent.randomgrid.R;
import com.vincent.randomgrid.databinding.InflateInputDialogBinding;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

public class DialogHelper {

    private static AlertDialog dialog;

    private static void showDialog(AlertDialog.Builder dialogBuilder, boolean customizedBackground, final float widthProportion) {
        closeDialogIfIsShowing();

        dialog = dialogBuilder.create();

        if (widthProportion != 0 && widthProportion != 1) {
            dialog.setOnShowListener((DialogInterface dialog) ->
            {
                if (DialogHelper.dialog != null) {
                    Window window = DialogHelper.dialog.getWindow();

                    if (window != null) {
                        int width = (int) (Utility.getScreenWidth() * widthProportion);
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.width = width;

                        window.setAttributes(params);
                    }

                    DialogHelper.dialog.setOnShowListener(null);
                }
            });
        }

        if (customizedBackground && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
    }

    private static void closeDialogIfIsShowing() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.setOnShowListener(null);
            dialog.dismiss();
            dialog = null;
        }
    }

    public static InflateInputDialogBinding getInputDialogBindingView(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final InflateInputDialogBinding bindingView = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.inflate_input_dialog, null, false);

        builder.setView(bindingView.getRoot());
        builder.setCancelable(true);
        builder.setOnDismissListener(dialog -> AppController.getInstance().showKeyboardAndDismissDialogWithDelay(false, bindingView.getRoot()));

        showDialog(builder, true, 0.9f);

        return bindingView;
    }
}
