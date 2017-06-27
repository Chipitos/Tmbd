package com.tmbdnews.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tmbdnews.App;
import com.tmbdnews.R;
import com.tmbdnews.annotations.Layout;
import com.tmbdnews.databinding.FrgFilmsListBinding;
import com.tmbdnews.model.ConfigAndTopRated;
import com.tmbdnews.model.TopRated;
import com.tmbdnews.server.response.BaseResponse;
import com.tmbdnews.server.response.ResponseGetConfig;
import com.tmbdnews.server.response.ResponseGetTopRated;
import com.tmbdnews.utils.SharedPreferenceUtils;
import com.tmbdnews.view.adapters.FilmsAdapter;
import com.tmbdnews.view.handlers.Handlers;
import com.tmbdnews.viewmodel.FrgFilmListViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


@Layout(R.layout.frg_films_list)
public class FrgFilmsList extends BaseBindingFragment<FrgFilmsListBinding, FrgFilmListViewModel> implements FilmsAdapter.ListItemClickListener, Handlers.FilmListHandlers {
    private GridLayoutManager manager;
    private FilmsAdapter adapter;
    private int currentPage = 1;
    private int totalPage;
    private boolean loading = true;

    private final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) {
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int pastVisibleItems = manager.findFirstVisibleItemPosition();
                if (loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        onOffsetChanged();
                    }
                }
            }
        }
    };

    public static FrgFilmsList newInstance() {
        return new FrgFilmsList();
    }

    @Override
    public FrgFilmListViewModel initViewModel() {
        return new FrgFilmListViewModel();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setHandlers(this);
        getData();
    }

    private void handleResponse(BaseResponse response) {
        if (response instanceof ResponseGetTopRated) {
            viewModel.isShowEmptyView.set(false);
            ResponseGetTopRated getTopRated = (ResponseGetTopRated) response;
            initRecyclerView(getTopRated.getResults());
        } else if (response instanceof ResponseGetConfig) {
            ResponseGetConfig responseGetConfig = (ResponseGetConfig) response;
            utils.saveString(SharedPreferenceUtils.BASE_URL, responseGetConfig.getConfig().getBaseUrl());
        }
    }


    private void handleResponseWithOffset(BaseResponse response) {
        if (response instanceof ResponseGetTopRated) {
            ResponseGetTopRated getTopRated = (ResponseGetTopRated) response;
            viewModel.isProgressVisible.set(false);
            adapter.getItem().addAll(getTopRated.getResults());
            adapter.notifyItemRangeChanged(adapter.getItemCount(), getTopRated.getResults().size());
            loading = true;
            totalPage = getTopRated.getTotalPages();
        }
    }

    @Override
    protected void handleError(Throwable error) {
        super.handleError(error);
        viewModel.isProgressVisible.set(false);
        loading = true;
    }

    private void onOffsetChanged() {
        if (!networkUtils.isNetworkAvailable()) {
            showEmptyView();
            return;
        }
        if (currentPage == totalPage)
            return;
        ++currentPage;
        loading = false;
        viewModel.isProgressVisible.set(true);
        createRequest(service.getTopRated(currentPage), this::handleResponseWithOffset, false);
    }

    private void initRecyclerView(List<TopRated> list) {
        manager = new GridLayoutManager(App.getComponent().getContext(), 2);
        adapter = new FilmsAdapter(list);
        adapter.setListener(this);
        binding.rvFilms.setLayoutManager(manager);
        binding.rvFilms.setAdapter(adapter);
        binding.rvFilms.addOnScrollListener(scrollListener);
    }

    @Override
    public void onListItemClick(int position) {
        navigator.showFrgFilmDetails(adapter.getItem().get(position).getId());
    }

    @Override
    protected void showEmptyView() {
        currentPage = 1;
        viewModel.isShowEmptyView.set(true);
    }

    @Override
    public void onRetryClick(View v) {
        if (!networkUtils.isNetworkAvailable())
            return;
        getData();
    }

    private void getData() {
        if (!networkUtils.isNetworkAvailable()) {
            showEmptyView();
            return;
        }
        if (networkUtils.getBaseUrl() == null)
            createZipRequest(mergeEmittedItems(), service.getConfig(), service.getTopRated(currentPage));
        else
            createRequest(service.getTopRated(currentPage), this::handleResponse, true);
    }


    @NonNull
    private BiFunction<ResponseGetConfig, ResponseGetTopRated, ConfigAndTopRated> mergeEmittedItems() {
        return (responseGetConfig, responseGetTopRated) -> new ConfigAndTopRated(responseGetConfig.getConfig(), responseGetTopRated.getResults());
    }

    private void createZipRequest(BiFunction function, Observable responseGetConfigObservable, Observable responseGetTopRatedObservable) {
        Observable<ConfigAndTopRated> combined = Observable.zip(responseGetConfigObservable, responseGetTopRatedObservable, function);
        compositeDisposable.add(combined.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::onBeginResponse)
                .subscribe(this::handleMerge, this::handleError, this::onCompleteResponse));
    }

    private void handleMerge(ConfigAndTopRated configAndTopRated) {
        utils.saveString(SharedPreferenceUtils.BASE_URL, configAndTopRated.getConfig().getBaseUrl());
        viewModel.isShowEmptyView.set(false);
        initRecyclerView(configAndTopRated.getTopRated());
    }
}
