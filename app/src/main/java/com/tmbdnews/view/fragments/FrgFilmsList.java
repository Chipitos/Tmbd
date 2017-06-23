package com.tmbdnews.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tmbdnews.App;
import com.tmbdnews.R;
import com.tmbdnews.databinding.FrgFilmsListBinding;
import com.tmbdnews.model.TopRated;
import com.tmbdnews.server.response.BaseResponse;
import com.tmbdnews.server.response.ResponseGetTopRated;
import com.tmbdnews.utils.NetworkUtils;
import com.tmbdnews.view.adapters.FilmsAdapter;
import com.tmbdnews.view.handlers.Handlers;
import com.tmbdnews.viewmodel.FrgFilmListViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public int initLayout() {
        return R.layout.frg_films_list;
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
        if (!NetworkUtils.isNetworkAvailable()) {
            showEmptyView();
            return;
        }
        if (currentPage == totalPage)
            return;
        ++currentPage;
        loading = false;
        viewModel.isProgressVisible.set(true);
        Observable<ResponseGetTopRated> bb = service.getTopRated(currentPage);
        bb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponseWithOffset, this::handleError);
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
        if (!NetworkUtils.isNetworkAvailable())
            return;
        getData();
    }

    private void getData() {
        if (NetworkUtils.getBaseUrl() == null)
            getActMain().getConfig();
        Observable<ResponseGetTopRated> responseGetTopRatedObservable = service.getTopRated(currentPage);
        responseGetTopRatedObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::onBeginResponse)
                .subscribe(this::handleResponse, this::handleError, this::onCompleteResponse);
    }
}
