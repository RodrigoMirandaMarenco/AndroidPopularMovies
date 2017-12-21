package com.rodrigomirandamarenco.popularmovies.util;

import android.content.Context;

import com.rodrigomirandamarenco.popularmovies.R;

/**
 * Created by rodrigomiranda on 12/19/17.
 * Helper class used for API utils
 */

public class MovieApiUtils {
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    /**
     * Formats the complete URL for a movie poster image hosted in tmdb.org
     *
     * @param context    Context used to obtain the value of poster_image_size
     * @param posterPath Image suffix for the URL
     * @return Full image URL as a String
     */
    public static String getImageUrl(Context context, String posterPath) {
        return String.format("%s%s/%s", IMAGE_BASE_URL, context.getString(R.string.poster_image_size), posterPath);
    }
}
