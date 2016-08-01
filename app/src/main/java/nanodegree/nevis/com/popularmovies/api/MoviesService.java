package nanodegree.nevis.com.popularmovies.api;

import nanodegree.nevis.com.popularmovies.model.Movies;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Nikita Simonov
 */

public interface MoviesService {

    @GET("popular")
    Observable<Movies> getPopular();

    @GET("top_rated")
    Observable<Movies> getTopRated();

}
