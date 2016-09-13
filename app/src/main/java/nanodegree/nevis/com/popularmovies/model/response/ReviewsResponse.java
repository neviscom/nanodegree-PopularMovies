package nanodegree.nevis.com.popularmovies.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Review;

/**
 * @author Nikita Simonov
 */

public class ReviewsResponse {

    @SerializedName("results")
    private List<Review> mReviews;

    @NonNull
    public List<Review> getReviews() {
        return mReviews;
    }
}
