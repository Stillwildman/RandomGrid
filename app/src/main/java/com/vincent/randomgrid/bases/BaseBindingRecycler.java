package com.vincent.randomgrid.bases;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


public abstract class BaseBindingRecycler<T extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG = getClass().getSimpleName();

    protected abstract int getLayoutId();

    protected abstract void onBindingViewHolder(RecyclerView.ViewHolder holder, T bindingView, int position);

    protected abstract void onBindingViewHolder(RecyclerView.ViewHolder holder, T bindingView, int position, Object payload);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        T binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new BindingViewHolder(binding);
    }

    protected class BindingViewHolder extends RecyclerView.ViewHolder {

        public final T bindingView;

        public BindingViewHolder(T bindingView) {
            super(bindingView.getRoot());
            this.bindingView = bindingView;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindingViewHolder(holder, ((BindingViewHolder) holder).bindingView, position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        }
        else {
            onBindingViewHolder(holder, ((BindingViewHolder) holder).bindingView, position, payloads.get(0));
        }
    }
}
