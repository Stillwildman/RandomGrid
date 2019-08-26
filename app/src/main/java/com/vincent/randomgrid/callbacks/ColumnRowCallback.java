package com.vincent.randomgrid.callbacks;

public interface ColumnRowCallback {

    int getRowCount();

    boolean isInRandomColumnRow(int rowPosition);

    int getRandomRowIndex();

    int getRowHeight();

    int getRowFooterHeight();
}
