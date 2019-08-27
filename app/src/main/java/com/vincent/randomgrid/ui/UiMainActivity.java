package com.vincent.randomgrid.ui;

import android.os.Message;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vincent.randomgrid.AppController;
import com.vincent.randomgrid.R;
import com.vincent.randomgrid.bases.BaseActivity;
import com.vincent.randomgrid.databinding.InflateInputDialogBinding;
import com.vincent.randomgrid.ui.adapters.ColumnRowAdapter;
import com.vincent.randomgrid.utilities.DialogHelper;
import com.vincent.randomgrid.utilities.Utility;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class UiMainActivity extends BaseActivity {

    private RecyclerView recycler;

    private Runnable run_random;

    private static final int DURATION_RANDOMIZATION = 1000 * 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInputDialogIfNeeded();
        restartAutoRandomization();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoRandomization();
    }

    @Override
    protected void init() {
        initRecycler();
    }

    @Override
    protected void onUiHandleMessage(Message msg) {

    }

    private void initRecycler() {
        recycler = findViewById(R.id.recycler_columnRow);
        recycler.setLayoutManager(getUnScrollableLayoutManager());
        recycler.setNestedScrollingEnabled(false);
        recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
        recycler.setAdapter(new ColumnRowAdapter());
    }

    private ColumnRowAdapter getColumnRowAdapter() {
        return (ColumnRowAdapter) recycler.getAdapter();
    }

    private boolean isAdapterNotNullAndNotEmpty() {
        return getColumnRowAdapter() != null && !getColumnRowAdapter().isEmpty();
    }

    private void showInputDialogIfNeeded() {
        if (getColumnRowAdapter() != null && getColumnRowAdapter().isEmpty()) {
            getUiHandler().postDelayed(this::showInputDialog, 500);
        }
    }

    private void showInputDialog() {
        final InflateInputDialogBinding bindingView = DialogHelper.getInputDialogBindingView(this);

        if (isAdapterNotNullAndNotEmpty()) {
            bindingView.editColumn.setText(String.valueOf(getColumnRowAdapter().getItemCount()));
            bindingView.editRow.setText(String.valueOf(getColumnRowAdapter().getRowCount()));
        }

        getUiHandler().postDelayed(() -> {
            bindingView.editColumn.requestFocus();
            bindingView.editColumn.setSelection(bindingView.editColumn.length());
            AppController.getInstance().showKeyboardByGivenView(true, bindingView.editColumn);
        }, 600);

        bindingView.buttonDone.setOnClickListener(view -> setColumnAndRow(bindingView.editColumn, bindingView.editRow));
    }

    private void setColumnAndRow(EditText editColumn, EditText editRow) {
        if (editColumn.length() == 0) {
            editColumn.requestFocus();
            AppController.getInstance().showKeyboardByGivenView(true, editColumn);
            return;
        }

        if (editRow.length() == 0) {
            editRow.requestFocus();
            AppController.getInstance().showKeyboardByGivenView(true, editRow);
            return;
        }

        int column = Integer.parseInt(editColumn.getText().toString());
        int row = Integer.parseInt(editRow.getText().toString());

        if (column == 0) {
            Utility.toastShort(R.string.column_must_be_bigger_than_zero);
            return;
        }

        if (row == 0) {
            Utility.toastShort(R.string.row_must_be_bigger_than_zero);
            return;
        }

        AppController.getInstance().showKeyboardByGivenView(false, editColumn);

        DialogHelper.dismissDialog();

        getColumnRowAdapter().setColumnRowCount(column, row);

        if (run_random == null) {
            startAutoRandomization();
        }
    }

    @Override
    protected void onScreenSizeChanged() {
        if (isAdapterNotNullAndNotEmpty()) {
            getColumnRowAdapter().setColumnRowCount(getColumnRowAdapter().getItemCount(), getColumnRowAdapter().getRowCount());
        }
        DialogHelper.dismissDialog();
    }

    private void restartAutoRandomization() {
        if (isAdapterNotNullAndNotEmpty()) {
            startAutoRandomization();
        }
    }

    private void startAutoRandomization() {
        if (run_random == null) {
            run_random = new Runnable() {
                @Override
                public void run() {
                    getColumnRowAdapter().setRandomIndex();
                    getUiHandler().postDelayed(this, DURATION_RANDOMIZATION);
                }
            };
        }
        getUiHandler().postDelayed(run_random, DURATION_RANDOMIZATION);
    }

    private void stopAutoRandomization() {
        if (run_random != null) {
            getUiHandler().removeCallbacks(run_random);
        }
    }

    @Override
    protected void onMenuItemClick(int itemId) {
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_input:
                showInputDialog();
                break;
        }
    }
}
