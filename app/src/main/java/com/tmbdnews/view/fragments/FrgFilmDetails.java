package com.tmbdnews.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tmbdnews.R;
import com.tmbdnews.annotations.Layout;
import com.tmbdnews.databinding.FrgFilmDetailsBinding;
import com.tmbdnews.model.FilmDetails;
import com.tmbdnews.server.ApiConstants;
import com.tmbdnews.view.handlers.Handlers;
import com.tmbdnews.viewmodel.FrgFilmDetailsViewModel;

import java.util.Locale;

@Layout(R.layout.frg_film_details)
public class FrgFilmDetails extends BaseBindingFragment<FrgFilmDetailsBinding, FrgFilmDetailsViewModel> implements Handlers.FilmDetailsHandlers {
    private static final String ID_KEY = "idKey";

    public static FrgFilmDetails newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(ID_KEY, id);
        FrgFilmDetails fragment = new FrgFilmDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FrgFilmDetailsViewModel initViewModel() {
        return new FrgFilmDetailsViewModel();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            binding.setHandlers(this);
            getData();
        }
    }

    private void handleResponse(FilmDetails filmDetails) {
        viewModel.isShowEmptyView.set(false);
        viewModel.init(filmDetails);
    }

    @Override
    public void onBackClick(View v) {
        navigator.finish();
    }

    @Override
    public void onRetryClick(View v) {
        if (!networkUtils.isNetworkAvailable())
            return;
        getData();
    }

    @Override
    protected void showEmptyView() {
        viewModel.isShowEmptyView.set(true);
    }

    private void getData() {
        String url = String.format(Locale.getDefault(), ApiConstants.GET_MOVIE_BY_ID, getArguments().getInt(ID_KEY));
        createRequest(service.getFilmDetails(url), this::handleResponse, true);
    }
}
