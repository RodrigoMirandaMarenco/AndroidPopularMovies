package com.rodrigomirandamarenco.popularmovies.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rodrigomirandamarenco.popularmovies.R;
import com.rodrigomirandamarenco.popularmovies.adapter.MovieAdapter;
import com.rodrigomirandamarenco.popularmovies.model.Page;
import com.rodrigomirandamarenco.popularmovies.model.Result;
import com.rodrigomirandamarenco.popularmovies.network.MovieApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String PAGE_KEY = "page_key";

    private static final String REQUEST_TYPE_POPULAR = "popular";
    private static final String REQUEST_TYPE_TOP_RATED = "top_rated";

    private static final int GRID_SPAN_COUNT = 2;

    private RecyclerView mMoviesRecyclerView;
    private TextView mErrorMessageTextView;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;

    private static MovieApi sMovieApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesRecyclerView = findViewById(R.id.recyclerview_movies);
        mErrorMessageTextView = findViewById(R.id.tv_error_message_display);

        mProgressBar = findViewById(R.id.pb_loading_indicator);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_SPAN_COUNT);
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mMoviesRecyclerView.setAdapter(mMovieAdapter);

        Gson gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        sMovieApi = new Retrofit.Builder()
                .baseUrl(MovieApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build().create(MovieApi.class);

        if (savedInstanceState == null || !savedInstanceState.containsKey(PAGE_KEY)) {
            loadMovieData(REQUEST_TYPE_POPULAR);
        } else {
            mMovieAdapter.setMovieData((Page) savedInstanceState.getParcelable(PAGE_KEY));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMovieAdapter != null && mMovieAdapter.getPage() != null) {
            outState.putParcelable(PAGE_KEY, mMovieAdapter.getPage());
        }
        super.onSaveInstanceState(outState);
    }

    private void loadMovieData(String requestType) {
        mProgressBar.setVisibility(View.VISIBLE);
        final retrofit2.Call<Page> call = sMovieApi.getTopRatedMovieListPage(requestType, getString(R.string.themoviedb_api_key));
        call.enqueue(new retrofit2.Callback<Page>() {
            @Override
            public void onResponse(@NonNull Call<Page> call, @NonNull retrofit2.Response<Page> response) {
                if (response.code() == 200) {
                    mMovieAdapter.setMovieData(response.body());
                    showList();
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Page> call, @NonNull Throwable t) {
                showError();
            }
        });
    }

    private void showError() {
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mMoviesRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showList() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popular:
                loadMovieData(REQUEST_TYPE_POPULAR);
                return true;

            case R.id.action_sort_top_rated:
                loadMovieData(REQUEST_TYPE_TOP_RATED);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(Result result) {
        startActivity(DetailActivity.newInstance(this, result));
    }

}
