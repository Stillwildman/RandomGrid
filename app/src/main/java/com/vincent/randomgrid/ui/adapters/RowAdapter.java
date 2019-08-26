package com.vincent.randomgrid.ui.adapters;

import android.util.Pair;
import android.view.View;

import com.vincent.randomgrid.AppController;
import com.vincent.randomgrid.R;
import com.vincent.randomgrid.bases.BaseBindingRecycler;
import com.vincent.randomgrid.callbacks.ColumnRowCallback;
import com.vincent.randomgrid.databinding.InflateRowBinding;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RowAdapter extends BaseBindingRecycler<InflateRowBinding> {

    private ColumnRowCallback columnRowCallback;

    RowAdapter(ColumnRowCallback columnRowCallback) {
        this.columnRowCallback = columnRowCallback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.inflate_row;
    }

    @Override
    protected void onBindingViewHolder(RecyclerView.ViewHolder holder, InflateRowBinding bindingView, int position) {
        bindingView.getRoot().getLayoutParams().height = columnRowCallback.getRowHeight();
        bindingView.viewFooter.getLayoutParams().height = columnRowCallback.getRowFooterHeight();

        Pair<Integer, Integer> colorPair = getColorPair(position);

        bindingView.getRoot().setBackgroundColor(colorPair.first);
        bindingView.viewFooter.setBackgroundColor(colorPair.second);

        bindingView.textRandom.setVisibility(columnRowCallback.isInRandomColumnRow(position) ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onBindingViewHolder(RecyclerView.ViewHolder holder, InflateRowBinding bindingView, int position, Object payload) {
        boolean hasRandomRow = (boolean) payload;

        if (hasRandomRow) {
            bindingView.textRandom.setVisibility(position == columnRowCallback.getRandomRowIndex() ? View.VISIBLE : View.GONE);
        }
        else {
            bindingView.textRandom.setVisibility(View.GONE);
        }
    }

    void showRandomText(boolean hasRandomRow) {
        notifyItemRangeChanged(0, getItemCount(), hasRandomRow);
    }

    private Pair<Integer, Integer> getColorPair(int position) {
        int index = position;

        while (index >= 5) {
            index -= 5;
        }

        int footerColor;
        int backgroundColor;

        switch (index) {
            default:
            case 0:
                backgroundColor = R.color.md_red_100;
                footerColor = R.color.md_red_700;
                break;

            case 1:
                backgroundColor = R.color.md_green_100;
                footerColor = R.color.md_green_700;
                break;

            case 2:
                backgroundColor = R.color.md_orange_100;
                footerColor = R.color.md_orange_900;
                break;

            case 3:
                backgroundColor = R.color.md_blue_100;
                footerColor = R.color.md_blue_800;
                break;

            case 4:
                backgroundColor = R.color.md_teal_100;
                footerColor = R.color.md_teal_600;
                break;
        }

        footerColor = ContextCompat.getColor(AppController.getInstance().getApplicationContext(), footerColor);
        backgroundColor = ContextCompat.getColor(AppController.getInstance().getApplicationContext(), backgroundColor);

        return new Pair<>(backgroundColor, footerColor);
    }

    @Override
    public int getItemCount() {
        return columnRowCallback.getRowCount();
    }
}
