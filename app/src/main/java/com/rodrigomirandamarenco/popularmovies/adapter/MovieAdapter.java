package com.rodrigomirandamarenco.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rodrigomirandamarenco.popularmovies.R;
import com.rodrigomirandamarenco.popularmovies.model.Page;
import com.rodrigomirandamarenco.popularmovies.model.Result;
import com.rodrigomirandamarenco.popularmovies.util.MovieApiUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by rodrigomiranda on 12/19/17.
 * Adapter used to display Movie items.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private Page mPage;

    private final MovieAdapterOnClickHandler mMovieAdapterOnClickHandler;

    public Page getPage() {
        return mPage;
    }

    public MovieAdapter(MovieAdapterOnClickHandler movieAdapterOnClickHandler) {
        mMovieAdapterOnClickHandler = movieAdapterOnClickHandler;
    }

    /**
     * This interface needs to be implemented to call onClick events
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Result result);
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent   The ViewGroup that these ViewHolders are contained within.
     * @param viewType Unused until different viewTypes are required.
     * @return A new MovieAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        if (mPage != null && mPage.getResults() != null && !TextUtils.isEmpty(mPage.getResults().get(position).getBackdropPath())) {
            Picasso.with(holder.mMoviePosterImageView.getContext())
                    .load(MovieApiUtils.getImageUrl(holder.mMoviePosterImageView.getContext(), mPage.getResults().get(position).getPosterPath()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.mMoviePosterImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mPage != null && mPage.getResults() != null ? mPage.getResults().size() : 0;
    }

    public void setMovieData(Page page) {
        mPage = page;
        notifyDataSetChanged();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mMoviePosterImageView;

        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePosterImageView = itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mPage != null) {
                mMovieAdapterOnClickHandler.onClick(mPage.getResults().get(getAdapterPosition()));
            }
        }
    }

}
