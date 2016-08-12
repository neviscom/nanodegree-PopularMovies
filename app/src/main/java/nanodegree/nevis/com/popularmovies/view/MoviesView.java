package nanodegree.nevis.com.popularmovies.view;

import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public interface MoviesView {

    void showMovies(List<Movie> movies);
}
