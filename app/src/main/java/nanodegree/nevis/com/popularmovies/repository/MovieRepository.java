package nanodegree.nevis.com.popularmovies.repository;

import android.support.annotation.NonNull;

import java.util.List;

import nanodegree.nevis.com.popularmovies.api.MoviesService;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.Review;
import nanodegree.nevis.com.popularmovies.model.Video;
import nanodegree.nevis.com.popularmovies.model.response.MoviesResponse;
import nanodegree.nevis.com.popularmovies.model.response.ReviewsResponse;
import nanodegree.nevis.com.popularmovies.model.response.VideosResponse;
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
                .map(moviesToList());
    }

    public Observable<List<Movie>> getTopRated() {
        return mService.getTopRated()
                .map(moviesToList());
    }

    public Observable<List<Review>> getReviews(@NonNull Movie movie) {
        return mService.getMovierReviews(String.valueOf(movie.getId()))
                .map(new Func1<ReviewsResponse, List<Review>>() {
                    @Override
                    public List<Review> call(ReviewsResponse reviewsResponse) {
                        return reviewsResponse.getReviews();
                    }
                });
    }

    public Observable<List<Video>> getTrailers(@NonNull Movie movie) {
        return mService.getMovieTrailers(String.valueOf(movie.getId()))
                .map(new Func1<VideosResponse, List<Video>>() {
                    @Override
                    public List<Video> call(VideosResponse videosResponse) {
                        return videosResponse.getVideos();
                    }
                });
    }

    private Func1<MoviesResponse, List<Movie>> moviesToList() {
        return new Func1<MoviesResponse, List<Movie>>() {
            @Override
            public List<Movie> call(MoviesResponse moviesResponse) {
                return moviesResponse.getMovies();
            }
        };
    }

}
