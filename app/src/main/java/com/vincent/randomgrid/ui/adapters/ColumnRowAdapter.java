package com.vincent.randomgrid.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vincent.randomgrid.AppController;
import com.vincent.randomgrid.R;
import com.vincent.randomgrid.bases.BaseBindingRecycler;
import com.vincent.randomgrid.callbacks.ColumnRowCallback;
import com.vincent.randomgrid.databinding.InflateColumnRowBinding;
import com.vincent.randomgrid.model.ItemColumnRow;
import com.vincent.utilities.Utility;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ColumnRowAdapter extends BaseBindingRecycler<InflateColumnRowBinding> implements View.OnClickListener {

    private final ItemColumnRow item;

    public ColumnRowAdapter() {
        this.item = new ItemColumnRow();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.inflate_column_row;
    }

    @NonNull
    @Override
    public ColumnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InflateColumnRowBinding bindingView = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new ColumnViewHolder(bindingView);
    }

    class ColumnViewHolder extends BindingViewHolder {

        private InflateColumnRowBinding bindingView;

        ColumnViewHolder(InflateColumnRowBinding bindingView) {
            super(bindingView);
            this.bindingView = bindingView;

            initRowRecycler();
        }

        private void initRowRecycler() {
            if (bindingView.recyclerRow.getLayoutManager() == null || !bindingView.recyclerRow.getLayoutManager().isAttachedToWindow()) {
                bindingView.recyclerRow.setLayoutManager(getUnScrollableLayoutManager());
                bindingView.recyclerRow.addItemDecoration(new DividerItemDecoration(bindingView.getRoot().getContext(), LinearLayout.VERTICAL));
            }
        }

        private LinearLayoutManager getUnScrollableLayoutManager() {
            return new LinearLayoutManager(bindingView.getRoot().getContext()) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
        }

        private void setColumnWidth() {
            this.bindingView.getRoot().getLayoutParams().width = item.getColumnWidth();
        }

        private void setColumnRowCallback(ColumnRowCallback callback) {
            bindingView.recyclerRow.setAdapter(new RowAdapter(callback));
        }

        private void setHighlight(int position) {
            boolean isHighlight = position == item.getRandomColumnIndex();

            bindingView.getRoot().setBackgroundResource(isHighlight ? R.drawable.background_highlight : 0);

            if (bindingView.recyclerRow.getAdapter() != null) {
                ((RowAdapter) bindingView.recyclerRow.getAdapter()).showRandomText(isHighlight);
            }

            bindingView.buttonConfirm.setSelected(isHighlight);
        }
    }

    @Override
    protected void onBindingViewHolder(RecyclerView.ViewHolder holder, InflateColumnRowBinding bindingView, int position) {
        bindingView.buttonConfirm.setTag(position);
        bindingView.buttonConfirm.setOnClickListener(this);
        ((ColumnViewHolder) holder).setColumnWidth();

        final int finalPosition = position;

        ((ColumnViewHolder) holder).setColumnRowCallback(new ColumnRowCallback() {
            @Override
            public int getRowCount() {
                return item.getRowCount();
            }

            @Override
            public boolean isInRandomColumnRow(int rowPosition) {
                if (finalPosition == item.getRandomColumnIndex()) {
                    return rowPosition == getRandomRowIndex();
                }
                return false;
            }

            @Override
            public int getRandomRowIndex() {
                return item.getRandomRowIndex();
            }

            @Override
            public int getRowHeight() {
                return item.getRowHeight();
            }

            @Override
            public int getRowFooterHeight() {
                return getRowHeight() / 15;
            }
        });

        ((ColumnViewHolder) holder).setHighlight(position);
    }

    @Override
    protected void onBindingViewHolder(RecyclerView.ViewHolder holder, InflateColumnRowBinding bindingView, int position, Object payload) {
        if (payload instanceof Boolean) {
            ((ColumnViewHolder) holder).setHighlight(position);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_confirm) {
            clearHighlight((int) v.getTag());
        }
    }

    private void clearHighlight(int columnPosition) {
        if (columnPosition == item.getRandomColumnIndex()) {
            item.clearRandomColumnIndex();
            notifyItemChanged(columnPosition, true);
        }
    }

    public void setColumnRowCount(int columnCount, int rowCount) {
        int lastColumnCount = item.getColumnCount();

        item.setColumnCount(columnCount);
        item.setRowCount(rowCount);

        setColumnRowSize();

        if (columnCount > lastColumnCount) {
            notifyItemRangeInserted(lastColumnCount, columnCount - lastColumnCount);
        }
        else if (columnCount < lastColumnCount) {
            notifyItemRangeRemoved(lastColumnCount, lastColumnCount - columnCount);
        }

        notifyItemRangeChanged(0, getItemCount());

        setRandomIndex();
    }

    private void setColumnRowSize() {
        int buttonHeight = AppController.getInstance().getResources().getDimensionPixelSize(R.dimen.actionbar_height_little_higher);
        int listHeight = Utility.getScreenHeight() - Utility.getActionbarHeight() - buttonHeight;

        int rowHeight = (int) ((listHeight / item.getRowCount()) * 0.96);
        int columnWidth = Utility.getScreenWidth() / item.getColumnCount();

        item.setRowHeight(rowHeight);
        item.setColumnWidth(columnWidth);

        Log.i(TAG, "ListHeight: " + listHeight + " RowHeight: " + item.getRowHeight() + " ColumnWidth: " + columnWidth);
    }

    public void setRandomIndex() {
        item.doRandom();
        notifyItemRangeChanged(0, getItemCount(), true);
    }

    public boolean isEmpty() {
        return item.getColumnCount() == 0;
    }

    public int getRowCount() {
        return item.getRowCount();
    }

    @Override
    public int getItemCount() {
        return item.getColumnCount();
    }
}

