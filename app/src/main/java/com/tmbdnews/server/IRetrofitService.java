package com.tmbdnews.server;


import com.tmbdnews.model.FilmDetails;
import com.tmbdnews.server.response.ResponseGetConfig;
import com.tmbdnews.server.response.ResponseGetTopRated;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface IRetrofitService {

    @GET(ApiConstants.GET_TOP_RATED)
    Observable<ResponseGetTopRated> getTopRated(@Query("page") int page);

    @GET(ApiConstants.GET_CONFIG)
    Observable<ResponseGetConfig> getConfig();

    @GET
    Observable<FilmDetails> getFilmDetails(@Url String url);
}
