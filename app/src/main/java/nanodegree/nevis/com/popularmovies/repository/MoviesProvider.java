package nanodegree.nevis.com.popularmovies.repository;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.MoviesType;

/**
 * @author Nikita Simonov
 */
class MoviesProvider {

    private MoviesProvider() {
        // do nothing
    }

    static void save(@NonNull List<Movie> movies, @NonNull MoviesType movieType) {
        clearMovies(movieType);
        Hawk.put(movieType.name(), movies);
    }

    static List<Movie> getMovies(@NonNull MoviesType movieType) {
        return Hawk.get(movieType.name(), Collections.<Movie>emptyList());
    }

    static void save(@NonNull Movie movie, MoviesType movieType) {
        List<Movie> movies = Hawk.get(movieType.name(), new ArrayList<Movie>());
        movies.add(movie);
        save(movies, movieType);
    }

    static void delete(@NonNull Movie movie, MoviesType movieType) {
        List<Movie> movies = Hawk.get(movieType.name(), new ArrayList<Movie>());
        movies.remove(movie);
        save(movies, movieType);
    }

    private static void clearMovies(@NonNull MoviesType movieType) {
        Hawk.delete(movieType.name());
    }

}
