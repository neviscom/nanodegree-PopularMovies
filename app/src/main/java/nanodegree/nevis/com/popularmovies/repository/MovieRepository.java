package nanodegree.nevis.com.popularmovies.repository;

import java.util.List;

import nanodegree.nevis.com.popularmovies.api.MoviesService;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.Movies;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Nikita Simonov
 */

public class MovieRepository {

    private final MoviesService mService;

    public MovieRepository(MoviesService service) {
        mService = service;
    }

    public Observable<List<Movie>> getPopular() {
        return mService.getPopular()
                .flatMap(moviesToList());
    }

    public Observable<List<Movie>> getTopRated() {
        return mService.getTopRated()
                .flatMap(moviesToList());
    }

    private Func1<Movies, Observable<List<Movie>>> moviesToList() {
        return new Func1<Movies, Observable<List<Movie>>>() {
            @Override
            public Observable<List<Movie>> call(Movies movies) {
                return Observable.just(movies.getMovies());
            }
        };
    }

}
