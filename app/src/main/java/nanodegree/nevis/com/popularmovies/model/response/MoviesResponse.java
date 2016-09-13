package nanodegree.nevis.com.popularmovies.model.response;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public class MoviesResponse {

    @SerializedName("page")
    int mPage;

    @Nullable
    @SerializedName("results")
    private List<Movie> mMovies;

    @NonNull
    public List<Movie> getMovies() {
        if (mMovies == null) {
            mMovies = new ArrayList<>();
        }
        return mMovies;
    }
}
