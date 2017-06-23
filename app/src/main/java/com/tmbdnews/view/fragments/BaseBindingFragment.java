package com.tmbdnews.view.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmbdnews.BR;
import com.tmbdnews.utils.NetworkUtils;
import com.tmbdnews.view.activities.ActMain;
import com.tmbdnews.view.dialogs.DlgProgress;
import com.tmbdnews.viewmodel.BaseInjectViewModel;

import io.reactivex.disposables.Disposable;


public abstract class BaseBindingFragment<B extends ViewDataBinding, V extends BaseInjectViewModel> extends BaseInjectFragment {
    B binding;
    V viewModel;
    private DlgProgress dlgProgress = DlgProgress.newInstance();

    protected abstract
    @LayoutRes
    int initLayout();

    protected abstract V initViewModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            int layout = initLayout();
            viewModel = initViewModel();
            if (viewModel == null) {
                throw new IllegalStateException("viewModel must not be null and should be injected via getViewModel");
            }
            if (layout == 0) {
                throw new IllegalStateException("layout must not be null and should be injected via getLayout");
            }
            binding = DataBindingUtil.inflate(inflater, layout, container, false);
            binding.setVariable(BR.vm, viewModel);
        }
        return binding.getRoot();
    }

    void onBeginResponse(Disposable disposable) {
        if (!NetworkUtils.isNetworkAvailable()) {
            showEmptyView();
            return;
        }
        if (!dlgProgress.isAdded())
            dlgProgress.show(getChildFragmentManager(), "Progress");
    }

    void onCompleteResponse() {
        if (dlgProgress.isVisible()) {
            dlgProgress.dismissAllowingStateLoss();
            dlgProgress = DlgProgress.newInstance();
        }
    }

    void handleError(Throwable error) {
        if (dlgProgress.isVisible()) {
            dlgProgress.dismissAllowingStateLoss();
            dlgProgress = DlgProgress.newInstance();
        }
    }

    void showEmptyView() {
    }

    protected ActMain getActMain() {
        return (ActMain) getActivity();
    }


}
