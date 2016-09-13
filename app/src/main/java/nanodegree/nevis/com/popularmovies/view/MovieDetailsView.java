package nanodegree.nevis.com.popularmovies.view;

import android.support.annotation.NonNull;

import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.Review;
import nanodegree.nevis.com.popularmovies.model.Video;

/**
 * @author Nikita Simonov
 */

public interface MovieDetailsView extends ErrorView {

    void bindToolbarTitle(@NonNull String title);

    void bindImage(@NonNull Movie movie);

    void bindMovieTitle(@NonNull String title, @NonNull String date);

    void bindMovieOverview(@NonNull String overview);

    void bindAverageRating(@NonNull String average, @NonNull String max);

    void closeScreen();

    void showTrailersLoadingIndicator();

    void hideTrailersLoadingIndicator();

    void showReviewsLoadingIndicator();

    void hideReviewsLoadingIndicator();

    void showTrailers(@NonNull List<Video> trailers);

    void showReviews(@NonNull List<Review> reviews);

}
