package nanodegree.nevis.com.popularmovies.api;

import android.support.annotation.NonNull;

import nanodegree.nevis.com.popularmovies.model.response.MoviesResponse;
import nanodegree.nevis.com.popularmovies.model.response.ReviewsResponse;
import nanodegree.nevis.com.popularmovies.model.response.VideosResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Nikita Simonov
 */

public interface MoviesService {

    @GET("popular")
    Observable<MoviesResponse> getPopular();

    @GET("top_rated")
    Observable<MoviesResponse> getTopRated();

    @GET("{id}/videos")
    Observable<VideosResponse> getMovieTrailers(@Path("id") @NonNull String id);

    @GET("{id}/reviews")
    Observable<ReviewsResponse> getMovierReviews(@Path("id") @NonNull String id);


}
