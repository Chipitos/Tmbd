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
import com.tmbdnews.annotations.Layout;
import com.tmbdnews.model.ConfigAndTopRated;
import com.tmbdnews.server.response.BaseResponse;
import com.tmbdnews.server.response.ResponseGetConfig;
import com.tmbdnews.server.response.ResponseGetTopRated;
import com.tmbdnews.view.activities.ActMain;
import com.tmbdnews.view.dialogs.DlgProgress;
import com.tmbdnews.viewmodel.BaseInjectViewModel;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public abstract class BaseBindingFragment<B extends ViewDataBinding, V extends BaseInjectViewModel> extends BaseInjectFragment {
    B binding;
    V viewModel;
    private DlgProgress dlgProgress = DlgProgress.newInstance();
    protected CompositeDisposable compositeDisposable;

    protected abstract V initViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            int layout = getClass().getAnnotation(Layout.class).value();
            viewModel = initViewModel();
            if (viewModel == null) {
                throw new IllegalStateException("viewModel must not be null and should be injected via getViewModel");
            }
            if (layout == 0) {
                throw new IllegalStateException("layout must not be null and should be described on @Layout annotation");
            }
            binding = DataBindingUtil.inflate(inflater, layout, container, false);
            binding.setVariable(BR.vm, viewModel);
        }
        return binding.getRoot();
    }

    void onBeginResponse(Disposable disposable) {
        if (!networkUtils.isNetworkAvailable()) {
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

    protected <T extends BaseResponse> void createRequest(Observable<T> baseResponseObservable, Consumer<T> consumerResponse, boolean isNeedToShowProgress) {
        if (isNeedToShowProgress)
            compositeDisposable.add(baseResponseObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(this::onBeginResponse)
                    .subscribe(consumerResponse, this::handleError, this::onCompleteResponse));
        else
            compositeDisposable.add(baseResponseObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumerResponse, this::handleError));
    }

}
