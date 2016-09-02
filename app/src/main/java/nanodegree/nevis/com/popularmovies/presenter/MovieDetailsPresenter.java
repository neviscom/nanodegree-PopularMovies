package nanodegree.nevis.com.popularmovies.presenter;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;

import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.view.MovieDetailsView;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsPresenter {

    private static final String MAX_RATING = "10";

    private MovieDetailsView mView;
    private Movie mMovie;

    public MovieDetailsPresenter() {
        // do nothing
    }

    public void init(@NonNull MovieDetailsView view, @NonNull Movie movie) {
        mView = view;
        mMovie = movie;
        showContent();
        mView.prepareForAnimation();
    }
    
    public void onHomeButtonPressed() {
        mView.closeScreen();
    }

    private void showContent() {
        mView.bindToolbarTitle(mMovie.getTitle());
        mView.bindImage(mMovie);
        mView.bindMovieTitle(mMovie.getTitle(), mMovie.getReleasedDate());
        mView.bindAverageRating(formatVoteAverage(mMovie.getVoteAverage()), MAX_RATING);
        mView.bindMovieOverview(mMovie.getOverview());
    }

    private String formatVoteAverage(double voteAverage) {
        return new DecimalFormat("#0.0").format(voteAverage);
    }
}
