package nanodegree.nevis.com.popularmovies.view;

import android.support.annotation.NonNull;

import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public interface MovieDetailsView {

    void prepareForAnimation();

    void bindToolbarTitle(@NonNull String title);

    void bindImage(@NonNull Movie movie);

    void bindMovieTitle(@NonNull String title, @NonNull String date);

    void bindMovieOverview(@NonNull String overview);

    void bindAverageRating(@NonNull String average, @NonNull String max);

    void closeScreen();

}
