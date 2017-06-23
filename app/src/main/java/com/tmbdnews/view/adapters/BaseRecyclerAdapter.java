package com.tmbdnews.view.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmbdnews.BR;
import com.tmbdnews.viewmodel.BaseViewModel;

import java.util.List;

abstract class BaseRecyclerAdapter<B extends ViewDataBinding, V extends BaseViewModel, H extends BaseRecyclerAdapter.BaseViewHolder, T> extends RecyclerView.Adapter<H> {
    final List<T> item;
    V viewModel;
    B binding;

    BaseRecyclerAdapter(List<T> item) {
        this.item = item;
    }

    protected abstract
    @LayoutRes
    int initLayout();

    protected abstract V initViewModel();

    protected abstract H initHolder();

    public List<T> getItem() {
        return item;
    }

    @Override
    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), initLayout(), parent, false);
        viewModel = initViewModel();
        H holder = initHolder();
        binding.setVariable(BR.vm, viewModel);
        return holder;
    }


    class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
            DataBindingUtil.bind(itemView);
        }
    }

}