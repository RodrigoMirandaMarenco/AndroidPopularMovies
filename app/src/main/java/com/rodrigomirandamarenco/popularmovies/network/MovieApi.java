package com.rodrigomirandamarenco.popularmovies.network;

import com.rodrigomirandamarenco.popularmovies.model.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rodrigomiranda on 12/19/17.
 */

public interface MovieApi {
    String BASE_URL = "http://api.themoviedb.org/3/";

    @GET("movie/{order}/")
    Call<Page> getTopRatedMovieListPage(@Path("order") String order, @Query("api_key") String sort);

}
