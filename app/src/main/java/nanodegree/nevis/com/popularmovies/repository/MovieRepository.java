package nanodegree.nevis.com.popularmovies.repository;

import android.support.annotation.NonNull;

import java.util.List;

import nanodegree.nevis.com.popularmovies.api.MoviesService;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.MoviesType;
import nanodegree.nevis.com.popularmovies.model.Review;
import nanodegree.nevis.com.popularmovies.model.Video;
import nanodegree.nevis.com.popularmovies.model.response.MoviesResponse;
import nanodegree.nevis.com.popularmovies.model.response.ReviewsResponse;
import nanodegree.nevis.com.popularmovies.model.response.VideosResponse;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * @author Nikita Simonov
 */

public class MovieRepository {

    private final MoviesService mService;

    MovieRepository(MoviesService service) {
        mService = service;
    }

    @NonNull
    public Observable<List<Movie>> loadMovies(@NonNull final MoviesType type) {
        return getMoviesObservable(type)
                .doOnNext(new Action1<List<Movie>>() {
                    @Override
                    public void call(List<Movie> movies) {
                        MoviesProvider.save(movies, type);
                    }
                })
                .zipWith(getMoviesObservable(MoviesType.FAVOURITE), markFavouritesFunc());
    }

    @NonNull
    public Observable<List<Review>> getReviews(@NonNull Movie movie) {
        return mService.getMovierReviews(String.valueOf(movie.getId()))
                .map(new Func1<ReviewsResponse, List<Review>>() {
                    @Override
                    public List<Review> call(ReviewsResponse reviewsResponse) {
                        return reviewsResponse.getReviews();
                    }
                });
    }

    @NonNull
    public Observable<List<Video>> getTrailers(@NonNull Movie movie) {
        return mService.getMovieTrailers(String.valueOf(movie.getId()))
                .map(new Func1<VideosResponse, List<Video>>() {
                    @Override
                    public List<Video> call(VideosResponse videosResponse) {
                        return videosResponse.getVideos();
                    }
                });
    }

    @NonNull
    public Observable<Boolean> addToFavourite(@NonNull final Movie movie) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                MoviesProvider.save(movie, MoviesType.FAVOURITE);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        })
                .compose(RxLoader.<Boolean>async());
    }

    @NonNull
    public Observable<Boolean> removeFromFavourite(@NonNull final Movie movie) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                MoviesProvider.delete(movie, MoviesType.FAVOURITE);
                subscriber.onNext(false);
                subscriber.onCompleted();
            }
        })
                .compose(RxLoader.<Boolean>async());
    }

    @NonNull
    private Func1<MoviesResponse, List<Movie>> moviesToList() {
        return new Func1<MoviesResponse, List<Movie>>() {
            @Override
            public List<Movie> call(MoviesResponse moviesResponse) {
                return moviesResponse.getMovies();
            }
        };
    }

    @NonNull
    private Observable<List<Movie>> getMoviesObservable(@NonNull MoviesType type) {
        switch (type) {
            case POPULAR:
                return mService.getPopular().map(moviesToList());
            case TOP_RATED:
                return mService.getTopRated().map(moviesToList());
            case FAVOURITE:
                return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
                    @Override
                    public void call(Subscriber<? super List<Movie>> subscriber) {
                        subscriber.onNext(MoviesProvider.getMovies(MoviesType.FAVOURITE));
                        subscriber.onCompleted();
                    }
                });
        }
        return Observable.empty();
    }

    @NonNull
    private Func2<List<Movie>, List<Movie>, List<Movie>> markFavouritesFunc() {
        return new Func2<List<Movie>, List<Movie>, List<Movie>>() {
            @Override
            public List<Movie> call(List<Movie> movies, List<Movie> favouriteMovies) {
                for (Movie movie : movies) {
                    for (Movie favouriteMovie : favouriteMovies) {
                        if (movie.getId() == favouriteMovie.getId()) {
                            movie.setFavourite(true);
                            break;
                        }
                    }
                }
                return movies;
            }
        };
    }

}
