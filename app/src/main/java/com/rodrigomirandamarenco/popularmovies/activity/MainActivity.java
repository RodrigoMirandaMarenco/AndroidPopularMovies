package com.rodrigomirandamarenco.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rodrigomirandamarenco.popularmovies.R;
import com.rodrigomirandamarenco.popularmovies.adapter.MovieAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int SPAN_COUNT = 2;

    private RecyclerView mMoviesRecyclerView;
    private TextView mErroMessageTextView;
    private ProgressBar mProgressBar;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mErroMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        mMoviesRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter();
        mMoviesRecyclerView.setAdapter(mMovieAdapter);
        
        loadMovieData();
    }

    private void loadMovieData() {
        ArrayList<String> items = new ArrayList<>();
        //TODO: Replace mock items list for an API request
        items.add("Test 1");
        items.add("Test 2");
        items.add("Test 3");
        items.add("Test 4");
        items.add("Test 5");
        mMovieAdapter.setMovieData(items);
    }

}
