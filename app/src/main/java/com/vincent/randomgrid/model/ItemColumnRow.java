package com.vincent.randomgrid.model;

import android.util.Log;

public class ItemColumnRow {

    private int columnCount;
    private int rowCount;

    private int rowHeight;
    private int columnWidth;

    private int randomColumnIndex;
    private int randomRowIndex;

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public int getRandomColumnIndex() {
        return randomColumnIndex;
    }

    public void clearRandomColumnIndex() {
        randomColumnIndex = -1;
    }

    public int getRandomRowIndex() {
        return randomRowIndex;
    }

    public void doRandom() {
        this.randomColumnIndex = (int) (Math.random() * columnCount);
        this.randomRowIndex = (int) (Math.random() * rowCount);

        Log.i("ItemColumnRow", "RandomColumnIndex: " + randomColumnIndex + " RandomRowIndex: " + randomRowIndex);
    }
}
