package nanodegree.nevis.com.popularmovies.view;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public interface MoviesView {

    void bindTitle(@StringRes int titleId);

    void showMovies(@NonNull List<Movie> movies);
}
