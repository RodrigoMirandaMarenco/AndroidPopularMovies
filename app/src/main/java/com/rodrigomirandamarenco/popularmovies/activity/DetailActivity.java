package com.rodrigomirandamarenco.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodrigomirandamarenco.popularmovies.R;
import com.rodrigomirandamarenco.popularmovies.model.Result;
import com.rodrigomirandamarenco.popularmovies.util.MovieApiUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_RESULT = "extra_result";

    private Result mResult;
    private TextView mTitleTextView;
    private TextView mReleaseDateTextView;
    private TextView mAverageRatingTextView;
    private TextView mSynopsisTextView;
    private ImageView mMoviePosterImageView;

    public static Intent newInstance(Context context, Result result) {
        Intent detailIntent = new Intent(context, DetailActivity.class);
        detailIntent.putExtra(EXTRA_MOVIE_RESULT, result);
        return detailIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTitleTextView = findViewById(R.id.tv_title);
        mReleaseDateTextView = findViewById(R.id.tv_release_date);
        mAverageRatingTextView = findViewById(R.id.tv_average_rating);
        mSynopsisTextView = findViewById(R.id.tv_synopsis);
        mMoviePosterImageView = findViewById(R.id.iv_movie_poster);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().hasExtra(EXTRA_MOVIE_RESULT)) {
            mResult = getIntent().getParcelableExtra(EXTRA_MOVIE_RESULT);
            if (mResult != null) {
                mTitleTextView.setText(mResult.getTitle());
                mReleaseDateTextView.setText(mResult.getReleaseYear());
                mAverageRatingTextView.setText(String.format(Locale.getDefault(),"%.1f/10", mResult.getVoteAverage()));
                mSynopsisTextView.setText(mResult.getOverview());
                Picasso.with(this)
                        .load(MovieApiUtils.getImageUrl(this, mResult.getPosterPath()))
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(mMoviePosterImageView);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
